abstract class RoomUC2 {

    private int beds;
    private double price;

    public RoomUC2(int beds, double price) {
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

class SingleRoomUC2 extends RoomUC2 {

    public SingleRoomUC2() {
        super(1, 1000);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoomUC2 extends RoomUC2 {

    public DoubleRoomUC2() {
        super(2, 2000);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoomUC2 extends RoomUC2 {

    public SuiteRoomUC2() {
        super(3, 5000);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

public class UseCase2RoomInitialization {

    public static void main(String[] args) {

        RoomUC2 single = new SingleRoomUC2();
        RoomUC2 dbl = new DoubleRoomUC2();
        RoomUC2 suite = new SuiteRoomUC2();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("=== Room Details ===");

        System.out.println(single.getRoomType() + " Available: " + singleAvailable);
        System.out.println(dbl.getRoomType() + " Available: " + doubleAvailable);
        System.out.println(suite.getRoomType() + " Available: " + suiteAvailable);
    }
}