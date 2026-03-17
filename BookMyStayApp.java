import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State (Inventory + Booking History)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state to file
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state from file
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state restored successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Corrupted data. Starting fresh.");
        }

        // Default state if file missing/corrupt
        Map<String, Integer> defaultInventory = new HashMap<>();
        defaultInventory.put("Single", 2);
        defaultInventory.put("Double", 2);
        defaultInventory.put("Suite", 1);

        return new SystemState(defaultInventory, new ArrayList<>());
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Load previous state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> bookings = state.bookings;

        while (true) {
            System.out.println("\n1. Book Room");
            System.out.println("2. View Bookings");
            System.out.println("3. View Inventory");
            System.out.println("4. Save & Exit");

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

                    int available = inventory.getOrDefault(type, 0);

                    if (available <= 0) {
                        System.out.println("No rooms available.");
                        break;
                    }

                    inventory.put(type, available - 1);

                    Reservation r = new Reservation(id, name, type);
                    bookings.add(r);

                    System.out.println("Booking successful!");
                    break;

                case 2:
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (Reservation res : bookings) {
                            System.out.println(res);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Inventory: " + inventory);
                    break;

                case 4:
                    // Save before exit
                    PersistenceService.save(new SystemState(inventory, bookings));
                    System.out.println("Exiting system...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}