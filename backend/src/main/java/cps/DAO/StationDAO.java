package cps.DAO;

import java.sql.ResultSet;

import cps.models.Station;
import cps.utils.Mysqlcon;

public class StationDAO {
  public Station fetchLocation(int locationId) {
    Station station = null;
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format("SELECT * FROM stations WHERE station_id = %d", locationId);
      con.executeQuery(queryString);
      ResultSet rs = con.getResultSet();
      if (rs.next()) {
        String name = rs.getString("name");
        String address = rs.getString("address");
        String postalCode = rs.getString("postal_code");
        String city = rs.getString("city");
        String country = rs.getString("address");
        station = new Station(locationId, name, address, postalCode, city, country);
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return station;
  }
}
