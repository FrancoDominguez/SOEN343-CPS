package cps.models;

public class Delivery {
  private int id;
  private int ClientId;
  private int trackingId;
  private Parcel parcel;
  private Location Destination;
  private Boolean signatureRequired;
  private Boolean hasPriority;
  private ShippingStatus shippingStatus;

  public Delivery(Contract contract) {
  }
}
