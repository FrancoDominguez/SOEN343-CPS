package cps;

import java.time.LocalDateTime;

import cps.DAO.ClientDAO;
import cps.DAO.ContractDAO;
import cps.DAO.StationDAO;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.ClientModel;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.HomePickup;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.Station;
import cps.DomainLayer.models.StationDropoff;
import cps.utils.Mysqlcon;

public class antoineTest {

  public static void testDAOs() {
    try {
      System.out.println("Clearing all tables\n");
      clearTable("contracts");
      clearTable("locations");
      clearTable("parcels");
      System.out.println("All tables are now clear\n");
      ContractDAO contractDAO = new ContractDAO();

      // fetching client
      ClientDAO clientDAO = new ClientDAO();
      ClientModel clientObj = clientDAO.fetchByEmail("hoboslime@gmail.com");

      // delivery info
      StationDAO stationDAO = new StationDAO();
      Station station = stationDAO.fetchLocation(1);
      Location origin = new Location("1081 Caledonia Rd, Mount Royal, QC H3R 2V6", "", "", "t");
      Location destination = new Location("484 Rue des Alismas, Laval, QC H7X 4G9", "", "", "");
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
      Location newStationDestination = new Location("1645 Rue de Beaurivage, Terrebonne, QC J6X 2B9", "", "", "");
      Contract retrievedStationContract = contractDAO.fetchById(stationContractId);
      retrievedStationContract.setDestination(newStationDestination);
      System.out.println("\nRetrieved contract id: " + retrievedStationContract.getId() + "\n");
      System.out.println("\nretrieved contract: " + retrievedStationContract);
      retrievedStationContract.processQuote();
      retrievedStationContract.save();

      // testing home pickup update
      System.out.println("\ntesting home pickup updates\n");
      Location newPickupDestination = new Location("36 Rue de Vaudreuil, Blainville, QC J7C 4A2", "", "", "");
      Contract retrievedPickupContract = contractDAO.fetchById(pickupContractId);
      retrievedPickupContract.setDestination(newPickupDestination);
      System.out.println("\nRetrieved contract id: " + retrievedPickupContract.getId() + "\n");
      System.out.println("\nretrieved contract: " + retrievedPickupContract);
      retrievedPickupContract.processQuote();
      retrievedPickupContract.save();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // Contract newContract = new Contract();
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
