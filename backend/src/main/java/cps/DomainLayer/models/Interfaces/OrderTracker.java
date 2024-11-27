package cps.DomainLayer.models.Interfaces;

import cps.DomainLayer.models.ShippingStatus;

public interface OrderTracker {
  public ShippingStatus trackOrder(int trackingId);
}
