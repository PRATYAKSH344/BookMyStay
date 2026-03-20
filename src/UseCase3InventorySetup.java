import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * Use Case 3: Centralized Room Inventory Management
 * ============================================================
 *
 * Demonstrates use of HashMap for centralized inventory.
 *
 * @version 3.1
 */

// Inventory Class
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("Centralized Room Inventory System\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating Single Room availability...\n");
        inventory.updateAvailability("Single", 4);

        // Display updated inventory
        inventory.displayInventory();

        // Example fetch
        System.out.println("\nAvailable Suite Rooms: " +
                inventory.getAvailability("Suite"));
    }
}
