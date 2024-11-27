package cps.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.Location;
import cps.DomainLayer.models.Parcel;
import cps.DomainLayer.models.Station;
import cps.utils.Mysqlcon;

public class DeliveryDAO {

  public int insert(int contractId) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      Connection sqlcon = con.getConnection();

      String qs1 = "SELECT "
          + "parcels.*, "
          + "contracts.*, "
          + "origin.location_id AS origin_location_id, origin.street_address AS origin_street_address, "
          + "origin.postal_code AS origin_postal_code, origin.city AS origin_city, "
          + "origin.country AS origin_country, "

          + "destination.location_id AS destination_location_id, destination.street_address AS destination_street_address, "
          + "destination.postal_code AS destination_postal_code, destination.city AS destination_city, "
          + "destination.country AS destination_country, "

          + "stations.station_id, "
          + "stations.name "
          + "FROM parcels "
          + "JOIN contracts ON parcels.parcel_id = contracts.parcel_id "
          + "JOIN locations AS origin ON contracts.origin_id = origin.location_id "
          + "JOIN locations AS destination ON contracts.destination_id = destination.location_id "
          + "JOIN stations ON contracts.station_id = stations.station_id "
          + "WHERE contracts.contract_id = ?";

      PreparedStatement pst1 = sqlcon.prepareStatement(qs1);
      ResultSet rs = pst1.executeQuery();
      if (rs.next()) {
        String originStreetAddress = rs.getString("origin_street_address");
        String originPos = rs.getString("origin_street_address");

      }

      con.close();
    } catch (Exception e) {
      System.out.println("Error inserting Delivery into database" + e.getMessage());
    }

    return 1;
  }

  public void update(Delivery del) {
    return;
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