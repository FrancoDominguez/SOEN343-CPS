package cps.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.ShippingStatus;
import cps.utils.Mysqlcon;

public class DeliveryDAO {

  public int insert(int contractId) {
    return 1;
  }

  public int insert(Delivery delivery) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      ShippingStatus status = delivery.getStatus();
      String qs1 = "INSERT INTO shipping_status (status, eta) VALUES (?, ?);";
      PreparedStatement pst1 = sqlcon.prepareStatement(qs1, Statement.RETURN_GENERATED_KEYS);
      pst1.setString(1, status.getStatus());
      pst1.setObject(2, java.sql.Date.valueOf(status.getEta()));

      pst1.executeUpdate();

      ResultSet rs1 = pst1.getGeneratedKeys();
      int trackingId = -1;
      if (rs1.next()) {
        trackingId = rs1.getInt(1);
      }

      System.out.println("\n\n TRACKING ID: " + trackingId + "\n\n");

      String qs3 = "INSERT INTO deliveries (client_id, tracking_id, parcel_id, destination_id," +
          " signature_required, has_priority, is_flexible, pickup_time, pickup_location_id) VALUES " +
          "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pst2 = sqlcon.prepareStatement(qs3, Statement.RETURN_GENERATED_KEYS);
      pst2.setInt(1, delivery.getClientId());
      pst2.setInt(2, trackingId);
      pst2.setInt(3, delivery.getParcel().getId());
      pst2.setInt(4, delivery.getDestination().getId());
      pst2.setBoolean(5, delivery.isSignatureRequired());
      pst2.setBoolean(6, delivery.hasPriority());
      pst2.setBoolean(7, delivery.isFlexible());
      if (delivery.getPickupTime() != null && delivery.getPickupLocation() != null) {
        pst2.setTimestamp(8, Timestamp.valueOf(delivery.getPickupTime()));
        pst2.setInt(9, delivery.getPickupLocation().getId());
      } else {
        pst2.setNull(8, java.sql.Types.NULL);
        pst2.setNull(9, java.sql.Types.NULL);
      }

      pst2.executeUpdate();

      System.out.println("\nupdate executed\n");
      int deliveryId = -1;
      ResultSet rs2 = pst2.getGeneratedKeys();
      if (rs2.next()) {
        deliveryId = rs2.getInt(1);
      }

      con.close();
      System.out.println("new delivery has been created");
      return deliveryId;
    } catch (Exception e) {
      System.out.println("Error inserting Delivery into database" + e.getMessage());
    }
    return -1;
  }

  public void update(Delivery del) {
    return;
  }

  public ArrayList<Delivery> fetchAllByClientId(int clientId) {
    ArrayList<Delivery> deliveries = new ArrayList<>();
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      String qs = "SELECT " +
          "    d.*," +
          "    l1.location_id AS destination_location_id," +
          "    l1.street_address AS destination_street_address," +
          "    l1.postal_code AS destination_postal_code," +
          "    l1.city AS destination_city," +
          "    l1.country AS destination_country," +
          "    l2.location_id AS pickup_location_id," +
          "    l2.street_address AS pickup_street_address," +
          "    l2.postal_code AS pickup_postal_code," +
          "    l2.city AS pickup_city," +
          "    l2.country AS pickup_country," +
          "    p.*," +
          "    s.* " +
          "FROM " +
          "    deliveries d " +
          "LEFT JOIN locations l1 ON d.destination_id = l1.location_id " +
          "LEFT JOIN locations l2 ON d.pickup_location_id = l2.location_id " +
          "LEFT JOIN parcels p ON d.parcel_id = p.parcel_id " +
          "LEFT JOIN shipping_status s ON d.tracking_id = s.tracking_id " +
          "WHERE d.client_id = ?;";
      PreparedStatement pst = sqlcon.prepareStatement(qs);
      pst.setInt(1, clientId);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        int deliveryId = rs.getInt("id"); // Clarified variable name
        int trackingId = rs.getInt("tracking_id");
        String status = rs.getString("status");
        Timestamp etaTimestamp = rs.getTimestamp("eta");
        LocalDate etaDate = (etaTimestamp != null) ? etaTimestamp.toLocalDateTime().toLocalDate() : null;
        int parcelId = rs.getInt("parcel_id");
        double parcelHeight = rs.getDouble("height");
        double parcelLength = rs.getDouble("length");
        double parcelWidth = rs.getDouble("width");
        double parcelWeight = rs.getDouble("weight");
        Boolean isFragile = rs.getBoolean("is_fragile");
        int destinationId = rs.getInt("destination_location_id"); // Fixed column name
        String destinationStreetAddress = rs.getString("destination_street_address"); // Renamed
        String destinationPostalCode = rs.getString("destination_postal_code");
        String destinationCity = rs.getString("destination_city");
        String destinationCountry = rs.getString("destination_country");
        Boolean signatureRequired = rs.getBoolean("signature_required"); // Correct column
        Boolean hasPriority = rs.getBoolean("has_priority"); // Correct column
        Boolean isFlexible = rs.getBoolean("is_flexible"); // Correct column
        LocalDateTime pickupTime;
        Timestamp pickupTimestamp = rs.getTimestamp("pickup_time");
        pickupTime = (pickupTimestamp != null) ? pickupTimestamp.toLocalDateTime() : null;
        Boolean pickupSignatureRequired = rs.getBoolean("signature_required"); // Renamed
        int pickupLocationId = rs.getInt("pickup_location_id"); // Renamed
        String pickupStreetAddress = rs.getString("pickup_street_address"); // Renamed
        String pickupPostalCode = rs.getString("pickup_postal_code");
        String pickupCity = rs.getString("pickup_city");
        String pickupCountry = rs.getString("pickup_country");

        Location destination = new Location(destinationId, destinationStreetAddress, destinationPostalCode,
            destinationCity,
            destinationCountry);
        ShippingStatus shippingStatus = new ShippingStatus(trackingId, status, etaDate);
        Parcel parcel = new Parcel(parcelId, parcelHeight, parcelLength, parcelWidth, parcelWeight, isFragile);
        System.out.println("extracted eta date: " + etaDate);

        Delivery delivery = null;
        if (pickupStreetAddress != null) {
          Location pickup = new Location(pickupLocationId, pickupStreetAddress, pickupPostalCode, pickupCity,
              pickupCountry);
          delivery = new Delivery(deliveryId, clientId, parcel, destination, pickupSignatureRequired, hasPriority,
              isFlexible, pickupTime, pickup, shippingStatus);
        } else {
          delivery = new Delivery(deliveryId, clientId, parcel, destination, signatureRequired, hasPriority,
              shippingStatus);
        }
        deliveries.add(delivery);
      }
    } catch (Exception e) {
      System.out.println("Error fetching all deliveries: " + e.getMessage());
    }

    return deliveries;
  }

  public Delivery findById(int deliveryId) {
    return null;
  }

  public void updatePickupTime(int deliveryId, String newTime){
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      System.out.println("Changing pick up time to " + newTime);
      Timestamp newTimestamp = Timestamp.valueOf(newTime);

      PreparedStatement pst1 = sqlcon.prepareStatement("UPDATE deliveries SET pickup_time = ? WHERE id = ?");
      pst1.setTimestamp(1, newTimestamp);
      pst1.setInt(2, deliveryId);

      pst1.executeUpdate();

      System.out.println("Successfully changed pickup time");

    }catch(Exception e){
      System.err.println(e);
    }
  }
}