package cps.ApplicationLayer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cps.utils.Mysqlcon;

import java.sql.ResultSet;
import java.util.ArrayList;

@RestController
public class DropoffLocationsController {

  // example of a get request
  // @GetMapping("/dropoff-locations")
  // public ArrayList<DropoffLocation> getDropoffLocations() {
  // try {
  // establish a connection object
  // Mysqlcon mysqlConnection = new Mysqlcon();
  // mysqlConnection.connect();

  // // run the query string, the result is saved to the connection object
  // String queryString = "SELECT * FROM dropoff_locations";
  // mysqlConnection.executeQuery(queryString);
  // ResultSet rs = mysqlConnection.getResultSet();

  // // Iterate through each row of the table with rs.next()
  // ArrayList<DropoffLocation> locations = new ArrayList<DropoffLocation>();
  // while (rs.next()) {
  // // create an object out of each row
  // String name = rs.getString("name");
  // String address = rs.getString("address");
  // locations.add(new DropoffLocation(name, address));
  // }

  // // remember to close the connection
  // mysqlConnection.close();

  // System.out.println(locations);

  // // return the object, spring boot will parse it into a JSON automatically
  // return locations;

  // } catch (Exception e) {
  // System.out.println("endpoint error\n" + e.getMessage());
  // return null;
  // }
  // }
}
