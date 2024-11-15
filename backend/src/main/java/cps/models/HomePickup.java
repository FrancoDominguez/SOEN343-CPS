package cps.models;

public class HomePickup extends Departure {
  public HomePickup(Location station, int quotationId) {
    super(station, quotationId);
  }

  public Location getHomeAddress() {
    return this.location;
  }
}