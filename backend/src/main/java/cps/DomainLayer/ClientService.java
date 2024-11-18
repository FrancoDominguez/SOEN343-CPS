package cps.DomainLayer;

import java.util.ArrayList;

import cps.models.Contract;
import cps.models.Delivery;
import cps.models.ShippingStatus;
import cps.models.Interfaces.OrderTracker;

public class ClientService implements OrderTracker {
  public ShippingStatus trackOrder(int trackingId) {
    return null;
  }

  public void createNewContract() {
  }

  public ArrayList<Contract> viewAllActiveContracts() {
    return null;
  }

  public void updateContract(String key, String value) {
  }

  public void deleteContract(int contractId) {
  }

  public void createDelivery(Contract contract) {
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
