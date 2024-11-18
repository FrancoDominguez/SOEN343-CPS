package cps.models;

import java.time.Duration;
import java.time.LocalDateTime;

import cps.DAO.ContractDAO;

public class StationDropoff extends Contract {
  private Station station;

  public StationDropoff(int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount, Station station) {
    super(clientId, parcel, destination, signatureRequired, hasPriority, warrantedAmount);
    this.station = station;
  }

  public StationDropoff(int id, int clientId, Parcel parcel, Location destination, Boolean signatureRequired,
      Boolean hasPriority, double warrantedAmount, double price, Duration eta, Station station) {
    super(id, clientId, parcel, destination, signatureRequired, hasPriority, warrantedAmount, price, eta);
    this.station = station;
  }

  public Station getStation() {
    return this.station;
  }

  public void setStation(Station station) {
    this.station = station;
  }

  public void save() {
    ContractDAO cDAO = new ContractDAO();
    if (this.id == -1) {
      cDAO.insert(this);
    } else {
      cDAO.insert(this);
    }
  }

  public void processQuote() {
  };

  protected double calculatePrice() {
    return 0;
  };

  protected LocalDateTime calculateEta() {
    return null;
  };
}