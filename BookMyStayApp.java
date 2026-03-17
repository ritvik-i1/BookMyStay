import java.util.*;

// Reservation class (represents a confirmed booking)
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double baseCost;

    public Reservation(String reservationId, String guestName, String roomType, double baseCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.baseCost = baseCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getBaseCost() {
        return baseCost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Cost: ₹" + baseCost;
    }
}

// Booking History Manager
class BookingHistoryManager {

    // List to maintain order of confirmed bookings
    private List<Reservation> bookingHistory;

    public BookingHistoryManager() {
        bookingHistory = new ArrayList<>();
    }

    // Store reservation
    public void addReservation(Reservation reservation) {
        bookingHistory.add(reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return bookingHistory;
    }
}

// Reporting Service (separate from storage)
class ReportingService {

    // Generate summary report
    public void generateReport(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n===== Booking History Report =====");

        double totalRevenue = 0;

        for (Reservation r : reservations) {
            System.out.println(r);
            totalRevenue += r.getBaseCost();
        }

        System.out.println("----------------------------------");
        System.out.println("Total Bookings: " + reservations.size());
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookingHistoryManager historyManager = new BookingHistoryManager();
        ReportingService reportingService = new ReportingService();

        while (true) {
            System.out.println("\n1. Add Reservation");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Room Type: ");
                    String room = sc.nextLine();

                    System.out.print("Enter Cost: ");
                    double cost = sc.nextDouble();

                    Reservation reservation = new Reservation(id, name, room, cost);
                    historyManager.addReservation(reservation);

                    System.out.println("Reservation added successfully!");
                    break;

                case 2:
                    List<Reservation> all = historyManager.getAllReservations();

                    if (all.isEmpty()) {
                        System.out.println("No bookings yet.");
                    } else {
                        System.out.println("\nBooking History:");
                        for (Reservation r : all) {
                            System.out.println(r);
                        }
                    }
                    break;

                case 3:
                    reportingService.generateReport(historyManager.getAllReservations());
                    break;

                case 4:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}