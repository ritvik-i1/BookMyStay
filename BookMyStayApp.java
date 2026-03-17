import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;
    boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }

    @Override
    public String toString() {
        return "ID: " + reservationId +
                ", Guest: " + guestName +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Cancelled: " + isCancelled;
    }
}

// Inventory Manager
class InventoryManager {

    private Map<String, Integer> inventory;
    private Map<String, Stack<String>> availableRooms;

    public InventoryManager() {
        inventory = new HashMap<>();
        availableRooms = new HashMap<>();

        // Initialize inventory
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        // Initialize room IDs using Stack (LIFO)
        availableRooms.put("Single", new Stack<>());
        availableRooms.put("Double", new Stack<>());
        availableRooms.put("Suite", new Stack<>());

        availableRooms.get("Single").push("S1");
        availableRooms.get("Single").push("S2");

        availableRooms.get("Double").push("D1");
        availableRooms.get("Double").push("D2");

        availableRooms.get("Suite").push("SU1");
    }

    public String allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            throw new Exception("Room not available.");
        }

        inventory.put(roomType, inventory.get(roomType) - 1);
        return availableRooms.get(roomType).pop(); // LIFO allocation
    }

    public void releaseRoom(String roomType, String roomId) {
        inventory.put(roomType, inventory.get(roomType) + 1);
        availableRooms.get(roomType).push(roomId); // LIFO rollback
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

// Booking Manager
class BookingManager {

    private Map<String, Reservation> reservations;

    public BookingManager() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        reservations.put(r.reservationId, r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }
}

// Cancellation Service
class CancellationService {

    public static void cancelReservation(String reservationId,
                                         BookingManager bookingManager,
                                         InventoryManager inventoryManager)
            throws CancellationException {

        Reservation r = bookingManager.getReservation(reservationId);

        // Validation
        if (r == null) {
            throw new CancellationException("Reservation does not exist.");
        }

        if (r.isCancelled) {
            throw new CancellationException("Reservation already cancelled.");
        }

        // Rollback (Controlled Mutation)
        inventoryManager.releaseRoom(r.roomType, r.roomId);
        r.isCancelled = true;

        System.out.println("Cancellation successful for ID: " + reservationId);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager inventory = new InventoryManager();
        BookingManager bookingManager = new BookingManager();

        while (true) {
            try {
                System.out.println("\n1. Book Room");
                System.out.println("2. Cancel Booking");
                System.out.println("3. View Inventory");
                System.out.println("4. Exit");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Enter Reservation ID: ");
                        String id = sc.nextLine();

                        System.out.print("Enter Guest Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Room Type (Single/Double/Suite): ");
                        String type = sc.nextLine();

                        String roomId = inventory.allocateRoom(type);

                        Reservation r = new Reservation(id, name, type, roomId);
                        bookingManager.addReservation(r);

                        System.out.println("Booking Successful! Room ID: " + roomId);
                        break;

                    case 2:
                        System.out.print("Enter Reservation ID to cancel: ");
                        String cancelId = sc.nextLine();

                        CancellationService.cancelReservation(cancelId, bookingManager, inventory);
                        break;

                    case 3:
                        inventory.displayInventory();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (CancellationException e) {
                System.out.println("Cancellation Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}