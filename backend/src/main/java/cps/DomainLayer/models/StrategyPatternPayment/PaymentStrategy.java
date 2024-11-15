package cps.DomainLayer.models.StrategyPatternPayment;

public interface PaymentStrategy {
    void collectPaymentDetails();
    boolean validatePaymentDetails();
    void pay(int amount);
}

