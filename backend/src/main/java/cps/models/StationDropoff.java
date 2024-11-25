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

  public int save() {
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
    return this.id;
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