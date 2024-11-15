package cps.models.StrategyPatternPayment;

public class PaymentByCreditCardStripe implements PaymentStrategy {
    private CreditCard card;

    @Override
    public void collectPaymentDetails() {
        this.card = new CreditCard("4242424242424242", "12/25", "123", 0);
    }

    @Override
    public boolean validatePaymentDetails() {
        return card != null &&
                card.getCardNumber().length() == 16 &&
                card.getCvv().length() == 3;
    }

    @Override
    public void pay(int amount) {
        // Simulate Stripe payment logic
        if (validatePaymentDetails()) {
            System.out.println("Processing payment of " + amount + " using Stripe...");
            card.setAmount(amount);
            System.out.println("Payment successful!");
        } else {
            System.out.println("Payment failed: Invalid credit card details.");
        }
    }
}
