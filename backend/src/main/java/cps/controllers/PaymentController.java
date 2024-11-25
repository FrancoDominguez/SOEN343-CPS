//PaymentController.java
package cps.controllers;

import cps.models.Quotation;
import cps.models.StrategyPatternPayment.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

@GetMapping("/quotation/{id}")
public ResponseEntity<Map<String, String>> getQuotationAmount(@PathVariable("id") Long quotationId) {
    // Simulated fetching from a database
    Map<Long, Quotation> quotations = Map.of(
        1L, new Quotation(BigDecimal.valueOf(150.75)),
        2L, new Quotation(BigDecimal.valueOf(155.75)),
        3L, new Quotation(BigDecimal.valueOf(140.75))
    );

    Quotation quotation = quotations.get(quotationId);
    if (quotation == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Quotation not found for ID: " + quotationId));
    }

    return ResponseEntity.ok(Map.of(
        "quotationId", String.valueOf(quotationId),
        "amount", String.valueOf(quotation.getPrice())
    ));
}

    @PostMapping("/process")
    public Map<String, String> processPayment(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        double quotationPrice = (double) request.get("quotationPrice");

        // Simulate fetching a quotation
        Quotation quotation = new Quotation(BigDecimal.valueOf(quotationPrice));

        PaymentService paymentService = new PaymentService(quotation);

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

        paymentService.setPaymentStrategy(strategy);
        paymentService.processPayment();

        return Map.of(
            "status", "success",
            "method", method,
            "amount", String.valueOf(quotation.getPrice())
        );
    }
}
