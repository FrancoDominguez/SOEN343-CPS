package cps.DAO;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import cps.models.Contract;
import cps.models.HomePickup;
import cps.models.Location;
import cps.models.Parcel;
import cps.models.Station;
import cps.models.StationDropoff;
import cps.utils.Mysqlcon;

public class ContractDAO {

  public Contract fetchById(int contractId) {

    Contract contract = null;
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();

      String queryString = String.format(
          "SELECT " +
              "c.contract_id, c.price, c.eta, c.signature_required, c.priority_shipping, " +
              "c.warranted_amount, c.pickup_time, c.is_flexible, " +
              "c.origin_station_id, c.origin_location_id, c.parcel_id, c.client_id, c.destination_id, " +
              "cl.firstname AS client_firstname, cl.lastname AS client_lastname, cl.email AS client_email, " +
              "p.height AS parcel_height, p.width AS parcel_width, p.length AS parcel_length, p.weight AS parcel_weight, "
              +
              "p.is_fragile AS parcel_is_fragile, " +
              "dl.street_address AS destination_street_address, " +
              "dl.city AS destination_city, dl.postal_code AS destination_postal_code, dl.country AS destination_country, "
              +
              "s.name AS origin_station_name, s.street_address AS origin_station_street_address, " +
              "s.city AS origin_station_city, s.postal_code AS origin_station_postal_code, s.country AS origin_station_country, "
              +
              "l.street_address AS origin_location_street_address, " +
              "l.city AS origin_location_city, l.postal_code AS origin_location_postal_code, l.country AS origin_location_country "
              +
              "FROM contracts c " +
              "LEFT JOIN clients cl ON c.client_id = cl.client_id " +
              "LEFT JOIN parcels p ON c.parcel_id = p.parcel_id " +
              "LEFT JOIN locations dl ON c.destination_id = dl.location_id " +
              "LEFT JOIN stations s ON c.origin_station_id = s.station_id " +
              "LEFT JOIN locations l ON c.origin_location_id = l.location_id " +
              "WHERE c.contract_id = %d;",
          contractId);
      con.formerExecuteQuery(queryString);
      ResultSet rs = con.getResultSet();

      if (rs.next()) {
        // Contract fields
        int clientId = rs.getInt("client_id");
        boolean signatureRequired = rs.getBoolean("signature_required");
        boolean hasPriority = rs.getBoolean("priority_shipping");
        double warrantedAmount = rs.getDouble("warranted_amount");
        double price = rs.getDouble("price");
        String etaString = rs.getString("eta");
        Duration eta = Duration.parse(etaString);

        // Destination fields
        int destinationId = rs.getInt("destination_id");
        String destinationStreetAddress = rs.getString("destination_street_address");
        String destinationCity = rs.getString("destination_city");
        String destinationPostalCode = rs.getString("destination_postal_code");
        String destinationCountry = rs.getString("destination_country");
        Location destination = new Location(destinationId, destinationStreetAddress, destinationPostalCode,
            destinationCity, destinationCountry);

        Station originStation = null;
        Location originLocation = null;
        LocalDateTime pickupTime = null;
        boolean isFlexible = false;

        // Origin Station
        int originStationId = rs.getInt("origin_station_id");
        if (!rs.wasNull()) {
          String originStationName = rs.getString("origin_station_name");
          String originStationStreetAddress = rs.getString("origin_station_street_address");
          String originStationCity = rs.getString("origin_station_city");
          String originStationPostalCode = rs.getString("origin_station_postal_code");
          String originStationCountry = rs.getString("origin_station_country");
          originStation = new Station(originStationId, originStationName, originStationStreetAddress,
              originStationPostalCode, originStationCity, originStationCountry);
        } else {
          // Origin Location
          int originLocationId = rs.getInt("origin_location_id");
          String originLocationStreetAddress = rs.getString("origin_location_street_address");
          String originLocationCity = rs.getString("origin_location_city");
          String originLocationPostalCode = rs.getString("origin_location_postal_code");
          String originLocationCountry = rs.getString("origin_location_country");
          originLocation = new Location(originLocationId, originLocationStreetAddress, originLocationPostalCode,
              originLocationCity, originLocationCountry);

          // Pickup time and flexibility
          Timestamp pickupTimestamp = rs.getTimestamp("pickup_time");
          pickupTime = (pickupTimestamp != null) ? pickupTimestamp.toLocalDateTime() : null;
          isFlexible = rs.getBoolean("is_flexible");
        }

        // Parcel fields
        int parcelId = rs.getInt("parcel_id");
        double length = rs.getDouble("parcel_length");
        double height = rs.getDouble("parcel_height");
        double width = rs.getDouble("parcel_width");
        double weight = rs.getDouble("parcel_weight");
        boolean isFragile = rs.getBoolean("parcel_is_fragile");
        Parcel parcel = new Parcel(parcelId, length, width, height, weight, isFragile);

        // Construct Contract
        if (originStation == null) {
          contract = new HomePickup(contractId, clientId, parcel, destination, signatureRequired, hasPriority,
              warrantedAmount, price, eta, originLocation, pickupTime, isFlexible);
        } else {
          contract = new StationDropoff(contractId, clientId, parcel, destination, signatureRequired, hasPriority,
              warrantedAmount, price, eta, originStation);
        }
      } else {
        throw new Exception(String.format("No contract found with ID %d", contractId));
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return contract;
  }

  public ArrayList<Contract> fetchAllByClientId(int clientId) {
    ArrayList<Contract> contracts = new ArrayList<Contract>();
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();

      String queryString = String.format(
          "SELECT " +
              "c.contract_id, c.price, c.eta, c.signature_required, c.priority_shipping, " +
              "c.warranted_amount, c.pickup_time, c.is_flexible, " +
              "c.origin_station_id, c.origin_location_id, c.parcel_id, c.client_id, c.destination_id, " +
              "cl.firstname AS client_firstname, cl.lastname AS client_lastname, cl.email AS client_email, " +
              "p.height AS parcel_height, p.width AS parcel_width, p.length AS parcel_length, p.weight AS parcel_weight, "
              +
              "p.is_fragile AS parcel_is_fragile, " +
              "dl.street_address AS destination_street_address, " +
              "dl.city AS destination_city, dl.postal_code AS destination_postal_code, dl.country AS destination_country, "
              +
              "s.name AS origin_station_name, s.street_address AS origin_station_street_address, " +
              "s.city AS origin_station_city, s.postal_code AS origin_station_postal_code, s.country AS origin_station_country, "
              +
              "l.street_address AS origin_location_street_address, " +
              "l.city AS origin_location_city, l.postal_code AS origin_location_postal_code, l.country AS origin_location_country "
              +
              "FROM contracts c " +
              "LEFT JOIN clients cl ON c.client_id = cl.client_id " +
              "LEFT JOIN parcels p ON c.parcel_id = p.parcel_id " +
              "LEFT JOIN locations dl ON c.destination_id = dl.location_id " +
              "LEFT JOIN stations s ON c.origin_station_id = s.station_id " +
              "LEFT JOIN locations l ON c.origin_location_id = l.location_id " +
              "WHERE c.client_id = %d;",
          clientId);
      con.formerExecuteQuery(queryString);
      ResultSet rs = con.getResultSet();

      while (rs.next()) {
        // Contract fields
        int contractId = rs.getInt("contract_id");
        boolean signatureRequired = rs.getBoolean("signature_required");
        boolean hasPriority = rs.getBoolean("priority_shipping");
        double warrantedAmount = rs.getDouble("warranted_amount");
        double price = rs.getDouble("price");
        String etaString = rs.getString("eta");
        Duration eta = Duration.parse(etaString);

        // Destination fields
        int destinationId = rs.getInt("destination_id");
        String destinationStreetAddress = rs.getString("destination_street_address");
        String destinationCity = rs.getString("destination_city");
        String destinationPostalCode = rs.getString("destination_postal_code");
        String destinationCountry = rs.getString("destination_country");
        Location destination = new Location(destinationId, destinationStreetAddress, destinationPostalCode,
            destinationCity, destinationCountry);

        Station originStation = null;
        Location originLocation = null;
        LocalDateTime pickupTime = null;
        boolean isFlexible = false;

        // Origin Station
        int originStationId = rs.getInt("origin_station_id");
        if (!rs.wasNull()) {
          String originStationName = rs.getString("origin_station_name");
          String originStationStreetAddress = rs.getString("origin_station_street_address");
          String originStationCity = rs.getString("origin_station_city");
          String originStationPostalCode = rs.getString("origin_station_postal_code");
          String originStationCountry = rs.getString("origin_station_country");
          originStation = new Station(originStationId, originStationName, originStationStreetAddress,
              originStationPostalCode, originStationCity, originStationCountry);
        } else {
          // Origin Location
          int originLocationId = rs.getInt("origin_location_id");
          String originLocationStreetAddress = rs.getString("origin_location_street_address");
          String originLocationCity = rs.getString("origin_location_city");
          String originLocationPostalCode = rs.getString("origin_location_postal_code");
          String originLocationCountry = rs.getString("origin_location_country");
          originLocation = new Location(originLocationId, originLocationStreetAddress, originLocationPostalCode,
              originLocationCity, originLocationCountry);

          // Pickup time and flexibility
          Timestamp pickupTimestamp = rs.getTimestamp("pickup_time");
          pickupTime = (pickupTimestamp != null) ? pickupTimestamp.toLocalDateTime() : null;
          isFlexible = rs.getBoolean("is_flexible");
        }

        // Parcel fields
        int parcelId = rs.getInt("parcel_id");
        double length = rs.getDouble("parcel_length");
        double height = rs.getDouble("parcel_height");
        double width = rs.getDouble("parcel_width");
        double weight = rs.getDouble("parcel_weight");
        boolean isFragile = rs.getBoolean("parcel_is_fragile");
        Parcel parcel = new Parcel(parcelId, length, width, height, weight, isFragile);

        // Construct Contract
        Contract newContract = null;
        if (originStation == null) {
          newContract = new HomePickup(contractId, clientId, parcel, destination, signatureRequired, hasPriority,
              warrantedAmount, price, eta, originLocation, pickupTime, isFlexible);
          contracts.add(newContract);
        } else {
          newContract = new StationDropoff(contractId, clientId, parcel, destination, signatureRequired, hasPriority,
              warrantedAmount, price, eta, originStation);
        }
        if (newContract != null) {
          contracts.add(newContract);
        }
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return contracts;
  }

  public int insert(StationDropoff contract) {
    Location destination = contract.getDestination();
    Parcel parcel = contract.getParcel();

    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();

      con.formerExecuteUpdate(String.format(
          "INSERT INTO locations (street_address, postal_code, city, country) VALUES ('%s', '%s', '%s', '%s');",
          destination.getStreetAddress(), destination.getPostalCode(),
          destination.getCity(), destination.getCountry()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS id;");
      ResultSet rs = con.getResultSet();
      rs.next();
      int destinationId = rs.getInt("id");

      con.formerExecuteUpdate(String.format(
          "INSERT INTO parcels (height, width, length, weight, is_fragile) VALUES (%f, %f, %f, %f, %b);",
          parcel.getHeight(), parcel.getWidth(),
          parcel.getLength(), parcel.getWeight(), parcel.isFragile()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS id;");
      rs = con.getResultSet();
      rs.next();
      int parcelId = rs.getInt("id");

      con.formerExecuteUpdate(String.format(
          "INSERT INTO contracts (client_id, price, eta, signature_required, priority_shipping, warranted_amount, parcel_id, destination_id, origin_station_id) VALUES "
              + "(%d, %f, '%s', %b, %b, %f, %d, %d, %d);",
          contract.getClientId(), contract.getPrice(), contract.getEta(),
          contract.signatureRequired(), contract.hasPriority(),
          contract.getWarrantedAmount(), parcelId, destinationId, contract.getStation().getId()));

      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS contract_id;");
      rs = con.getResultSet();
      if (rs.next()) {
        int contractId = rs.getInt("contract_id");
        con.close();
        return contractId;
      }
      con.close();
    } catch (Exception e) {
      System.out.println("Error inserting station dropoff contract: " + e.getMessage());
    }
    return -1;
  }

  public void update(StationDropoff contract) {
    try {
      Location destination = contract.getDestination();
      Parcel parcel = contract.getParcel();

      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      StringBuilder queryString = new StringBuilder();

      queryString.append("START TRANSACTION;");

      // Update destinations
      queryString.append("UPDATE locations SET ");
      queryString.append(String.format(
          "street_address = '%s', postal_code = '%s', city = '%s', country = '%s' ",
          escapeSql(destination.getStreetAddress()),
          escapeSql(destination.getPostalCode()),
          escapeSql(destination.getCity()),
          escapeSql(destination.getCountry())));
      queryString.append(String.format("WHERE location_id = %d;", destination.getId()));

      // Update parcels
      queryString.append("UPDATE parcels SET ");
      queryString.append(String.format(
          "height = %f, width = %f, length = %f, weight = %f, is_fragile = %d ",
          parcel.getHeight(), parcel.getWidth(), parcel.getLength(), parcel.getWeight(),
          parcel.isFragile() ? 1 : 0));
      queryString.append(String.format("WHERE parcel_id = %d;", parcel.getId()));

      // Update contracts
      queryString.append("UPDATE contracts SET ");
      queryString.append(String.format(
          "client_id = %d, price = %f, eta = '%s', signature_required = %d, priority_shipping = %d, warranted_amount = %f, origin_station_id = %d ",
          contract.getClientId(), contract.getPrice(), escapeSql(contract.getEta().toString()),
          contract.signatureRequired() ? 1 : 0, contract.hasPriority() ? 1 : 0,
          contract.getWarrantedAmount(), contract.getStation().getId()));
      queryString.append(String.format("WHERE contract_id = %d;", contract.getId()));

      queryString.append("COMMIT;");
      con.formerExecuteUpdate(queryString.toString());
      con.close();
    } catch (Exception e) {
      System.out.println("Error updating station dropoff contract: " + e.getMessage());
    }
  }

  // Utility method to escape single quotes in SQL strings
  public int insert(HomePickup contract) {
    Location destination = contract.getDestination();
    Location origin = contract.getOrigin();
    Parcel parcel = contract.getParcel();

    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();

      // Insert destination
      con.formerExecuteUpdate(String.format(
          "INSERT INTO locations (street_address, postal_code, city, country) VALUES ('%s', '%s', '%s', '%s');",
          destination.getStreetAddress(), destination.getPostalCode(),
          destination.getCity(), destination.getCountry()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS id;");
      ResultSet rs = con.getResultSet();
      rs.next();
      int destinationId = rs.getInt("id");

      // Insert origin
      con.formerExecuteUpdate(String.format(
          "INSERT INTO locations (street_address, postal_code, city, country) VALUES ('%s', '%s', '%s', '%s');",
          origin.getStreetAddress(), origin.getPostalCode(),
          origin.getCity(), origin.getCountry()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS id;");
      rs = con.getResultSet();
      rs.next();
      int originId = rs.getInt("id");

      // Insert parcel
      con.formerExecuteUpdate(String.format(
          "INSERT INTO parcels (height, width, length, weight, is_fragile) VALUES (%f, %f, %f, %f, %b);",
          parcel.getHeight(), parcel.getWidth(),
          parcel.getLength(), parcel.getWeight(), parcel.isFragile()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS id;");
      rs = con.getResultSet();
      rs.next();
      int parcelId = rs.getInt("id");

      // Insert contract
      con.formerExecuteUpdate(String.format(
          "INSERT INTO contracts (client_id, price, eta, signature_required, priority_shipping, warranted_amount, "
              + "parcel_id, destination_id, origin_location_id, pickup_time, is_flexible) VALUES "
              + "(%d, %f, '%s', %b, %b, %f, %d, %d, %d, '%s', %b);",
          contract.getClientId(), contract.getPrice(), contract.getEta(),
          contract.signatureRequired(), contract.hasPriority(),
          contract.getWarrantedAmount(), parcelId, destinationId, originId,
          Timestamp.valueOf(contract.getPickupTime()).toString(), contract.isFlexible()));
      con.formerExecuteQuery("SELECT LAST_INSERT_ID() AS contract_id;");
      rs = con.getResultSet();
      if (rs.next()) {
        int contractId = rs.getInt("contract_id");
        con.close();
        return contractId;
      }
      con.close();
    } catch (Exception e) {
      System.out.println("Error inserting home pickup contract: " + e.getMessage());
    }
    return -1;
  }

  public void update(HomePickup contract) {
    try {
      Location destination = contract.getDestination();
      Location origin = contract.getOrigin();
      Parcel parcel = contract.getParcel();

      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      StringBuilder queryString = new StringBuilder();

      queryString.append("START TRANSACTION;");

      // update destination in origins
      queryString.append("UPDATE origins SET");
      queryString.append(String.format(" street_address = '%s', postal_code = '%s', city = '%s', country = '%s'",
          destination.getStreetAddress(), destination.getPostalCode(), destination.getCity(),
          destination.getCountry()));
      queryString.append(String.format(" WHERE id = %d;", destination.getId()));

      // update origin in locations
      queryString.append("UPDATE locations SET");
      queryString.append(String.format(" street_address = '%s', postal_code = '%s', city = '%s', country = '%s'",
          origin.getStreetAddress(), origin.getPostalCode(), origin.getCity(), origin.getCountry()));
      queryString.append(String.format(" WHERE id = %d;", origin.getId()));

      // update parcel in parcels
      queryString.append("UPDATE parcels SET");
      queryString.append(String.format(
          " height = %f, width = %f, length = %f, weight = %f, is_fragile = %b",
          parcel.getHeight(), parcel.getWidth(), parcel.getLength(), parcel.getWeight(), parcel.isFragile()));
      queryString.append(String.format(" WHERE id = %d;", parcel.getId()));

      // update contract in contracts
      queryString.append("UPDATE contracts SET");
      queryString.append(String.format(
          " client_id = %d, price = %f, eta = '%s', signature_required = %b, priority_shipping = %b, warranted_amount = %f, "
              + "parcel_id = %d, destination_id = %d, origin_location_id = %d, pickup_time = '%s', is_flexible = %b",
          contract.getClientId(), contract.getPrice(), contract.getEta(), contract.signatureRequired(),
          contract.hasPriority(), contract.getWarrantedAmount(), parcel.getId(), destination.getId(), origin.getId(),
          Timestamp.valueOf(contract.getPickupTime()).toString(), contract.isFlexible()));
      queryString.append(String.format(" WHERE id = %d;", contract.getId()));

      queryString.append("COMMIT;");
      con.formerExecuteUpdate(queryString.toString());
      con.close();
    } catch (Exception e) {
      System.out.println("Error updating home pickup contract: " + e.getMessage());
    }
  }

  private String escapeSql(String input) {
    return input.replace("'", "''");
  }
}
