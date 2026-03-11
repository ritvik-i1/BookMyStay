import java.util.*;

// Reservation request
class ReservationUC6 {

    private String guestName;
    private String roomType;

    public ReservationUC6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// Inventory Service
class RoomInventoryUC6 {

    private HashMap<String, Integer> inventory;

    public RoomInventoryUC6() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseInventory(String roomType) {

        int count = inventory.get(roomType);

        if (count > 0) {
            inventory.put(roomType, count - 1);
        }
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}


// Booking Service
class BookingServiceUC6 {

    private Queue<ReservationUC6> bookingQueue;
    private RoomInventoryUC6 inventory;

    // Prevent duplicate room IDs
    private Set<String> allocatedRoomIds;

    // Track room IDs per room type
    private HashMap<String, Set<String>> allocatedRooms;

    private int roomCounter = 1;

    public BookingServiceUC6(RoomInventoryUC6 inventory) {

        this.inventory = inventory;

        bookingQueue = new LinkedList<>();
        allocatedRoomIds = new HashSet<>();
        allocatedRooms = new HashMap<>();
    }

    public void addBookingRequest(ReservationUC6 reservation) {

        bookingQueue.add(reservation);

        System.out.println("Booking request added for "
                + reservation.getGuestName());
    }

    public void processBookings() {

        System.out.println("\nProcessing Booking Requests...\n");

        while (!bookingQueue.isEmpty()) {

            ReservationUC6 reservation = bookingQueue.poll();

            String roomType = reservation.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decreaseInventory(roomType);

                System.out.println("Reservation Confirmed:");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println();

            } else {

                System.out.println("Reservation Failed for "
                        + reservation.getGuestName()
                        + " (No " + roomType + " available)");
            }
        }
    }

    private String generateRoomId(String roomType) {

        String roomId;

        do {
            roomId = roomType.replace(" ", "")
                    + "-" + roomCounter++;
        }
        while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}


// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        RoomInventoryUC6 inventory = new RoomInventoryUC6();

        BookingServiceUC6 bookingService =
                new BookingServiceUC6(inventory);

        // Booking requests (FIFO)
        bookingService.addBookingRequest(
                new ReservationUC6("Ravi", "Single Room"));

        bookingService.addBookingRequest(
                new ReservationUC6("Priya", "Double Room"));

        bookingService.addBookingRequest(
                new ReservationUC6("Arjun", "Single Room"));

        bookingService.addBookingRequest(
                new ReservationUC6("Meera", "Suite Room"));

        bookingService.addBookingRequest(
                new ReservationUC6("Karan", "Suite Room"));

        // Process bookings
        bookingService.processBookings();

        // Show remaining inventory
        inventory.displayInventory();
    }
}