package cps.models.StrategyPatternPayment;

public class PaymentService {
    private int amount;
    private boolean includeDelivery;
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void processOrder() {
        strategy.collectPaymentDetails();
        if (strategy.validatePaymentDetails()) {
            strategy.pay(getTotal());
        } else {
            System.out.println("Order failed: Payment details are invalid.");
        }
    }

    private int getTotal() {
        return includeDelivery ? amount + 10 : amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIncludeDelivery(boolean includeDelivery) {
        this.includeDelivery = includeDelivery;
    }
}
