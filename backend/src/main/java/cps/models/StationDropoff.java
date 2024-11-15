package cps.models;

public class StationDropoff extends Origin {
  public StationDropoff(Location station, int quotationId) {
    super(station, quotationId);
  }

  public void selectNewStation(Location newStation) {
    this.location = newStation;
  }

  public Location getStation() {
    return this.location;
  }
}
