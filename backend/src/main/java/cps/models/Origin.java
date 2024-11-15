package cps.models;

public abstract class Origin {
  public Location location;
  public int clientId;

  public Origin(Location location, int clientId) {
    this.location = location;
    this.clientId = clientId;
  }

  public Location getLocation() {
    return this.location;
  }
}
