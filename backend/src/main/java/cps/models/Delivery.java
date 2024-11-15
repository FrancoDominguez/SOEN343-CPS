package cps.models;

public class Delivery extends Quotation {
  private ShippingStatus shippingStatus;

  public Delivery(int clientId, Parcel parcel, Origin origin, Location destination, ShippingStatus shippingStatus) {
    super(clientId, parcel, origin, destination);
    this.shippingStatus = shippingStatus;
  }

  public Delivery(int id, int clientId, Parcel parcel, Origin origin, Location destination,
      ShippingStatus shippingStatus) {
    super(id, clientId, parcel, origin, destination);
    this.shippingStatus = shippingStatus;
  }

  public void deliver() {
    // modify shipping status
  }

  public Boolean isCompleted() {
    return this.shippingStatus.isDelivered();
  }
}
