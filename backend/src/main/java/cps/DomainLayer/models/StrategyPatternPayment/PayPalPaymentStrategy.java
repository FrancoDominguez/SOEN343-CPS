// //PaymentByPayPal.java
package cps.DomainLayer.models.StrategyPatternPayment;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPaymentStrategy(){}

    public PayPalPaymentStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }
    

    @Override
    public String pay(double amount) {
        // Mock payment logic
        System.out.println("PayPal Payment Successful with Amount = $" + amount/100);
        return "PayPal Payment Successful with Amount = $" + amount/100;
    }
}
