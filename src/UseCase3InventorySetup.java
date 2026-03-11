import java.util.HashMap;

class RoomInventoryUC3 {

    private HashMap<String, Integer> inventory;

    public RoomInventoryUC3() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void displayInventory() {

        System.out.println("=== Inventory ===");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        RoomInventoryUC3 inventory = new RoomInventoryUC3();

        inventory.displayInventory();
    }
}