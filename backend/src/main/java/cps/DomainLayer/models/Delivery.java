package cps.DomainLayer.models;

import java.time.LocalDateTime;

import cps.DAO.*;

public class Delivery {
    private int id;
    private int clientId;
    private int trackingId;
    private Parcel parcel;
    private Location destination;
    private Boolean signatureRequired;
    private Boolean hasPriority;
    private ShippingStatus status; // Simplified ShippingStatus
    private Boolean isFlexible; // Indicates if the delivery is flexible
    private LocalDateTime pickupTime; // For flexible deliveries
    private Location pickupLocation; // For flexible deliveries

    // Constructor for HomePickup and StationDropoff contracts
    public Delivery(Contract contract) {
        this.clientId = contract.getClientId();
        this.parcel = contract.getParcel();
        this.destination = contract.getDestination();
        this.signatureRequired = contract.signatureRequired();
        this.hasPriority = contract.hasPriority();
        this.status = new ShippingStatus(); // Default to pending
        this.isFlexible = (contract instanceof HomePickup) && ((HomePickup) contract).isFlexible();

        // Set additional fields if it's a HomePickup contract
        if (contract instanceof HomePickup) {
            HomePickup homePickup = (HomePickup) contract;
            this.pickupTime = homePickup.getPickupTime();
            this.pickupLocation = homePickup.getOrigin();
        }
    }

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

    public Boolean isFlexible() {
        return isFlexible;
    }

    public void setFlexible(Boolean flexible) {
        isFlexible = flexible;
    }

    public LocalDateTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDateTime pickupTime) {
        if (this.isFlexible && this.status.isPending()) {
            this.pickupTime = pickupTime;
        } else {
            throw new IllegalStateException(
                    "Cannot update pickup time. Delivery is not flexible or not in pending status.");
        }
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        if (this.isFlexible && this.status.isPending()) {
            this.pickupLocation = pickupLocation;
        } else {
            throw new IllegalStateException(
                    "Cannot update pickup location. Delivery is not flexible or not in pending status.");
        }
    }

    public void save() {
        DeliveryDAO deliveryDAO = new DeliveryDAO();
        if (this.getId() == -1) {
            // Insert new Delivery and assign the generated ID
            this.id = deliveryDAO.insert(5); // Update the Delivery object with the generated ID
        } else {
            try {
                // Update existing Delivery
                deliveryDAO.update(this);
            } catch (Exception e) {
                System.out.println("Error updating delivery: " + e.getMessage());
            }
        }
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
                ", status=" + status.getStatusString() +
                ", isFlexible=" + isFlexible +
                ", pickupTime=" + pickupTime +
                ", pickupLocation=" + pickupLocation +
                '}';
    }
}
