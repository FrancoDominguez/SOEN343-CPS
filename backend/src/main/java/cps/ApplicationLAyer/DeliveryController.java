package cps.ApplicationLayer;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps.DAO.ContractDAO;
import cps.DTO.RequestBodies.CreateDelivReqBody;
import cps.DTO.ResponseBodies.BasicResponse;
import cps.DTO.ResponseBodies.TrackingResponse;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.Services.MovementService;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.ShippingStatus;

@RestController
public class DeliveryController {
    ClientService clientService = new ClientService();

    @GetMapping("/delivery/status")
    public ResponseEntity<Object> getDeliveryStatus(@RequestParam int trackingId) {
        try {
            ClientService clientService = new ClientService();
            Delivery delivery = clientService.trackOrder(trackingId);
            ShippingStatus shipStatus = delivery.getStatus();
            TrackingResponse responseObj = new TrackingResponse(shipStatus.getStatus(), shipStatus.getEta(),
                    delivery.getDestination());

            return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Tracking ID not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // create delivery
    @PostMapping("/delivery")
    public ResponseEntity<Object> createNewDelivery(@RequestBody CreateDelivReqBody body) {
        try {
            int contractId = body.getContractId();
            System.out.println("\n received contract id: \n" + contractId);
            ClientService clientService = new ClientService();
            ContractDAO contractDAO = new ContractDAO();
            Contract contract = contractDAO.fetchById(contractId);
            clientService.createDelivery(contract);
            BasicResponse response = new BasicResponse("Delivery has been created");
            return new ResponseEntity<Object>(response, HttpStatus.OK);
        } catch (Error e) {
            System.out.println("Error in create delivery endpoint" + e.getMessage());
        }
        return null;
    }

    @GetMapping("/delivery")
    public ArrayList<Delivery> getDeliveriesByUserId(@RequestParam int userId) {
        try {
            ArrayList<Delivery> deliveries = clientService.viewAllActiveDeliveries(userId);
            return deliveries;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @GetMapping("/move-shipments")
    public void moveShipments() {
        System.out.println("Movement request received");
        MovementService movements = new MovementService();
        movements.updateShippingStatus();
    }
}
