package cps.models.Interfaces;

import cps.models.ShippingStatus;

public interface OrderTracker {
  public ShippingStatus trackOrder(int trackingId);
}
