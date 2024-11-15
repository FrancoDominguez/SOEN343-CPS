package cps.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShippingStatus {
  private int id;
  private int deliveryId;
  private LocalDateTime eta;
  private ArrayList<Location> travelPath;
  private int currentLocationIndex;

  public ShippingStatus() {
  }

  public ShippingStatus(int deliveryId, LocalDateTime eta, ArrayList<Location> travelPath) {
    this.id = -1;
    this.deliveryId = deliveryId;
    this.eta = eta;
    this.travelPath = travelPath;
    this.currentLocationIndex = 0;
  }

  public ShippingStatus(int id, int deliveryId, LocalDateTime eta, ArrayList<Location> travelPath,
      int currentLocationIndex) {
    this.id = id;
    this.deliveryId = deliveryId;
    this.eta = eta;
    this.travelPath = travelPath;
    this.currentLocationIndex = currentLocationIndex;
  }

  public int getId() {
    return this.id;
  }

  public int getDeliveryId() {
    return this.deliveryId;
  }

  public LocalDateTime getEta() {
    return this.eta;
  }

  public ArrayList<Location> getTravelPath() {
    return this.travelPath;
  }

  public void goNext() {
    this.currentLocationIndex++;
  }

  public Location getCurrentLocation() {
    return this.travelPath.get(this.currentLocationIndex);
  }

  public String getStatus() {
    if (this.currentLocationIndex == 0) {
      return "Delivery is being processed";
    } else if (this.currentLocationIndex == this.travelPath.size()) {
      return "Order has been delivered";
    } else {
      return "Order is in transit";
    }
  }

  public Boolean isDelivered() {
    return (this.currentLocationIndex == this.travelPath.size());
  }
}
