package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DTO.RequestBodies.ContractRequestBody;
import cps.DomainLayer.ClientService;
import cps.models.Contract;

@RestController
public class ContractController {

  @PostMapping("/contract")
  public ResponseEntity<Contract> generateContract(@RequestBody ContractRequestBody contractInfo) {
    Contract newContract = null;
    ClientService activeClient = new ClientService();
    try {
      newContract = activeClient.addNewContract(contractInfo);
      return new ResponseEntity<Contract>(newContract, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Contract>(newContract, HttpStatus.BAD_REQUEST);
    }
  }
}
