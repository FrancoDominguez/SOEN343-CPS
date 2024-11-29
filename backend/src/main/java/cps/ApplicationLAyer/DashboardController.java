package cps.ApplicationLayer;

import cps.DAO.ContractDAO;
import cps.DAO.DeliveryDAO;
import cps.DTO.ResponseBodies.DashboardResponse;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    private final ContractDAO contractDAO = new ContractDAO();
    private final DeliveryDAO deliveryDAO = new DeliveryDAO();
    //private final DeliveryDAO deliveryDAO = new DeliveryDAO();

    @GetMapping("/dashboard/{clientId}")
    public ResponseEntity<Object> getDashboardDetails(@PathVariable int clientId) {
        try {
            // Fetch contracts and deliveries
            List<Contract> contracts = contractDAO.fetchAllByClientId(clientId);
            List<Delivery> deliveries = deliveryDAO.fetchAllByClientId(clientId);
            //List<Delivery> deliveries = deliveryDAO.findByClientId(clientId);
            DashboardResponse responseObj = new DashboardResponse("Success retrieving deliveries and contracts", contracts, deliveries);
            // Prepare response
            return new ResponseEntity<>(responseObj, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}