package cps.DTO.RequestBodies;

import java.time.Duration;
import java.time.LocalDateTime;

import cps.models.Location;
import cps.models.Parcel;
import cps.models.Station;

public class ContractRequestBody extends AuthenticatedRequestBody {
  private int clientId;
  private Parcel parcel;
  private Location destination;
  private Boolean signatureRequired;
  private Boolean hasPriority;
  private double warrantedAmount;
  private Location origin;
  private LocalDateTime pickupTime;
  private Boolean isFlexible;
  private Station station;

  public int getClientId() {
    return clientId;
  }

  public Parcel getParcel() {
    return parcel;
  }

  public Location getDestination() {
    return destination;
  }

  public Boolean getSignatureRequired() {
    return signatureRequired;
  }

  public Boolean getHasPriority() {
    return hasPriority;
  }

  public double getWarrantedAmount() {
    return warrantedAmount;
  }

  public Location getOrigin() {
    return origin;
  }

  public LocalDateTime getPickupTime() {
    return pickupTime;
  }

  public Boolean getIsFlexible() {
    return isFlexible;
  }

  public Station getStation() {
    return station;
  }

  public Boolean isHomePickup() {
    return (this.station == null);
  }
}