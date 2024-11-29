package cps.DomainLayer.Services;

import java.util.ArrayList;
import java.util.Random;

import cps.DAO.ShippingStatusDAO;
import cps.DomainLayer.models.ShippingStatus;

public class MovementService {
  public void updateShippingStatus() {
    ShippingStatusDAO shippingStatusDAO = new ShippingStatusDAO();
    ArrayList<ShippingStatus> statuses = shippingStatusDAO.fretchAll();
    Random random = new Random();
    for (int i = 0; i < statuses.size(); i++) {
      boolean randomBoolean = random.nextInt(100) < 25; // 25% chance of being true

      String status = statuses.get(i).getStatus();
      int trackingId = statuses.get(i).getTrackingId();
      if (randomBoolean) {
        if (status.equals("pending")) {
          shippingStatusDAO.setStatus(trackingId, "shipped");
        }
        if (status.equals("shipped")) {
          shippingStatusDAO.setStatus(trackingId, "out for delivery");
        }
        if (status.equals("out for delivery")) {
          shippingStatusDAO.setStatus(trackingId, "delivered");
        }
      }
    }
  }
}
