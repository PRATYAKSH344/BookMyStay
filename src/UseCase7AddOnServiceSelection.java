import java.util.*;

/**
 * ============================================================
 * Use Case 7: Add-On Service Selection
 * ============================================================
 *
 * Demonstrates adding optional services to reservations
 *
 * @version 7.1
 */

// ---------------- SERVICE CLASS ----------------
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

// ---------------- SERVICE MANAGER ----------------
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation " + reservationId + ":");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService service : services) {
            service.displayService();
        }
    }

    // Calculate total service cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection System\n");

        // Assume reservation IDs from UC6
        String res1 = "S-1";
        String res2 = "D-2";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa Access", 1500);

        // Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);

        manager.addService(res2, spa);

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);

        // Show total cost
        System.out.println("\nTotal Add-On Cost for " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));

        System.out.println("Total Add-On Cost for " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));
    }
}