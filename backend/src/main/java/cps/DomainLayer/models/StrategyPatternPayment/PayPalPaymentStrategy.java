// //PaymentByPayPal.java
package cps.DomainLayer.models.StrategyPatternPayment;

public class PayPalPaymentStrategy implements PaymentStrategy {

    @Override
    public String pay(double amount) {
        // Mock payment logic
        return "PayPal Payment Successful with Amount = $" + amount;
    }
}
