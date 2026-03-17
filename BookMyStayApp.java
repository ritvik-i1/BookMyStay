import java.util.*;

// Class representing an Add-On Service
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class to handle Add-On Services
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        List<Service> services = reservationServices.get(reservationId);
        if (services == null) return 0.0;

        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    // Display services
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);

        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation ID: " + reservationId);
        for (Service s : services) {
            System.out.println("- " + s);
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        while (true) {
            System.out.println("\nSelect Add-On Service:");
            System.out.println("1. Breakfast (₹200)");
            System.out.println("2. Spa (₹1000)");
            System.out.println("3. Airport Pickup (₹500)");
            System.out.println("4. Finish Selection");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, new Service("Breakfast", 200));
                    break;
                case 2:
                    manager.addService(reservationId, new Service("Spa", 1000));
                    break;
                case 3:
                    manager.addService(reservationId, new Service("Airport Pickup", 500));
                    break;
                case 4:
                    manager.displayServices(reservationId);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}