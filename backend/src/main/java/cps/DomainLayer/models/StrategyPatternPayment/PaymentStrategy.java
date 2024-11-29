// PaymentStrategy.java
package cps.DomainLayer.models.StrategyPatternPayment;

public interface PaymentStrategy {
    String pay(double amount); // Method for executing the payment
}