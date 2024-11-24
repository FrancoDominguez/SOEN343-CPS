package cps.DomainLayer;

import cps.models.ShippingStatus;
import cps.models.Interfaces.OrderTracker;

public class GuestService implements OrderTracker {
  public ShippingStatus trackOrder(int trackingId) {
    return null;
  }

  public void login() {
  }

  public void signup() {
  }
}
