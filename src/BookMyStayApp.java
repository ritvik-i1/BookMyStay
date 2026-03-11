import java.util.HashMap;

abstract class RoomUC4 {

    private int beds;
    private double price;

    public RoomUC4(int beds, double price) {
        this.beds = beds;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();
}

class SingleRoomUC4 extends RoomUC4 {

    public SingleRoomUC4() {
        super(1, 1000);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoomUC4 extends RoomUC4 {

    public DoubleRoomUC4() {
        super(2, 2000);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoomUC4 extends RoomUC4 {

    public SuiteRoomUC4() {
        super(3, 5000);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

class RoomInventoryUC4 {

    private HashMap<String, Integer> inventory;

    public RoomInventoryUC4() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomUC4[] rooms = {
                new SingleRoomUC4(),
                new DoubleRoomUC4(),
                new SuiteRoomUC4()
        };

        RoomInventoryUC4 inventory = new RoomInventoryUC4();

        System.out.println("=== Available Rooms ===");

        for (RoomUC4 room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                System.out.println(room.getRoomType());
                System.out.println("Beds: " + room.getBeds());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}