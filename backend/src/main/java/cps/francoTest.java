package cps;

import java.time.LocalDateTime;
import java.util.ArrayList;

import cps.DAO.ClientDAO;
import cps.DAO.ContractDAO;

import cps.DAO.StationDAO;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.ClientModel;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.HomePickup;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.Station;
import cps.DomainLayer.models.StationDropoff;
import cps.utils.Mysqlcon;

public class francoTest {

  public static void testDAOs() {
    try {
      // System.out.println("Clearing all tables\n");
      // clearTable("contracts");
      // clearTable("locations");
      // clearTable("parcels");
      // clearTable("shipping_status");
      // System.out.println("All tables are now clear\n");

      ContractDAO contractDAO = new ContractDAO();

      ClientDAO clientDAO = new ClientDAO();
      ClientModel clientObj = clientDAO.fetchByEmail("hoboslime@gmail.com");

      StationDAO stationDAO = new StationDAO();
      Station station = stationDAO.fetchLocation(1);
      Location origin = new Location("1081 Caledonia Rd", "Mount Royal", "QC", "H3R 2V6");
      Location destination = new Location("484 Rue des Alismas", "Laval", "QC", "H7X 4G9");
      Parcel parcel = new Parcel(10.0, 10.0, 10.0, 10.0, true);

      // creating contract object
      HomePickup pickupContract = new HomePickup(clientObj.getId(), parcel, destination, true, true, 100.00, origin,
          LocalDateTime.now(), false);
      StationDropoff stationContract = new StationDropoff(clientObj.getId(), parcel, destination, true, true, 100.00,
          station);

      System.out.println("\nprocessing and saving station dropoff contract\n");
      stationContract.processQuote();
      int stationContractId = stationContract.save();
      System.out.println("\nId retrieved from saving: " + stationContractId + "\n");
      System.out.println("\nprocessing and saving home pickup contract\n");
      pickupContract.processQuote();
      int pickupContractId = pickupContract.save();
      System.out.println("\nId retrieved from saving: " + pickupContractId + "\n");

      System.out.println("\ntesting station dropoff updates\n");
      Location newStationDestination = new Location("1645 Rue de Beaurivage", "Terrebonne", "QC", "J6X 2B9");
      Contract retrievedStationContract = contractDAO.fetchById(stationContractId);
      retrievedStationContract.setDestination(newStationDestination);
      System.out.println("\nRetrieved contract id: " + retrievedStationContract.getId() + "\n");
      System.out.println("\nretrieved contract: " + retrievedStationContract);
      retrievedStationContract.processQuote();
      retrievedStationContract.save();

      // testing home pickup update
      System.out.println("\ntesting home pickup updates\n");
      Location newPickupDestination = new Location("36 Rue de Vaudreuil", "Blainville", "QC", "J7C 4A2");
      Contract retrievedPickupContract = contractDAO.fetchById(pickupContractId);
      retrievedPickupContract.setDestination(newPickupDestination);
      System.out.println("\nRetrieved contract id: " + retrievedPickupContract.getId() + "\n");
      System.out.println("\nretrieved contract: " + retrievedPickupContract);
      retrievedPickupContract.processQuote();
      retrievedPickupContract.save();

      // testing delivery DAO
      // testing creating new Delivery
      // creating objects
      Delivery newStationDelivery = new Delivery(retrievedStationContract);
      Delivery newPickupDelivery = new Delivery(retrievedPickupContract);
      System.out.println("\nPrinting new delivery objects:\n");
      System.out.println("new station delivery: \n\n" + newStationDelivery);
      System.out.println("new pickup delivery: \n\n" + newPickupDelivery);

      // inserting objects into DB
      newStationDelivery.save();
      newPickupDelivery.save();

      // updating delivery pickup time and location
      // creating a flexible delivery
      HomePickup flexContract = new HomePickup(clientObj.getId(), parcel, newPickupDestination, true, true, 100.00,
          origin, LocalDateTime.now(), true);
      flexContract.processQuote();
      int flexContractId = flexContract.save();
      Contract fetchedFlexContract = contractDAO.fetchById(flexContractId);
      Delivery flexDeliv = new Delivery(fetchedFlexContract);
      int flexDelivId = flexDeliv.save();
      Delivery fetchedFlexDeliv = null; // < implement fetching delivery by id here

      ClientService clientService = new ClientService();
      ArrayList<Delivery> deliveries = clientService.viewAllActiveDeliveries(clientObj.getId());
      System.out.println("printing deliveries: \n" + deliveries);
      int randomTracking = deliveries.get(0).getTrackingId();

      System.out.println("printing random trackingid: " + randomTracking);

      System.out.println("\nprinting tracked order: \n");
      System.out.println(clientService.trackOrder(123502));

      System.out.println("done ---------------------");

      for (int i = 0; i < deliveries.size(); i++) {
        System.out.println("\n" + deliveries.get(i));
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void clearTable(String tableName) {
    Mysqlcon con = Mysqlcon.getInstance();
    try {
      System.out.println("Clearing table " + tableName);
      con.connect();
      String queryString = String.format("DELETE FROM %s;", tableName);
      con.formerExecuteUpdate(queryString);
      con.close();
      System.out.println("table " + tableName + " has been cleared");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
