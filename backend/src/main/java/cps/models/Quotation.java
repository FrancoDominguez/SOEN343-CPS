package cps.models;

import java.math.BigDecimal;

import static cps.services.MapsService.getDurationDistance;
import cps.utils.Pair;

public class Quotation {
  private static long counter = 1;
  private long id;
  private User user;
  private Address origin;
  private Address destination;
  private Package packageUnit;
  private int distanceInMeters;
  private int eta;
  private BigDecimal price;


  public Quotation(BigDecimal price) {
    this.price = price;
    this.id = counter++;
}


  public Quotation(User user, Address origin, Address destination, Package packageUnit) {
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

  public long getId() {
    return id;
}

  private int determineETA() {
    return 0;
  }

  private BigDecimal determinePrice() {
    return null;
  }

  public BigDecimal getPrice() {
    return price;
}
public void setPrice(BigDecimal price) {
  this.price = price;
}

  public String toString() {
    return null;
  }
}
