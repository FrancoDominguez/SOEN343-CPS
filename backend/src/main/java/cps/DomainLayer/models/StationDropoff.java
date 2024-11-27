package cps.DomainLayer.models;

import java.time.Duration;
import java.time.LocalDateTime;
import static cps.utils.MapsService.getDurationDistance;

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
    calculatePrice();
    calculateEta();
    
  };

  @Override
    protected double calculatePrice() {
        double price = 0.0;

        // Distance calculation logic (in meters)
        Location stationAdress = (Location) this.station;
        int distance = getDurationDistance(stationAdress.toString(), this.destination.toString());

        // Base price based on distance
        if (distance <= 5000) { // Up to 5 km
            price += 10;
        } else if (distance <= 20000) { // 5 to 20 km
            price += 20;
        } else if (distance <= 50000) { // 20 to 50 km
            price += 35;
        } else { // Above 50 km
            price += 50;
        }

        // Add $5 for each condition met
        if (this.signatureRequired()) {
            price += 5;
        }
        if (this.hasPriority()) {
            price += 5;
        }
        if (this.warrantedAmount > 1000) {
            price += 5;
        }

       

        return price;
    }

    @Override
    protected LocalDateTime calculateEta() {
        // Base delivery time is 3 days
        int daysToAdd = 3;

        // If priority shipping is selected, reduce by 1 day
        if (this.hasPriority()) {
            daysToAdd = 2;
        }

        return LocalDateTime.now().plusDays(daysToAdd);
    }

    
}

