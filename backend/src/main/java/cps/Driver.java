package cps;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.stripe.exception.StripeException;

import cps.ApplicationLayer.PaymentController;
import cps.utils.MapsService;
import cps.utils.Pair;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {

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
