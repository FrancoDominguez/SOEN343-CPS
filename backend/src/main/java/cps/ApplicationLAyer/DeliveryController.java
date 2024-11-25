package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.ClientService;
import cps.models.ShippingStatus;

@RestController
public class DeliveryController {

    @GetMapping("/delivery/status")
    public ResponseEntity<Object> getDeliveryStatus(@RequestParam int trackingId) {
        try {
            ClientService clientService = new ClientService();
            ShippingStatus shippingStatus = clientService.trackOrder(trackingId);

            // Return the status as a string (e.g., "pending", "in transit", "delivered")
            return new ResponseEntity<Object>(shippingStatus, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tracking ID not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
