// PaymentByCreditCardStripe.java
package cps.DomainLayer.models.StrategyPatternPayment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public String pay(double amount) {
        try {
            // Create a PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100)) // Convert to cents
                    .setCurrency("cad")
                    .addPaymentMethodType("card")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Stripe Payment Successful: PaymentIntent ID = " + intent.getId());
            return intent.getClientSecret();
        } catch (StripeException e) {
            throw new RuntimeException("Stripe Payment Failed: " + e.getMessage());
        }
    }
}
