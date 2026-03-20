import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================
 * Use Case 4: Room Search & Availability Check
 * ============================================================
 *
 * Demonstrates read-only search on inventory.
 *
 * @version 4.1
 */

// ---------------- ROOM DOMAIN ----------------
abstract class Room {
    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 0); // Example: unavailable
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// ---------------- SEARCH SERVICE ----------------
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // READ-ONLY search
    public void searchAvailableRooms() {

        System.out.println("Available Rooms:\n");

        // Single Room
        if (inventory.getAvailability("Single") > 0) {
            System.out.println("Single Room:");
            new SingleRoom().displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Single") + "\n");
        }

        // Double Room
        if (inventory.getAvailability("Double") > 0) {
            System.out.println("Double Room:");
            new DoubleRoom().displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Double") + "\n");
        }

        // Suite Room (filtered if 0)
        if (inventory.getAvailability("Suite") > 0) {
            System.out.println("Suite Room:");
            new SuiteRoom().displayDetails();
            System.out.println("Available: " + inventory.getAvailability("Suite"));
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("Room Search System\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Perform search (READ-ONLY)
        searchService.searchAvailableRooms();
    }
}