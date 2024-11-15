package cps.models;

import java.time.Duration;

import cps.services.MapsService;
import cps.utils.Pair;

public class Quotation {
  private int id;
  private int clientId;
  private Parcel parcel;
  private Origin origin;
  private Location destination;
  private Duration initialExpectedDelay;
  private double price;
  private Boolean hasPriority;
  private double warrantedAmount;
  private Boolean signatureRequired;
  private static MapsService mapsService;

  public Quotation(int clientId, Parcel parcel, Origin origin, Location destination, Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = -1;
    this.clientId = clientId;
    this.parcel = parcel;
    this.origin = origin;
    this.destination = destination;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public Quotation(int id, int clientId, Parcel parcel, Origin origin, Location destination, Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    this.id = id;
    this.clientId = clientId;
    this.parcel = parcel;
    this.origin = origin;
    this.destination = destination;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.signatureRequired = signatureRequired;
    this.initialExpectedDelay = null;
    this.price = -1;
  }

  public void processQuote() {
    Pair<Integer, Integer> durationDistance = mapsService.getDurationDistance(this.origin.getLocation().toString(),
        this.destination.toString());
    int duration = durationDistance.getFirst();
    int distance = durationDistance.getSecond();
    // to give a price you must account for add ons
  }

  public Boolean isProcessed() {
    return (this.initialExpectedDelay == null || this.price == -1);
  }

  public String getDeliveryType() {
    if (origin instanceof StationDropoff) {
      return "Station Dropoff";
    } else if (origin instanceof ScheduledHomePickup) {
      return "Scheduled Home Pickup";
    } else if (origin instanceof HomePickup) {
      return "Regular Home Pickup";
    } else {
      return "Unknown Delivery Type";
    }
  }

  public void changeDeliveryType(Origin origin) {
    this.origin = origin;
  }

  public int getId() {
    return this.id;
  }

  public int getClientId() {
    return this.clientId;
  }

  public Parcel getParcel() {
    return this.parcel;
  }

  public Origin getOrigin() {
    return this.origin;
  }

  public Location getDestination() {
    return this.destination;
  }

  public Duration getInitialExpectedDelay() {
    return this.initialExpectedDelay;
  }

  public double getPrice() {
    return this.price;
  }
}
