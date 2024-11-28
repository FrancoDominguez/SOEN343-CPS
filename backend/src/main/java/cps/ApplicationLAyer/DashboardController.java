package cps.ApplicationLayer;

import cps.DAO.ContractDAO;
import cps.DAO.DeliveryDAO;
import cps.DomainLayer.models.Contract;
import cps.DomainLayer.models.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

    private final ContractDAO contractDAO = new ContractDAO();
    //private final DeliveryDAO deliveryDAO = new DeliveryDAO();

    @GetMapping("/dashboard/{clientId}")
    public ResponseEntity<Map<String, Object>> getDashboardDetails(@PathVariable int clientId) {
        try {
            // Fetch contracts and deliveries
            List<Contract> contracts = contractDAO.fetchAllByClientId(clientId);
            //List<Delivery> deliveries = deliveryDAO.findByClientId(clientId);

            // Prepare response
            return ResponseEntity.ok(Map.of(
                "contracts", contracts
                //"deliveries", deliveries
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

