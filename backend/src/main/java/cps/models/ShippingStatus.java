package cps.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShippingStatus {
    private final int id; // Immutable
    private final ArrayList<Location> travelPath; // Immutable reference
    private int currentLocationIndex;

    public enum Status {
        PENDING,
        IN_TRANSIT,
        DELIVERED
    }
    public ShippingStatus() {
      this.id = -1; // Default ID
      this.travelPath = new ArrayList<>(); // Empty travel path
      this.currentLocationIndex = 0; // Start at the beginning
  }
    // Constructor
    public ShippingStatus(int id, ArrayList<Location> travelPath) {
        this.id = id;
        this.travelPath = travelPath != null ? travelPath : new ArrayList<>();
        this.currentLocationIndex = 0; // Start at the beginning
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public ArrayList<Location> getTravelPath() {
        return this.travelPath;
    }

    public int getCurrentLocationIndex() {
        return this.currentLocationIndex;
    }

    // Methods
    public void goNext() {
        if (currentLocationIndex < travelPath.size() - 1) {
            this.currentLocationIndex++;
        } else {
            throw new IllegalStateException("Already at the final destination");
        }
    }

    public Status getStatus() {
        if (this.currentLocationIndex == 0) {
            return Status.PENDING;
        } else if (this.currentLocationIndex == this.travelPath.size() - 1) {
            return Status.DELIVERED;
        } else {
            return Status.IN_TRANSIT;
        }
    }

    public String getStatusString() {
        return getStatus().name().toLowerCase();
    }

    public LocalDateTime getEta() {
        if (currentLocationIndex >= travelPath.size() - 1) {
            return null; // Already delivered
        }

        int remainingStops = travelPath.size() - 1 - currentLocationIndex;
        int timePerStopMinutes = 30; // Example: each stop takes 30 minutes
        return LocalDateTime.now().plusMinutes(remainingStops * timePerStopMinutes);
    }

    public boolean isAtLocation(Location location) {
        return travelPath != null && !travelPath.isEmpty() && 
               travelPath.get(currentLocationIndex).equals(location);
    }

    public void reset() {
        this.currentLocationIndex = 0;
    }
}
