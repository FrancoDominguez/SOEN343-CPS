package cps;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.stripe.exception.StripeException;

import cps.controllers.PaymentController;
import cps.services.MapsService;
import cps.utils.Pair;

@SpringBootApplication
public class Driver {
  public static void main(String[] args) {
    // Run the Spring Boot application and get the application context
    ApplicationContext context = SpringApplication.run(Driver.class, args);

    // Test the APIs
    // testMapsApi();
    testPaymentApi(context);
  }

  public static void testMapsApi() {
    MapsService mapService = new MapsService();
    String origin = "13069 rue Ramsay h8z2z7 Quebec";
    String destination = "21 Crois Donnacona h9b2s3 Quebec";
    Pair<Integer, Integer> pair = mapService.getDurationDistance(origin, destination);
    System.out.printf("duration in seconds: %s, distance in meters: %s%n", pair.getFirst(), pair.getSecond());
  }

  public static void testPaymentApi(ApplicationContext context) {
    PaymentController paymentController = context.getBean(PaymentController.class);
    Map<String, Object> requestData = new HashMap<>();
    requestData.put("amount", 1000L); // 1000 cents = $10.00 CAD

    try {
      Map<String, String> response = paymentController.createPaymentIntent(requestData);
      System.out.println("PaymentIntent created successfully!");
      System.out.println("Client Secret: " + response.get("clientSecret"));
    } catch (StripeException e) {
      System.err.println("StripeException occurred: " + e.getMessage());
    }
  }
}
