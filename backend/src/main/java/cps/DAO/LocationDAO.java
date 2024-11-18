package cps.DAO;

import java.sql.ResultSet;

import cps.models.Location;
import cps.utils.Mysqlcon;

public class LocationDAO {
  public Location fetchLocation(int locationId) {
    Location location = null;
    try {
      Mysqlcon con = Mysqlcon.getInstance();
      con.connect();
      String queryString = String.format("SELECT * FROM locations WHERE location_id = %d", locationId);
      con.executeQuery(queryString);
      ResultSet rs = con.getResultSet();
      if (rs.next()) {
        String address = rs.getString("address");
        String postalCode = rs.getString("postal_code");
        String city = rs.getString("city");
        String country = rs.getString("address");
        location = new Location(locationId, address, postalCode, city, country);
      }
      con.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return location;
  }
}
