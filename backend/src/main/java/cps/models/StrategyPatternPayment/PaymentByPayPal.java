package cps.models.StrategyPatternPayment;

public class PaymentByPayPal implements PaymentStrategy {
    private String email;
    private String password;

    @Override
    public void collectPaymentDetails() {
        this.email = "example@paypal.com";
        this.password = "securepassword";
    }

    @Override
    public boolean validatePaymentDetails() {
        return email != null && password != null && !email.isEmpty() && !password.isEmpty();
    }

    @Override
    public void pay(int amount) {
        if (validatePaymentDetails()) {
            System.out.println("Processing payment of " + amount + " using PayPal...");
            System.out.println("Payment successful!");
        } else {
            System.out.println("Payment failed: Invalid PayPal details.");
        }
    }
}
