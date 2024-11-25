package cps;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;

import cps.DAO.ClientDAO;
import cps.DAO.ContractDAO;
import cps.DAO.StationDAO;
import cps.models.ClientModel;
import cps.models.Contract;
import cps.models.HomePickup;
import cps.models.Location;
import cps.models.Parcel;
import cps.models.Station;
import cps.models.StationDropoff;
import cps.utils.Mysqlcon;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    testDAOs();

    // SpringApplication.run(Driver.class, args);
  }

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
      Location origin = new Location("origin", "origin", "origin", "origin");
      Location destination = new Location("destination", "destination", "destination", "destination");
      Parcel parcel = new Parcel(10.0, 10.0, 10.0, 10.0, true);

      // creating contract object
      HomePickup pickupContract = new HomePickup(clientObj.getId(), parcel, destination, true, true, 100.00, origin,
          LocalDateTime.now(), false);
      StationDropoff stationContract = new StationDropoff(clientObj.getId(), parcel, destination, true, true, 100.00,
          station);

      System.out.println("\nprocessing and saving station dropoff contract\n");
      stationContract.processQuote();
      stationContract.save();
      System.out.println("\nprocessing and saving home pickup contract\n");
      pickupContract.processQuote();
      int savedPickupId = pickupContract.save();
      System.out.println("\nId retrieved from saving: " + savedPickupId + "\n");

      // testing home pickup update
      System.out.println("\ntesting home pickup updates\n");
      Location newDestination = new Location("new value", "new value", "new value", "new value");
      Contract savedPickupContract = contractDAO.fetchById(savedPickupId);
      savedPickupContract.setDestination(newDestination);
      System.out.println("\nRetrieved contract id: " + savedPickupContract.getId() + "\n");
      System.out.println("\nretrieved contract: " + savedPickupContract);
      savedPickupContract.processQuote();
      savedPickupContract.save();

      // testing station dropoff updates
      // System.out.println("\ntesting station dropoff updates\n");
      // stationContract.setHasPriority(false);
      // Parcel changedParcel = new Parcel(7.0, 7.0, 7.0, 7.0, true);
      // stationContract.setParcel(changedParcel);
      // stationContract.processQuote();
      // stationContract.save();

      // ArrayList<Contract> contracts =
      // contractDAO.fetchAllByClientId(clientObj.getId());
      // System.out.println("\nprinting contracts:\n");
      // System.out.println(contracts);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // Contract newContract = new Contract();
  }

  private static void clearTable(String tableName) {
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
