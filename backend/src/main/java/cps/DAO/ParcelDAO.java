package cps.DAO;

import java.sql.ResultSet;

import cps.models.Parcel;
import cps.utils.Mysqlcon;

public class ParcelDAO {
  public Parcel fetchById(int parcelId) {
    Parcel parcel = null;
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format("SELECT * FROM parcels WHERE parcel_id = %d", parcelId);
      con.formerExecuteQuery(queryString);
      ResultSet rs = con.getResultSet();
      if (rs.next()) {
        double height = rs.getDouble("height");
        double width = rs.getDouble("width");
        double length = rs.getDouble("length");
        double weight = rs.getDouble("weight");
        Boolean isFragile = rs.getBoolean("is_fragile");
        parcel = new Parcel(parcelId, length, width, height, weight, isFragile);
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return parcel;
  }

  public void insert(Parcel parcel) {
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format(
          "START TRANSACTION; INSERT INTO parcels (height, width, length, weight, is_fragile) " +
              "VALUES (%f, %f, %f, %f, %b); " +
              "SET @locationId = LAST_INSERT_ID(); " +
              "SELECT @locationId AS locationId;" +
              "COMMIT",
          parcel.getHeight(), parcel.getWidth(), parcel.getLength(), parcel.getWeight(), parcel.isFragile());
      con.formerExecuteUpdate(queryString);
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
