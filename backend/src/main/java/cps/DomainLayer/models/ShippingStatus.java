package cps.DomainLayer.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShippingStatus {
    private int trackingId;
    private String status;
    private LocalDate eta;

    public ShippingStatus() {
        this.trackingId = -1;
        this.status = "pending";
        this.eta = LocalDate.now().plusDays(3);
    }

    public ShippingStatus(int trackingId, String status, LocalDate eta) {
        this.trackingId = trackingId;
        this.status = status;
        this.eta = eta;
    }

    public Boolean isPending() {
        return true;
    }

    public int getId() {
        return this.trackingId;
    }

    public int getTrackingId() {
        return this.trackingId;
    }

    public String getStatus() {
        return this.status;
    }

    public LocalDate getEta() {
        return this.eta;
    }
}
