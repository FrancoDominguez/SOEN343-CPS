package cps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cps.models.DropoffLocation;
import cps.services.Mysqlcon;
import java.sql.ResultSet;
import java.util.ArrayList;

@RestController
public class DropoffLocationsController {

  @GetMapping("/dropoff-locations")
  public ArrayList<DropoffLocation> index() {
    try {
      Mysqlcon mysqlConnection = new Mysqlcon();
      mysqlConnection.connect();
      String queryString = "SELECT * FROM dropoff_locations";
      mysqlConnection.executeQuery(queryString);
      ResultSet rs = mysqlConnection.getResultSet();
      ArrayList<DropoffLocation> locations = new ArrayList<DropoffLocation>();
      while (rs.next()) {
        String name = rs.getString("name");
        String address = rs.getString("address");
        locations.add(new DropoffLocation(name, address));
      }

      queryString = "INSERT INTO dropoff_locations (name, address) VALUES ()";
      mysqlConnection.executeQuery(queryString);

      mysqlConnection.close();
      System.out.println(locations);
      return locations;
    } catch (Exception e) {
      System.out.println("endpoint error\n" + e.getMessage());
      return null;
    }
  }
}
