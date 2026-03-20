import java.util.*;

/**
 * ============================================================
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * ============================================================
 *
 * Demonstrates thread-safe booking using synchronized blocks
 *
 * @version 11.1
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

// ---------------- SHARED INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // synchronized method (critical section)
    public synchronized boolean allocateRoom(String type, String guestName) {

        if (inventory.getOrDefault(type, 0) > 0) {

            System.out.println(Thread.currentThread().getName() +
                    " allocating " + type + " room to " + guestName);

            // simulate delay (to expose race condition if not synchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(type, inventory.get(type) - 1);

            System.out.println("✅ " + guestName + " booking confirmed");
            return true;
        } else {
            System.out.println("❌ No " + type + " rooms available for " + guestName);
            return false;
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// ---------------- SHARED QUEUE ----------------
class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

// ---------------- THREAD PROCESSOR ----------------
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation request;

            // synchronized retrieval
            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null) {
                break;
            }

            // critical section (inventory allocation)
            inventory.allocateRoom(request.roomType, request.guestName);
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Shared resources
        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        // Add multiple requests (simulating concurrent users)
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // should fail
        queue.addRequest(new Reservation("David", "Double"));
        queue.addRequest(new Reservation("Eve", "Double")); // should fail

        // Create multiple threads
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        // Final inventory
        inventory.displayInventory();
    }
}
