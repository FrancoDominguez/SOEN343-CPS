package cps.models;

public class FlexibleDelivery extends Delivery {
    private boolean isFlexible; // Indicates if the delivery is flexible

    // Constructor to initialize a FlexibleDelivery from a Contract
    public FlexibleDelivery(Contract contract, boolean isFlexible) {
        super(contract); // Call the parent class constructor
        this.isFlexible = isFlexible;
    }

    // Getter and Setter for isFlexible
    public boolean isFlexible() {
        return isFlexible;
    }

    public void setFlexible(boolean isFlexible) {
        this.isFlexible = isFlexible;
    }

    // Override the toString method to include flexibility
    @Override
    public String toString() {
        return "FlexibleDelivery{" +
                "id=" + getId() +
                ", clientId=" + getClientId() +
                ", trackingId=" + getTrackingId() +
                ", parcel=" + getParcel() +
                ", destination=" + getDestination() +
                ", signatureRequired=" + isSignatureRequired() +
                ", hasPriority=" + hasPriority() +
                ", status=" + getStatus().getStatus() +
                ", isFlexible=" + isFlexible +
                '}';
    }
}

