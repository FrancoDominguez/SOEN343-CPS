//PaymentByPayPal.java
package cps.models.StrategyPatternPayment;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPaymentStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment:");
        System.out.println("Email: " + email);
        System.out.println("Amount: " + amount);
        System.out.println("Payment successful using PayPal!");
    }
}
