import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        notifyAll();
    }

    public synchronized BookingRequest getRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue.poll();
    }
}

// Inventory Manager (shared resource)
class InventoryManager {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    // Critical Section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType +
                    " | Remaining: " + (available - 1));

            return true;
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " failed booking " + roomType + " (No rooms)");

            return false;
        }
    }

    public void displayInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Booking Processor Thread
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private InventoryManager inventory;

    public BookingProcessor(String name, BookingQueue queue, InventoryManager inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        for (int i = 0; i < 2; i++) {   // each thread processes requests
            BookingRequest request = queue.getRequest();

            System.out.println(getName() + " processing booking for " + request.guestName);

            inventory.allocateRoom(request.roomType);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

        BookingQueue queue = new BookingQueue();
        InventoryManager inventory = new InventoryManager();

        // Simulate guest booking requests
        queue.addRequest(new BookingRequest("Alice", "Single"));
        queue.addRequest(new BookingRequest("Bob", "Single"));
        queue.addRequest(new BookingRequest("Charlie", "Double"));
        queue.addRequest(new BookingRequest("David", "Double"));
        queue.addRequest(new BookingRequest("Eve", "Suite"));

        // Multiple processors (threads)
        BookingProcessor t1 = new BookingProcessor("Processor-1", queue, inventory);
        BookingProcessor t2 = new BookingProcessor("Processor-2", queue, inventory);
        BookingProcessor t3 = new BookingProcessor("Processor-3", queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        inventory.displayInventory();
    }
}