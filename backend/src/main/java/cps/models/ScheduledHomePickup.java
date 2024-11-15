package cps.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class ScheduledHomePickup extends HomePickup {
  private LocalDateTime pickupTime;

  public ScheduledHomePickup(Location station, int quotationId, LocalDateTime pickupTime) {
    super(station, quotationId);
    this.pickupTime = pickupTime;
  }

  public void updatePickupTime(LocalDateTime newPickupTime) {
    this.pickupTime = newPickupTime;
  }

  public Duration getTimeUntilPickup() {
    return Duration.between(LocalDateTime.now(), pickupTime);
  }
}