package cps.DomainLayer;

import java.util.ArrayList;

import cps.DTO.RequestBodies.ContractRequestBody;
import cps.DTO.RequestBodies.ReviewRequestBody;
import cps.DAO.ContractDAO;
import cps.DAO.DeliveryDAO;
import cps.DAO.ShippingStatusDAO;
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
  ContractDAO contractDAO = new ContractDAO();
  DeliveryDAO deliveryDAO = new DeliveryDAO();

  public Delivery trackOrder(int trackingId) {
    ShippingStatusDAO statusDAO = new ShippingStatusDAO();
    Delivery delivery = statusDAO.trackOrder2(trackingId);
    return delivery;
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
    ArrayList<Contract> contracts = contractDAO.fetchAllByClientId(clientId);
    return contracts;
  }

  // Franco
  public void updateContract(String key, String value) {
  }

  public void deleteContract(int contractId) {
    contractDAO.delete(contractId);
  }

  public int createDelivery(Contract contract) {
    Delivery newDelivery = new Delivery(contract);
    this.deleteContract(contract.getId());
    int contractId = newDelivery.save();
    return contractId;
  }

  public static void createReview(ReviewRequestBody reviewRequest) throws Exception {

    ReviewDAO reviewDAO = new ReviewDAO();
    Review review = new Review(
        reviewRequest.getComment(),
        reviewRequest.getRating()
    );

    reviewDAO.insert(review); // Save the review to the database
}



  public ArrayList<Delivery> viewAllActiveDeliveries(int clientId) {
    ArrayList<Delivery> deliveries = deliveryDAO.fetchAllByClientId(clientId);
    return deliveries;
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

  public void updatePickupTime(int deliveryId, String newTime){
    System.out.println("Update pickup time of " + deliveryId);
    deliveryDAO.updatePickupTime(deliveryId, newTime);
  }

}
