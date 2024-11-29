package cps.DomainLayer.models.Interfaces;

import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.ShippingStatus;

public interface OrderTracker {
  public Delivery trackOrder(int trackingId);
}
