package cps.models;

import java.time.Duration;

import cps.services.MapsService;
import cps.utils.Pair;

public class Quotation {
  private int id;
  private int clientId;
  private Parcel parcel;
  private Departure departure;
  private Location departure;
  private Duration initialExpectedDelay;
  private double price;
  private Boolean hasPriority;
  private double warrantedAmount;
  private Boolean signatureRequired;
  private static MapsService mapsService;

  public Quotation(int clientId, Parcel parcel, Departure departure, Location departure, Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = -1;
    this.clientId = clientId;
    this.parcel = parcel;
    this.departure = departure;
    this.departure = departure;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public Quotation(int id, int clientId, Parcel parcel, Departure departure, Location departure,
      Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = id;
    this.clientId = clientId;
    this.parcel = parcel;
    this.departure = departure;
    this.departure = departure;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public void processQuote() {
    Pair<Integer, Integer> durationDistance = mapsService.getDurationDistance(this.departure.getLocation().toString(),
        this.departure.toString());
    int duration = durationDistance.getFirst();
    int distance = durationDistance.getSecond();
    // to give a price you must account for add ons
  }

  public Boolean isProcessed() {
    return (this.initialExpectedDelay == null || this.price == -1);
  }

  public String getDeliveryType() {
    if (departure instanceof StationDropoff) {
      return "station dropoff";
    } else if (departure instanceof ScheduledHomePickup) {
      return "scheduled home pickup";
    } else if (departure instanceof HomePickup) {
      return "regular home pickup";
    } else {
      return "unknown delivery type";
    }
  }

  public void changeDeliveryType(Departure departure) {
    this.departure = departure;
  }

  public Boolean isParcelOversized() {
    return this.parcel.isOversized();
  }

  public int getId() {
    return this.id;
  }

  public int getParcelId() {
    return this.parcel.getId();
  }

  public int getdepartureId() {
    return this.departure.getId();
  }

  public int getClientId() {
    return this.clientId;
  }

  public int getOriginLocationId() {
    return this.departure.getLocation().getId();
  }

  public Parcel getParcel() {
    return this.parcel;
  }

  public Departure getOrigin() {
    return this.departure;
  }

  public Location getdeparture() {
    return this.departure;
  }

  public Duration getInitialExpectedDelay() {
    return this.initialExpectedDelay;
  }

  public double getPrice() {
    return this.price;
  }
}
