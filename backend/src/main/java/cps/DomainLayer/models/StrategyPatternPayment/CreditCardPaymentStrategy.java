// PaymentByCreditCardStripe.java
package cps.DomainLayer.models.StrategyPatternPayment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expirationDate;

    public CreditCardPaymentStrategy(){}

    public CreditCardPaymentStrategy(String cardNumber, String cvv, String expirationDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }
    @Override
    public String pay(double amount) {
        try {
            // Create a PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount)) // Convert to cents
                    .setCurrency("cad")
                    .addPaymentMethodType("card")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Stripe Payment Successful: PaymentIntent ID = " + intent.getId());
            System.out.println("Stripe Payment Successful with Amount = $" + amount/100);
            return intent.getClientSecret();
        } catch (StripeException e) {
            throw new RuntimeException("Stripe Payment Failed: " + e.getMessage());
        }
    }
}
