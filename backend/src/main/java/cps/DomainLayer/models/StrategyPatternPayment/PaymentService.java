// // PaymentService.java
package cps.DomainLayer.models.StrategyPatternPayment;

public class PaymentService {

    private PaymentStrategy paymentStrategy; // Current payment strategy

    // Setter for injecting the strategy dynamically
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    // Executes the payment using the selected strategy
    public String executePayment(double amount) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy is not set!");
        }
        return paymentStrategy.pay(amount);
    }
}