import java.util.*;

/**
 * ============================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * ============================================================
 *
 * Demonstrates cancellation with rollback using Stack (LIFO)
 *
 * @version 10.1
 */

// ---------------- RESERVATION ----------------
class Reservation {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String id, String guestName, String roomType) {
        this.reservationId = id;
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// ---------------- INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public void increaseAvailability(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// ---------------- BOOKING HISTORY ----------------
class BookingHistory {

    private Map<String, Reservation> bookings = new HashMap<>();

    public void addReservation(Reservation r) {
        bookings.put(r.reservationId, r);
    }

    public Reservation getReservation(String id) {
        return bookings.get(id);
    }

    public void removeReservation(String id) {
        bookings.remove(id);
    }

    public void displayHistory() {
        System.out.println("\nActive Bookings:");
        for (Reservation r : bookings.values()) {
            System.out.println(r.reservationId + " | " + r.guestName + " | " + r.roomType);
        }
    }
}

// ---------------- CANCELLATION SERVICE ----------------
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nCancelling Reservation: " + reservationId);

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            System.out.println("❌ Cancellation failed: Reservation not found");
            return;
        }

        // Push to rollback stack (LIFO tracking)
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increaseAvailability(r.roomType);

        // Remove booking
        history.removeReservation(reservationId);

        System.out.println("✅ Booking cancelled successfully for " + r.guestName);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack (Recent cancellations): " + rollbackStack);
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation System\n");

        // Initialize
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("S-1", "Alice", "Single"));
        history.addReservation(new Reservation("D-1", "Bob", "Double"));

        history.displayHistory();

        // Cancellation service
        CancellationService cancelService = new CancellationService(inventory, history);

        // Cancel valid booking
        cancelService.cancelBooking("S-1");

        // Attempt invalid cancellation
        cancelService.cancelBooking("X-99");

        // Show results
        history.displayHistory();
        inventory.displayInventory();
        cancelService.displayRollbackStack();
    }
}