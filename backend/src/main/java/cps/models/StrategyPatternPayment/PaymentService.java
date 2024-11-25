// PaymentService.java
package cps.models.StrategyPatternPayment;

import cps.models.Quotation;

public class PaymentService {
    private PaymentStrategy paymentStrategy; // Strategy interface
    private Quotation quotation;            // Quotation object for amount

    public PaymentService(Quotation quotation) {
        this.quotation = quotation;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment() {
        if (quotation == null || paymentStrategy == null) {
            System.out.println("Quotation or Payment Strategy is not set.");
            return;
        }

        double amount = quotation.getPrice().doubleValue(); // Fetch price from Quotation
        System.out.println("Processing payment for amount: " + amount);
        paymentStrategy.processPayment(amount);
    }
}
