package cps.models;

public class Delivery {
    private int id;
    private int clientId;
    private int trackingId;
    private Parcel parcel;
    private Location destination;
    private Boolean signatureRequired;
    private Boolean hasPriority;
    private ShippingStatus status; // Use ShippingStatus model

    // Constructor to initialize from a Contract
    public Delivery(Contract contract) {
        this.clientId = contract.getClientId();
        this.parcel = contract.getParcel();
        this.destination = contract.getDestination();
        this.signatureRequired = contract.signatureRequired();
        this.hasPriority = contract.hasPriority();
        this.status = new ShippingStatus(); // Initialize with a new ShippingStatus
    }

      //CHange the is flexibile with instanceof for home pickup
      
   

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(int trackingId) {
        this.trackingId = trackingId;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Boolean isSignatureRequired() {
        return signatureRequired;
    }

    public void setSignatureRequired(Boolean signatureRequired) {
        this.signatureRequired = signatureRequired;
    }

    public Boolean hasPriority() {
        return hasPriority;
    }

    public void setHasPriority(Boolean hasPriority) {
        this.hasPriority = hasPriority;
    }

    public ShippingStatus getStatus() {
        return this.status;
    }

    public void setStatus(ShippingStatus status) {
        this.status = status;
    }

    // Debugging representation
    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", trackingId=" + trackingId +
                ", parcel=" + parcel +
                ", destination=" + destination +
                ", signatureRequired=" + signatureRequired +
                ", hasPriority=" + hasPriority +
                ", status=" + status.getStatus() + // Display current status
                '}';
    }
}
