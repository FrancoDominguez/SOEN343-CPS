package cps.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShippingStatus {
  private int id;
  private ArrayList<Location> travelPath;
  private int currentLocationIndex;

  public int getId() {
    return this.id;
  }

  public ArrayList<Location> getTravelPath() {
    return this.travelPath;
  }

  public int getCurrentLocationIndex() {
    return this.currentLocationIndex;
  }

  public void goNext() {
    this.currentLocationIndex++;
  }

  public String getStatus() {
    if (this.currentLocationIndex == 0)
      return "pending";
    else if (this.currentLocationIndex == this.travelPath.size() - 1)
      return "delivered";
    else
      return "in transit";
  }

  public LocalDateTime getEta() {
    // implement getEta logic
    return null;
  }
}
