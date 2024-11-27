package cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cps.utils.MapsService;
import cps.utils.Pair;

import static cps.Tests.clearTable;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    System.out.println("Clearing all tables\n");
    clearTable("contracts");
    clearTable("locations");
    clearTable("parcels");
    System.out.println("All tables are now clear\n");

    SpringApplication.run(Driver.class, args);
    
    

    
  }

  public static void testMapsApi() {
    MapsService mapService = new MapsService();
    String origin = "13069 rue Ramsay h8z2z7 Quebec";
    String destination = "21 Crois Donnacona h9b2s3 Quebec";
    Pair<Integer, Integer> pair = mapService.getDurationDistance(origin, destination);
    System.out.printf("duration in seconds: %s, distance in meters: %s", pair.getFirst(), pair.getSecond());
  }

}
