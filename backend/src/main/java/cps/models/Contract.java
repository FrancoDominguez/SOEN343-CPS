package cps.models;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Contract {
  protected int id;
  protected int clientId;
  protected Parcel parcel;
  protected Location destination;
  protected Boolean signatureRequired;
  protected Boolean hasPriority;
  protected double warrantedAmount;
  protected double price;
  protected Duration eta;

  public Contract(int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount) {
    this.id = -1;
    this.clientId = clientId;
    this.parcel = parcel;
    this.destination = destination;
    this.signatureRequired = signatureRequired;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
  }

  public Contract(int id, int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount, double price, Duration eta) {
    this.id = id;
    this.clientId = clientId;
    this.parcel = parcel;
    this.destination = destination;
    this.signatureRequired = signatureRequired;
    this.hasPriority = hasPriority;
    this.warrantedAmount = warrantedAmount;
    this.price = price;
    this.eta = eta;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getClientId() {
    return this.clientId;
  }

  public void setClientId(int clientId) {
    this.clientId = clientId;
  }

  public Parcel getParcel() {
    return this.parcel;
  }

  public void setParcel(Parcel parcel) {
    this.parcel = parcel;
  }

  public Location getDestination() {
    return this.destination;
  }

  public void setDestination(Location destination) {
    this.destination = destination;
  }

  public Boolean signatureRequired() {
    return this.signatureRequired;
  }

  public void setSignatureRequired(Boolean signatureRequired) {
    this.signatureRequired = signatureRequired;
  }

  public Boolean hasPriority() {
    return this.hasPriority;
  }

  public void setHasPriority(Boolean hasPriority) {
    this.hasPriority = hasPriority;
  }

  public double getPrice() {
    return this.price;
  }

  public Duration getEta() {
    return this.eta;
  }

  public double getWarrantedAmount() {
    return this.warrantedAmount;
  }

  public abstract void save();

  public abstract void processQuote();

  protected abstract double calculatePrice();

  protected abstract LocalDateTime calculateEta();

  @Override
  public String toString() {
    return "Contract{\n" +
        "  id=" + id + ",\n" +
        "  clientId=" + clientId + ",\n" +
        "  parcel=" + parcel + ",\n" +
        "  destination=" + destination + ",\n" +
        "  signatureRequired=" + signatureRequired + ",\n" +
        "  hasPriority=" + hasPriority + ",\n" +
        "  warrantedAmount=" + warrantedAmount + ",\n" +
        "  price=" + price + ",\n" +
        "  eta=" + eta + "\n" +
        '}';
  }
}
