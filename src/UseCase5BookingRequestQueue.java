import java.util.LinkedList;
import java.util.Queue;

/**
 * ============================================================
 * Use Case 5: Booking Request (First-Come-First-Served)
 * ============================================================
 *
 * Demonstrates queue-based booking request handling.
 *
 * @version 5.1
 */

// ---------------- RESERVATION CLASS ----------------
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// ---------------- BOOKING QUEUE ----------------
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all requests
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue (FIFO Order):");

        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("Booking Request Queue System\n");

        // Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();
    }
}
