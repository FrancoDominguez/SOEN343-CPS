package cps.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.ShippingStatus;
import cps.DomainLayer.models.Station;
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

  public ArrayList<Delivery> fetchAllByClientId() {
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
          "    s.*" +
          "FROM " +
          "    deliveries d" +
          "LEFT JOIN locations l1 ON d.destination_id = l1.location_id" +
          "LEFT JOIN locations l2 ON d.pickup_location_id = l2.location_id" +
          "LEFT JOIN parcels p ON d.parcel_id = p.parcel_id" +
          "LEFT JOIN shipping_status s ON d.tracking_id = s.tracking_id;";
      PreparedStatement pst = sqlcon.prepareStatement(qs);
      ResultSet rs = pst.executeQuery();
      while (rs.next()) {

      }

    } catch (Exception e) {
      System.out.println("Error fetching all deliveries: " + e.getMessage());
    }

    return deliveries;
  }

  public Delivery findById(int deliveryId) {
    return null;
  }
}