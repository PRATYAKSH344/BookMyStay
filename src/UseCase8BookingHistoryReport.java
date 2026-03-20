import java.util.*;

/**
 * ============================================================
 * Use Case 8: Booking History & Reporting
 * ============================================================
 *
 * Demonstrates storing booking history and generating reports
 *
 * @version 8.1
 */

// ---------------- RESERVATION ----------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// ---------------- BOOKING HISTORY ----------------
class BookingHistory {

    // Stores reservations in order (chronological)
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\nBooking History:");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }
}

// ---------------- REPORT SERVICE ----------------
class BookingReportService {

    // Generate summary report
    public void generateReport(List<Reservation> reservations) {

        System.out.println("\nBooking Summary Report:");

        if (reservations.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        // Count bookings per room type
        Map<String, Integer> report = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            report.put(type, report.getOrDefault(type, 0) + 1);
        }

        // Display report
        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Booked: " + entry.getValue());
        }

        // Total bookings
        System.out.println("Total Bookings: " + reservations.size());
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("Booking History & Reporting System\n");

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("S-1", "Alice", "Single"));
        history.addReservation(new Reservation("S-2", "Bob", "Single"));
        history.addReservation(new Reservation("D-1", "Charlie", "Double"));
        history.addReservation(new Reservation("SU-1", "David", "Suite"));

        // Display history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getAllReservations());
    }
}