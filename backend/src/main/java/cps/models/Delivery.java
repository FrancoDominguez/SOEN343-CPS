package cps.models;

public class Delivery extends Quotation {
  private ShippingStatus shippingStatus;

  public Delivery(int clientId, Parcel parcel, Departure departure, Location destination, Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    super(clientId, parcel, departure, destination, hasPriority, warrantedAmount, signatureRequired);
    this.shippingStatus = new ShippingStatus();
  }

  public Delivery(int id, int clientId, Parcel parcel, Departure departure, Location destination,
      Boolean hasPriority,
      double warrantedAmount, Boolean signatureRequired) {
    super(id, clientId, parcel, departure, destination, hasPriority, warrantedAmount, signatureRequired);
    this.shippingStatus = new ShippingStatus();
  }

  public void deliver() {
    // modify shipping status
  }

  public Boolean isCompleted() {
    return this.shippingStatus.isDelivered();
  }
}
