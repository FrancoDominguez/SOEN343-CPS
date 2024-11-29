package cps.DomainLayer;

import java.util.ArrayList;

import cps.DTO.RequestBodies.ContractRequestBody;
import cps.DTO.RequestBodies.ReviewRequestBody;
import cps.DAO.ContractDAO;
import cps.DAO.ReviewDAO;
import cps.DAO.StationDAO;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.HomePickup;
import cps.DomainLayer.models.Review;
import cps.DomainLayer.models.ShippingStatus;
import cps.DomainLayer.models.Station;
import cps.DomainLayer.models.StationDropoff;
import cps.DomainLayer.models.Interfaces.OrderTracker;

public class ClientService implements OrderTracker {

  // @Override
  // public ShippingStatus trackOrder(int trackingId) {
  // // Use DeliveryDAO to fetch the delivery by trackingId
  // DeliveryDAO deliveryDAO = new DeliveryDAO();
  // Delivery delivery = deliveryDAO.fetchByTrackingId(trackingId);

  // if (delivery == null) {
  // throw new IllegalArgumentException("Invalid tracking ID: " + trackingId);
  // }

  // // Return the ShippingStatus object from the delivery
  // return delivery.getStatus();
  // }

  public ShippingStatus trackOrder(int trackingId) {
    return null;
  }

  public Contract addNewContract(ContractRequestBody contractInfo) throws Exception {
    Contract newContract = null;
    StationDAO stationDAO = new StationDAO();
    if (contractInfo.isHomePickup()) {
      newContract = new HomePickup(contractInfo.getClientId(), contractInfo.getParcel(),
          contractInfo.getDestination(), contractInfo.getSignatureRequired(), contractInfo.getHasPriority(),
          contractInfo.getWarrantedAmount(), contractInfo.getOrigin(), contractInfo.getPickupTime(),
          contractInfo.getIsFlexible());
    } else {
      Station station = stationDAO.fetchLocation(contractInfo.getStationId());
      System.out.println(station);
      newContract = new StationDropoff(contractInfo.getClientId(), contractInfo.getParcel(),
          contractInfo.getDestination(), contractInfo.getSignatureRequired(), contractInfo.getHasPriority(),
          contractInfo.getWarrantedAmount(), station);
    }
    newContract.processQuote();
    newContract.save();
    return newContract;
  }

  public ArrayList<Station> getAllStations() {
    StationDAO stationDAO = new StationDAO();
    ArrayList<Station> stations = stationDAO.fetchAllStations();
    return stations;
  }

  // Franco
  public ArrayList<Contract> viewAllActiveContracts(int clientId) {
    ContractDAO contractDAO = new ContractDAO();
    ArrayList<Contract> contracts = contractDAO.fetchAllByClientId(clientId);
    return contracts;
  }

  // Franco
  public void updateContract(String key, String value) {
  }

  // Franco
  public void deleteContract(int contractId) {
  }

  public int createDelivery(Contract contract) {
    Delivery newDelivery = new Delivery(contract);
    int contractId = newDelivery.save();
    return contractId;
  }

  public Review createReview(ReviewRequestBody reviewInfo) throws Exception {
    Review newReview = null;
    ReviewDAO reviewDAO = new ReviewDAO();
    if (reviewInfo.getRating() < 1 || reviewInfo.getRating() > 5) {
        throw new Exception("Rating must be between 1 and 5");
    }
    if (reviewInfo.getTrackingId() <= 0) {
        throw new Exception("Invalid Tracking ID");
    }
    newReview = new Review(
        reviewInfo.getId(),
        reviewInfo.getTrackingId(),
        reviewInfo.getRating(),
        reviewInfo.getComment()
    );
    // Save the review to the database
    reviewDAO.insert(newReview);

    return newReview;
}


  public ArrayList<Delivery> viewAllActiveDeliveries() {
    return null;
  }

  public ArrayList<Delivery> viewPendingDeliveries() {
    return null;
  }

  public ArrayList<Delivery> viewInTransitDeliveries() {
    return null;
  }

  public ArrayList<Delivery> viewCompletedDeliveries() {
    return null;
  }

}
