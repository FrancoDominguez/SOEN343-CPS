package cps.DomainLayer;

import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.ShippingStatus;
import cps.DomainLayer.models.Interfaces.OrderTracker;

public class GuestService implements OrderTracker {
  public Delivery trackOrder(int trackingId) {
    return null;
  }

  public void login() {
  }

  public void signup() {
  }
}
