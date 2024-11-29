//PaymentController.java
package cps.ApplicationLayer;
import cps.DomainLayer.models.StrategyPatternPayment.*;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService();
    }

    @PostMapping("/create")
    public Map<String, String> createPaymentIntent(@RequestBody Map<String, Object> data) {
        if (data == null || !data.containsKey("amount") || data.get("amount") == null) {
            throw new IllegalArgumentException("Missing or invalid 'amount' field");
        }

        if (!data.containsKey("method")) {
            throw new IllegalArgumentException("Missing 'method' field for payment type");
        }

        double amount;
        try {
            amount = ((Number) data.get("amount")).doubleValue();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid 'amount' value. Must be numeric.", e);
        }

        String method = data.get("method").toString();
        String responseMessage;

        // Dynamically set the payment strategy based on the 'method' field
        switch (method.toLowerCase()) {
            case "stripe":
                PaymentStrategy stripePayment = new CreditCardPaymentStrategy();
                paymentService.setPaymentStrategy(stripePayment);
                responseMessage = paymentService.executePayment(amount);
                break;

            case "paypal":
                PaymentStrategy paypalPayment = new PayPalPaymentStrategy();
                paymentService.setPaymentStrategy(paypalPayment);
                responseMessage = paymentService.executePayment(amount);
                break;

            default:
                throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        // Prepare and return the response
        Map<String, String> responseData = new HashMap<>();
        if (method.equalsIgnoreCase("stripe")) {
            responseData.put("clientSecret", responseMessage); // CLIENTSECRETKey the frontend expects
        } else {
            responseData.put("message", responseMessage); // Mock PayPal response
        }
        return responseData;
    }
}
