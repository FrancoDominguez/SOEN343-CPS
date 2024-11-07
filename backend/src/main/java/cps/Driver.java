package cps;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import cps.services.Mysqlcon;
import cps.services.MapsService;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    MapsService mapService = new MapsService();
    String origin = "2144 Guy St, Montreal, Quebec H3H 2L8";
    String destination = "1909 Av. des Canadiens-de-Montréal, Montréal, QC H3B 5E8";
    mapService.getDistance(origin, destination);
    try {
      Mysqlcon mysql = new Mysqlcon();
      mysql.executeQuery("SQL QUERY HERE");

    } catch (Exception e) {
      System.out.println(e);
    }
    SpringApplication.run(Driver.class, args);
  }
}
