package cps.models;

import java.time.Duration;
import java.time.LocalDateTime;

import cps.DAO.ContractDAO;

public class HomePickup extends Contract {
  private Location origin;
  private LocalDateTime pickupTime;
  private Boolean isFlexible;

  public HomePickup(int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount, Location origin, LocalDateTime pickupTime, Boolean isFlexible) {
    super(clientId, parcel, destination, signatureRequired, hasPriority, warrantedAmount);
    this.origin = origin;
    this.isFlexible = isFlexible;
    this.pickupTime = pickupTime;
  }

  public HomePickup(int id, int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount, double price, Duration eta, Location origin,
      LocalDateTime pickupTime, Boolean isFlexible) {
    super(id, clientId, parcel, destination, signatureRequired, hasPriority, warrantedAmount, price, eta);
    this.origin = origin;
    this.isFlexible = isFlexible;
    this.pickupTime = pickupTime;
  }

  public Location getOrigin() {
    return this.origin;
  }

  public LocalDateTime getPickupTime() {
    return this.pickupTime;
  }

  public Boolean isFlexible() {
    return this.isFlexible;
  }

  public void setOrigin(Location location) {
    this.origin = location;
  }

  public void setPickupTime(LocalDateTime dateTime) {
    this.pickupTime = dateTime;
  }

  public void setIsFlexible(Boolean bool) {
    this.isFlexible = bool;
  }

  public void save() {
    ContractDAO contractDAO = new ContractDAO();
    if (this.id == -1) {
      this.id = contractDAO.insert(this);
    } else {
      try {
        contractDAO.update(this);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void processQuote() {
    this.price = 50.00;
    this.eta = Duration.ofDays(2);
  };

  protected double calculatePrice() {
    return 0;
  };

  protected LocalDateTime calculateEta() {
    return null;
  };
}
