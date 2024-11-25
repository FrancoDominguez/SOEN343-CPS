package cps.DomainLayer;

import java.util.ArrayList;

import cps.DAO.ContractDAO;
import cps.DTO.RequestBodies.ContractRequestBody;
import cps.models.Contract;
import cps.models.Delivery;
import cps.models.HomePickup;
import cps.models.ShippingStatus;
import cps.models.Station;
import cps.models.StationDropoff;
import cps.models.Interfaces.OrderTracker;

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
    if (contractInfo.isHomePickup()) {
      newContract = new HomePickup(contractInfo.getClientId(), contractInfo.getParcel(),
          contractInfo.getDestination(), contractInfo.getSignatureRequired(), contractInfo.getHasPriority(),
          contractInfo.getWarrantedAmount(), contractInfo.getOrigin(), contractInfo.getPickupTime(),
          contractInfo.getIsFlexible());
    } else {
      newContract = new StationDropoff(contractInfo.getClientId(), contractInfo.getParcel(),
          contractInfo.getDestination(), contractInfo.getSignatureRequired(), contractInfo.getHasPriority(),
          contractInfo.getWarrantedAmount(), contractInfo.getStation());
    }
    newContract.processQuote();
    newContract.save();
    return newContract;
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

  public void createDelivery(Contract contract) {
    Delivery newDelivery = new Delivery(contract);
    newDelivery.save();
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
