package cps.models;

import java.time.LocalDate;

public class Payment {
    private int clientId;
    private int deliveryId;
    private double amount;

    public Payment(int clientId, int deliveryId, double amount) {
        this.amount = amount;
        this.clientId = clientId;
        this.deliveryId = deliveryId;
    }

    public double getAmount() {
        return this.amount;
    }
}
