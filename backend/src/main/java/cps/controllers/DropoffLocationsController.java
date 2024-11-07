package cps.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import cps.services.Mysqlcon;
import java.util.ArrayList;

@RestController
public class DropOffLocations {

  @GetMapping("/dropoff-locations")
  public String index() {
    try {
      Mysqlcon sqlConnection = new Mysqlcon();
      String queryString = "SELECT * FROM dropoff_locations";
      String queryResult = sqlConnection.executeQuery(queryString);
      System.out.println(queryResult);
      return queryResult;
    } catch (Exception e) {
      System.out.println("endpoing error");
      System.out.println(e.getMessage());
      return null;
    }
  }

  @GetMapping("/test-query")
  public ArrayList<ArrayList<String>> getQuery() {
    return null;
  }
}
