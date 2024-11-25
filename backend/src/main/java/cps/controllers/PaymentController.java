//PaymentController.java
package cps.controllers;

import cps.models.Quotation;
import cps.models.StrategyPatternPayment.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/process")
    public Map<String, String> processPayment(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        double quotationPrice = (double) request.get("quotationPrice");

        // Log the incoming request for debugging
        System.out.println("Received request: " + request);

        // Simulate creating a quotation (you can replace this with a DB fetch)
        Quotation quotation = new Quotation(BigDecimal.valueOf(quotationPrice));

        // Initialize the PaymentService with the quotation
        PaymentService paymentService = new PaymentService(quotation);

        // Determine the payment strategy based on the method
        PaymentStrategy strategy;
        if ("creditCard".equalsIgnoreCase(method)) {
            Map<String, String> cardDetails = (Map<String, String>) request.get("cardDetails");
            if (cardDetails == null || cardDetails.get("cardNumber") == null || cardDetails.get("cvc") == null || cardDetails.get("expirationDate") == null) {
                throw new IllegalArgumentException("Card details are required for credit card payments.");
            }
            strategy = new CreditCardPaymentStrategy(
                cardDetails.get("cardNumber"),
                cardDetails.get("cvc"),
                cardDetails.get("expirationDate")
            );
        } else if ("paypal".equalsIgnoreCase(method)) {
            Map<String, String> paypalCredentials = (Map<String, String>) request.get("paypalCredentials");
            if (paypalCredentials == null || paypalCredentials.get("username") == null || paypalCredentials.get("password") == null) {
                throw new IllegalArgumentException("PayPal credentials are required for PayPal payments.");
            }
            strategy = new PayPalPaymentStrategy(
                paypalCredentials.get("username"),
                paypalCredentials.get("password")
            );
        } else {
            throw new IllegalArgumentException("Invalid payment method.");
        }

        // Process the payment
        paymentService.setPaymentStrategy(strategy);
        paymentService.processPayment();

        // Return a success response
        return Map.of(
            "status", "success",
            "method", method,
            "amount", String.valueOf(quotation.getPrice())
        );
    }
}
