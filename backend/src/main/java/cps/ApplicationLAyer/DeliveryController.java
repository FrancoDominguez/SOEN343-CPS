package cps.ApplicationLayer;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps.DAO.ContractDAO;
import cps.DTO.RequestBodies.CreateDelivReqBody;
import cps.DTO.ResponseBodies.BasicResponse;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import cps.DomainLayer.models.ShippingStatus;

@RestController
public class DeliveryController {
    ClientService clientService = new ClientService();

    @GetMapping("/delivery/status")
    public ResponseEntity<Object> getDeliveryStatus(@RequestParam int trackingId) {
        try {
            ShippingStatus shippingStatus = clientService.trackOrder(trackingId);

            // Return the status as a string (e.g., "pending", "in transit", "delivered")
            return new ResponseEntity<Object>(shippingStatus, HttpStatus.OK);
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
    public ArrayList<Delivery> getDeliveriesByUserId(@RequestParam int userId){
        try {
            ArrayList<Delivery> deliveries = clientService.viewAllActiveDeliveries(userId);
            return deliveries;
        } catch(Exception e){
            System.err.println(e);
        }
        return null;
    }

    @PutMapping("/delivery")
    public ResponseEntity<Object> updatePickupTime(@RequestParam int deliveryId, @RequestParam String newTime){
        try{
            clientService.updatePickupTime(deliveryId, newTime);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            System.err.println(e);
        }
        return null;
    }
}
