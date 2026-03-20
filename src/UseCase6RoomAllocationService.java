import java.util.*;

/**
 * ============================================================
 * Use Case 6: Reservation Confirmation & Room Allocation
 * ============================================================
 *
 * Demonstrates FIFO booking processing + unique room allocation
 *
 * @version 6.1
 */

// ---------------- RESERVATION ----------------
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// ---------------- BOOKING QUEUE ----------------
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// ---------------- BOOKING SERVICE ----------------
class BookingService {

    private RoomInventory inventory;

    // Track allocated room IDs (NO duplicates)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → allocated room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Generate unique room ID
    private String generateRoomId(String type, int count) {
        return type.substring(0, 1).toUpperCase() + "-" + count;
    }

    public void processBookings(BookingQueue queue) {

        System.out.println("Processing Booking Requests...\n");

        int counter = 1;

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String type = request.roomType;

            System.out.println("Processing: " + request.guestName + " (" + type + ")");

            // Check availability
            if (inventory.getAvailability(type) > 0) {

                // Generate unique ID
                String roomId = generateRoomId(type, counter++);

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    // Store in map
                    roomAllocations.putIfAbsent(type, new HashSet<>());
                    roomAllocations.get(type).add(roomId);

                    // Update inventory immediately
                    inventory.decreaseAvailability(type);

                    System.out.println("✅ Booking Confirmed! Room ID: " + roomId);

                } else {
                    System.out.println("❌ Duplicate Room ID detected!");
                }

            } else {
                System.out.println("❌ No rooms available for " + type);
            }

            System.out.println();
        }
    }

    public void displayAllocations() {
        System.out.println("\nRoom Allocations:");
        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " Rooms: " + entry.getValue());
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create booking queue
        BookingQueue queue = new BookingQueue();

        // Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Double"));
        queue.addRequest(new Reservation("David", "Suite"));
        queue.addRequest(new Reservation("Eve", "Suite")); // should fail

        // Process bookings
        BookingService service = new BookingService(inventory);
        service.processBookings(queue);

        // Show final allocation
        service.displayAllocations();

        // Show remaining inventory
        inventory.displayInventory();
    }
}
