// PaymentByCreditCardStripe.java
package cps.models.StrategyPatternPayment;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String cvv;
    private String expirationDate;

    public CreditCardPaymentStrategy(String cardNumber, String cvv, String expirationDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment:");
        System.out.println("Card Number: " + cardNumber);
        System.out.println("Card expirationDate: " + expirationDate);
        System.out.println("Amount: " + amount);
        System.out.println("Payment successful using Credit Card!");
    }
}
