package cps.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.ShippingStatus;
import cps.utils.Mysqlcon;

public class ShippingStatusDAO {
  public ShippingStatus trackOrder(int trackingId) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();
      String qs1 = "SELECT FROM shipping_status WHERE tracking_id = ?";

      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      pst1.setInt(1, trackingId);

      ResultSet rs = pst1.executeQuery();
      ShippingStatus shippingStatus = null;
      while (rs.next()) {
        String status = rs.getString("status");
        Timestamp etaTimestamp = rs.getTimestamp("eta");
        LocalDate etaDate = (etaTimestamp != null) ? etaTimestamp.toLocalDateTime().toLocalDate() : null;
        shippingStatus = new ShippingStatus(trackingId, status, etaDate);
      }
      return shippingStatus;
    } catch (Exception e) {
      System.out.println("Error fetching all shipping statuses: " + e.getMessage());
    }
    return null;
  }

  public Delivery trackOrder2(int trackingId) {
    Delivery delivery = null;
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
          "WHERE d.tracking_id = ?;";
      PreparedStatement pst = sqlcon.prepareStatement(qs);
      pst.setInt(1, trackingId);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        int clientId = rs.getInt("client_id");
        int deliveryId = rs.getInt("id"); // Clarified variable name
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

        if (pickupStreetAddress != null) {
          Location pickup = new Location(pickupLocationId, pickupStreetAddress, pickupPostalCode, pickupCity,
              pickupCountry);
          delivery = new Delivery(deliveryId, clientId, parcel, destination, pickupSignatureRequired, hasPriority,
              isFlexible, pickupTime, pickup, shippingStatus);
        } else {
          delivery = new Delivery(deliveryId, clientId, parcel, destination, signatureRequired, hasPriority,
              shippingStatus);
        }
      }
    } catch (Exception e) {
      System.out.println("Error fetching all deliveries: " + e.getMessage());
    }

    return delivery;
  }

  public ArrayList<ShippingStatus> fretchAll() {
    ArrayList<ShippingStatus> statuses = new ArrayList<ShippingStatus>();
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      String qs1 = "SELECT * FROM shipping_status";
      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      ResultSet rs = pst1.executeQuery();

      while (rs.next()) {
        String status = rs.getString("status");
        int trackingId = rs.getInt("tracking_id");
        Timestamp etaTimestamp = rs.getTimestamp("eta");
        LocalDate etaDate = (etaTimestamp != null) ? etaTimestamp.toLocalDateTime().toLocalDate() : null;
        ShippingStatus shippingStat = new ShippingStatus(trackingId, status, etaDate);
        statuses.add(shippingStat);
      }
    } catch (Exception e) {
      System.out.println("Error fetching all " + e.getMessage());
    }
    return statuses;
  }

  public void setStatus(int trackingId, String newStatus) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      String qs1 = "UPDATE shipping_status SET status = ? WHERE tracking_id = ?";
      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      pst1.setString(1, newStatus);
      pst1.setInt(2, trackingId);
      pst1.executeUpdate();
      System.out.println("Status has been updated");
    } catch (Exception e) {
      System.out.println("Error updating status: " + e.getMessage());
    }
  }
}
