import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Inventory Manager (guards system state)
class InventoryManager {
    private Map<String, Integer> roomInventory;

    public InventoryManager() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!roomInventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (roomInventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }

    public void bookRoom(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Room Availability: " + roomInventory);
    }
}

// Validator class (Fail-Fast)
class BookingValidator {

    public static void validateInput(String reservationId, String guestName, String roomType)
            throws InvalidBookingException {

        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InventoryManager inventory = new InventoryManager();

        while (true) {
            try {
                System.out.println("\n--- Booking Menu ---");

                System.out.print("Enter Reservation ID: ");
                String id = sc.nextLine();

                System.out.print("Enter Guest Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Room Type (Single/Double/Suite): ");
                String roomType = sc.nextLine();

                // Step 1: Input validation
                BookingValidator.validateInput(id, name, roomType);

                // Step 2: Business validation
                inventory.validateRoomType(roomType);
                inventory.validateAvailability(roomType);

                // Step 3: Booking
                inventory.bookRoom(roomType);

                Reservation reservation = new Reservation(id, name, roomType);

                System.out.println("Booking Successful!");
                System.out.println(reservation);

                inventory.displayInventory();

            } catch (InvalidBookingException e) {
                // Graceful failure handling
                System.out.println("Booking Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            }

            System.out.println("\nDo you want to continue? (yes/no)");
            String cont = sc.nextLine();

            if (!cont.equalsIgnoreCase("yes")) {
                System.out.println("Exiting system...");
                break;
            }
        }
    }
}