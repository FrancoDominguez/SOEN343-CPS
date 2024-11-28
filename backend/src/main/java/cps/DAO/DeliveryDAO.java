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

      String qs3 = "INSERT INTO deliveries (id, client_id, tracking_id, parcel_id, destination_id," +
          " signature_required, has_priority, is_flexible, pickup_time, pickup_location_id) VALUES " +
          "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement pst2 = sqlcon.prepareStatement(qs3, Statement.RETURN_GENERATED_KEYS);
      pst2.setInt(1, delivery.getId());
      pst2.setInt(2, delivery.getClientId());
      pst2.setInt(3, trackingId);
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

      int deliveryId = -1;
      ResultSet rs2 = pst2.getGeneratedKeys();
      if (rs2.next()) {
        deliveryId = rs2.getInt("1");
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

  public ArrayList<Delivery> fetchAll() {
    ArrayList<Delivery> deliveries = new ArrayList<>();
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      String qs = "SELECT * FROM deliver";
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

/*
 * SELECT
 * parcels.*,
 * contracts.*,
 * origin.location_id AS origin_location_id,
 * origin.street_address AS origin_street_address,
 * origin.postal_code AS origin_postal_code,
 * origin.city AS origin_city,
 * origin.country AS origin_country,
 * destination.location_id AS destination_location_id,
 * destination.street_address AS destination_street_address,
 * destination.postal_code AS destination_postal_code,
 * destination.city AS destination_city,
 * destination.country AS destination_country,
 * stations.station_id,
 * stations.name
 * FROM parcels
 * JOIN contracts ON parcels.parcel_id = contracts.parcel_id
 * JOIN locations AS origin ON contracts.origin_id = origin.location_id
 * JOIN locations AS destination ON contracts.destination_id =
 * destination.location_id
 * JOIN stations ON contracts.station_id = stations.station_id;
 */