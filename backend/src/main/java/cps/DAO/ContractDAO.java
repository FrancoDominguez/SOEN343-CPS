package cps.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.HomePickup;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.Station;
import cps.DomainLayer.models.StationDropoff;
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
              "s.city AS origin_station_city, s.postal_code AS origin_station_postal_code, s.province AS origin_station_country, "
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
          String originStationCountry = "Canada";
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
      System.out.println("Error fetching single by id: " + e.getMessage());
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
              "s.city AS origin_station_city, s.postal_code AS origin_station_postal_code, s.province AS origin_station_province, "
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
          String originStationCountry = rs.getString("origin_station_province");
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
      System.out.println("Error fetching all by client Id: " + e.getMessage());
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
      System.out.println("Successfully saved station dropoff contract");
      con.close();
    } catch (Exception e) {
      System.out.println("Error inserting station dropoff contract: " + e.getMessage());
    }
    return -1;
  }

  public void update(StationDropoff contract) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();
      Location destination = contract.getDestination();
      Station station = contract.getStation();
      Parcel parcel = contract.getParcel();

      String qs1 = "UPDATE locations SET street_address = ?, postal_code = ?, city = ?, country = ? WHERE location_id = ?;";
      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      pst1.setString(1, destination.getStreetAddress());
      pst1.setString(2, destination.getPostalCode());
      pst1.setString(3, destination.getCity());
      pst1.setString(4, destination.getCountry());
      pst1.setInt(5, destination.getId());

      pst1.executeUpdate();

      String qs3 = "UPDATE parcels SET height = ?, width = ?, length = ?, weight = ?, is_fragile = ? WHERE parcel_id = ?;";
      PreparedStatement pst3 = sqlcon.prepareStatement(qs3);
      pst3.setDouble(1, parcel.getHeight());
      pst3.setDouble(2, parcel.getWidth());
      pst3.setDouble(3, parcel.getLength());
      pst3.setDouble(4, parcel.getWeight());
      pst3.setBoolean(5, parcel.isFragile());
      pst3.setInt(6, parcel.getId());

      pst3.executeUpdate();

      String qs2 = "UPDATE stations SET name = ?, street_address = ?, postal_code = ?, city = ?, country = ? WHERE station_id = ?;";
      PreparedStatement pst2 = sqlcon.prepareStatement(qs2);
      pst2.setString(1, station.getName());
      pst2.setString(2, station.getStreetAddress());
      pst2.setString(3, station.getPostalCode());
      pst2.setString(4, station.getCity());
      pst2.setString(5, station.getCountry());
      pst2.setInt(6, station.getId());

      pst2.executeUpdate();

      String qs4 = "UPDATE contracts SET client_id = ?, price = ?, eta = ?, signature_required = ?, priority_shipping = ?, "
          +
          "warranted_amount = ? "
          +
          "WHERE contract_id = ?;";
      PreparedStatement pst4 = sqlcon.prepareStatement(qs4);
      pst4.setInt(1, contract.getClientId());
      pst4.setDouble(2, contract.getPrice());
      pst4.setObject(3, contract.getEta());
      pst4.setBoolean(4, contract.signatureRequired());
      pst4.setBoolean(5, contract.hasPriority());
      pst4.setDouble(6, contract.getWarrantedAmount());
      pst4.setInt(7, contract.getId());

      pst4.executeUpdate();

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
      System.out.println("Successfully saved home pickup contract");
      con.close();
    } catch (Exception e) {
      System.out.println("Error inserting home pickup contract: " + e.getMessage());
    }
    return -1;
  }

  public void update(HomePickup contract) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();
      Location destination = contract.getDestination();
      Location origin = contract.getOrigin();
      Parcel parcel = contract.getParcel();

      String qs1 = "UPDATE locations SET street_address = ?, postal_code = ?, city = ?, country = ? WHERE location_id = ?;";
      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      pst1.setString(1, destination.getStreetAddress());
      pst1.setString(2, destination.getPostalCode());
      pst1.setString(3, destination.getCity());
      pst1.setString(4, destination.getCountry());
      pst1.setInt(5, destination.getId());

      pst1.executeUpdate();

      String qs2 = "UPDATE locations SET street_address = ?, postal_code = ?, city = ?, country = ? WHERE location_id = ?;";
      PreparedStatement pst2 = sqlcon.prepareStatement(qs2);
      pst2.setString(1, origin.getStreetAddress());
      pst2.setString(2, origin.getPostalCode());
      pst2.setString(3, origin.getCity());
      pst2.setString(4, origin.getCountry());
      pst2.setInt(5, origin.getId());

      pst2.executeUpdate();

      String qs3 = "UPDATE parcels SET height = ?, width = ?, length = ?, weight = ?, is_fragile = ? WHERE parcel_id = ?;";
      PreparedStatement pst3 = sqlcon.prepareStatement(qs3);
      pst3.setDouble(1, parcel.getHeight());
      pst3.setDouble(2, parcel.getWidth());
      pst3.setDouble(3, parcel.getLength());
      pst3.setDouble(4, parcel.getWeight());
      pst3.setBoolean(5, parcel.isFragile());
      pst3.setInt(6, parcel.getId());

      pst3.executeUpdate();

      String qs4 = "UPDATE contracts SET client_id = ?, price = ?, eta = ?, signature_required = ?, priority_shipping = ?, "
          +
          "warranted_amount = ?, pickup_time = ?, is_flexible = ? "
          +
          "WHERE contract_id = ?;";
      PreparedStatement pst4 = sqlcon.prepareStatement(qs4);
      pst4.setInt(1, contract.getClientId());
      pst4.setDouble(2, contract.getPrice());
      pst4.setObject(3, contract.getEta());
      pst4.setBoolean(4, contract.signatureRequired());
      pst4.setBoolean(5, contract.hasPriority());
      pst4.setDouble(6, contract.getWarrantedAmount());
      pst4.setTimestamp(7, Timestamp.valueOf(contract.getPickupTime()));
      pst4.setBoolean(8, contract.isFlexible());
      pst4.setInt(9, contract.getId());

      pst4.executeUpdate();
    } catch (Exception e) {
      System.out.println("Error updating home pickup contract: " + e.getMessage());
    }
  }
}
