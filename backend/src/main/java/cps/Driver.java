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

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    testDAOs();

    // SpringApplication.run(Driver.class, args);
  }

  public static void testDAOs() {
    try {
      // fetching client
      ClientDAO clientDAO = new ClientDAO();
      ClientModel clientObj = clientDAO.fetchByEmail("hoboslime@gmail.com");

      // delivery info
      StationDAO stationDAO = new StationDAO();
      Station station = stationDAO.fetchLocation(1);
      Location origin = new Location("13069 Rue Ramsay", "h9b2s3", "Pierrefonds", "Canada");
      System.out.println(origin.toString());
      Location destination = new Location("1524 Notre-Dame", "h3c1l1", "Montreal", "Canada");
      System.out.println(destination.toString());
      Parcel parcel = new Parcel(10.0, 10.0, 10.0, 10.0, true);

      // creating contract object
      HomePickup pickupContract = new HomePickup(clientObj.getId(), parcel, destination, true, true, 100.00, origin,
          LocalDateTime.now(), false);
      StationDropoff stationContract = new StationDropoff(clientObj.getId(), parcel, destination, true, true, 100.00,
          station);

      stationContract.processQuote();
      stationContract.save();
      pickupContract.processQuote();
      pickupContract.save();

      ContractDAO contractDAO = new ContractDAO();
      ArrayList<Contract> contracts = contractDAO.fetchAllByClientId(clientObj.getId());
      System.out.println(contracts);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // Contract newContract = new Contract();
  }
}
