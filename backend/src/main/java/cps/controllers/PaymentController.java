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
        Map<String, String> cardDetails = (Map<String, String>) request.get("cardDetails");
        Map<String, String> paypalCredentials = (Map<String, String>) request.get("paypalCredentials");

        // Create Quotation (simulated, replace with DB fetch)
        Quotation quotation = new Quotation(BigDecimal.valueOf(quotationPrice));

        // Initialize PaymentService
        PaymentService paymentService = new PaymentService(quotation);

        // Set payment strategy based on method
        PaymentStrategy strategy;
        if ("stripe".equalsIgnoreCase(method)) {
            if (cardDetails == null || cardDetails.get("cardNumber") == null) {
                throw new IllegalArgumentException("Card details are required for Stripe payments.");
            }
            strategy = new CreditCardPaymentStrategy(
                cardDetails.get("cardNumber"),
                cardDetails.get("cvv"),
                cardDetails.get("expirationDate")
            );
        } else if ("paypal".equalsIgnoreCase(method)) {
            if (paypalCredentials == null || paypalCredentials.get("email") == null) {
                throw new IllegalArgumentException("PayPal credentials are required for PayPal payments.");
            }
            strategy = new PayPalPaymentStrategy(
                paypalCredentials.get("email"),
                paypalCredentials.get("password")
            );
        } else {
            throw new IllegalArgumentException("Invalid payment method.");
        }

        // Process the payment
        paymentService.setPaymentStrategy(strategy);
        paymentService.processPayment();

        // Return response
        return Map.of(
            "status", "success",
            "method", method,
            "amount", String.valueOf(quotation.getPrice())
        );
    }
}
