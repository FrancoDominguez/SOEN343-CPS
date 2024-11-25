package cps.DomainLayer.models;

import static cps.utils.MapsService.getDurationDistance;

import java.math.BigDecimal;

import cps.utils.Pair;

public class Quotation {
  private Client user;
  private Address origin;
  private Address destination;
  private Package packageUnit;
  private int distanceInMeters;
  private int eta;
  private BigDecimal price;

  public Quotation(Client user, Address origin, Address destination, Package packageUnit) {
    this.user = user;
    this.origin = origin;
    this.destination = destination;
    this.packageUnit = packageUnit;
    Pair<Integer, Integer> durationDistance = getDurationDistance(origin.toString(), destination.toString());
    // determine algo to get eta from below variable
    int durationInSeconds = durationDistance.getFirst();
    this.distanceInMeters = durationDistance.getSecond();
    this.eta = determineETA();
    this.price = determinePrice();
    this.save();
  }

  public void save() {
  }

  private int determineETA() {
    return 0;
  }

  private BigDecimal determinePrice() {
    return null;
  }

  public String toString() {
    return null;
  }
}
