package cps.models;

public class StationDropoff extends Departure {
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
