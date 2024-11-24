package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.ClientService;
import cps.models.DeliveryStatus;

@RestController
public class DeliveryController {

    @GetMapping("/delivery/status")
    public ResponseEntity<DeliveryStatus> getDeliveryStatus(@RequestParam int trackingId) {
        try {
            ClientService clientService = new ClientService();
            DeliveryStatus status = clientService.trackOrder(trackingId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
