import java.util.*;

/**
 * ============================================================
 * Use Case 9: Error Handling & Validation
 * ============================================================
 *
 * Demonstrates validation, custom exceptions, and safe execution
 *
 * @version 9.1
 */

// ---------------- CUSTOM EXCEPTION ----------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
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

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decreaseAvailability(String type) throws InvalidBookingException {

        int available = getAvailability(type);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, available - 1);
    }
}

// ---------------- VALIDATOR ----------------
class BookingValidator {

    public static void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// ---------------- BOOKING SERVICE ----------------
class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {
            // VALIDATION (fail-fast)
            BookingValidator.validate(guestName, roomType, inventory);

            // If valid → allocate
            inventory.decreaseAvailability(roomType);

            System.out.println("✅ Booking successful for " + guestName +
                    " | Room: " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful error handling
            System.out.println("❌ Booking failed: " + e.getMessage());
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("Booking System with Validation\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.bookRoom("Alice", "Single");

        // Invalid room type
        service.bookRoom("Bob", "Luxury");

        // Empty name
        service.bookRoom("", "Double");

        // Overbooking (should fail)
        service.bookRoom("Charlie", "Suite");
        service.bookRoom("David", "Suite"); // fails
    }
}
