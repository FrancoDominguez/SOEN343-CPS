//PaymentController.java

package cps.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import cps.models.Payment;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    // In-memory list to store payments
    private final List<Payment> paymentStore = new ArrayList<>();

    @PostMapping("/create")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
        long amount = ((Number) data.get("amount")).longValue(); // Amount in cents
        String currency = "cad";

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .addPaymentMethodType("card")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        // Store the payment details in memory
        Payment payment = new Payment(intent.getId(), amount, currency, intent.getStatus());
        paymentStore.add(payment);

        // Return the client secret to the frontend
        Map<String, String> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        return responseData;
    }

    @GetMapping("/all")
    public List<Payment> getAllPayments() {
        return paymentStore; // Return all stored payments
    }
}