import java.util.LinkedList;
import java.util.Queue;

class ReservationUC5 {

    private String guestName;
    private String roomType;

    public ReservationUC5(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(guestName + " requested " + roomType);
    }
}

class BookingQueueUC5 {

    private Queue<ReservationUC5> queue;

    public BookingQueueUC5() {
        queue = new LinkedList<>();
    }

    public void addRequest(ReservationUC5 r) {
        queue.add(r);
    }

    public void displayQueue() {

        System.out.println("=== Booking Queue ===");

        for (ReservationUC5 r : queue) {
            r.display();
        }
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        BookingQueueUC5 queue = new BookingQueueUC5();

        queue.addRequest(new ReservationUC5("Ravi", "Single Room"));
        queue.addRequest(new ReservationUC5("Priya", "Double Room"));
        queue.addRequest(new ReservationUC5("Arjun", "Suite Room"));

        queue.displayQueue();
    }
}