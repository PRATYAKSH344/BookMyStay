import java.io.*;
import java.util.*;

/**
 * ============================================================
 * Use Case 12: Data Persistence & System Recovery
 * ============================================================
 *
 * Demonstrates saving and restoring system state using serialization
 *
 * @version 12.1
 */

// ---------------- RESERVATION ----------------
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String id, String guest, String type) {
        this.reservationId = id;
        this.guestName = guest;
        this.roomType = type;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// ---------------- SYSTEM STATE ----------------
class SystemState implements Serializable {
    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// ---------------- PERSISTENCE SERVICE ----------------
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // SAVE STATE
    public static void save(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("✅ System state saved successfully.");

        } catch (IOException e) {
            System.out.println("❌ Error saving state: " + e.getMessage());
        }
    }

    // LOAD STATE
    public static SystemState load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("✅ System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("⚠ No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("❌ Error loading state: " + e.getMessage());
        }

        return null;
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Startup...\n");

        // Try to load existing state
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {
            // Fresh start
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);

            bookings = new ArrayList<>();

            System.out.println("Initialized new system state.\n");

        } else {
            // Restore previous state
            inventory = state.inventory;
            bookings = state.bookings;

            System.out.println("Restored previous system state.\n");
        }

        // Simulate booking
        Reservation r1 = new Reservation("S-1", "Alice", "Single");
        bookings.add(r1);
        inventory.put("Single", inventory.get("Single") - 1);

        System.out.println("Current Bookings:");
        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Save state before shutdown
        System.out.println("\nSaving system state...");
        PersistenceService.save(new SystemState(inventory, bookings));

        System.out.println("\nSystem shutdown complete.");
    }
}
