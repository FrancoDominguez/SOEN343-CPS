package cps.models;

import static cps.utils.MapsService.getDurationDistance;

import java.time.Duration;

import cps.services.MapsService;
import cps.utils.Pair;

public class Quotation {
  private int id;
  private int clientId;
  private Parcel parcel;
  private Departure departure;
  private Location destination;
  private Duration initialExpectedDelay;
  private double price;
  private Boolean hasPriority;
  private double warrantedAmount;
  private Boolean signatureRequired;
  private static MapsService mapsService;

  public Quotation(int clientId, Parcel parcel, Departure departure, Location destination, Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = -1;
    this.clientId = clientId;
    this.parcel = parcel;
    this.departure = departure;
    this.destination = destination;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public Quotation(int id, int clientId, Parcel parcel, Departure departure, Location destination,
      Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = id;
    this.clientId = clientId;
    this.parcel = parcel;
    this.departure = departure;
    this.destination = destination;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public void processQuote() {
    Pair<Integer, Integer> durationDistance = mapsService.getDurationDistance(this.departure.getLocation().toString(),
        this.destination.toString());
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

  public int getDestinationLocationId() {
    return this.destination.getId();
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

  public Location getDestinationLocation() {
    return this.destination;
  }

  public Duration getInitialExpectedDelay() {
    return this.initialExpectedDelay;
  }

  public double getPrice() {
    return this.price;
  }
}
