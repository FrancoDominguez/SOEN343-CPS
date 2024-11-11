package cps.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/create")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) throws StripeException {
        long amount = ((Number) data.get("amount")).longValue(); // Amount in smallest currency unit (e.g., pence)

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("cad")
                .addPaymentMethodType("card")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("clientSecret", intent.getClientSecret());
        return responseData;
    }
}
