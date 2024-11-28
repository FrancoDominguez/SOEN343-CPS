package cps.ApplicationLayer;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps.DTO.RequestBodies.ContractRequestBody;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.Contract;

@RestController
public class ContractController {
  ClientService activeClient = new ClientService();

  @PostMapping("/contract")
  public ResponseEntity<Contract> generateContract(@RequestBody ContractRequestBody contractInfo) {
    System.out.println("Processing contract request...\n");
    Contract newContract = null;
    try {
      newContract = activeClient.addNewContract(contractInfo);
      System.out.println("Contract request completed");
      return new ResponseEntity<Contract>(newContract, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Contract>(newContract, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/contract")
  public ArrayList<Contract> getContractsById(@RequestParam int userId){
    System.out.println("Fetching contracts by userId...\n");
    ArrayList<Contract> contracts;
    try{
      contracts = activeClient.viewAllActiveContracts(userId);
      return contracts;
    }catch(Exception e){
    }
    return null;
  }

  @DeleteMapping("/contract")
  public ResponseEntity<Object> deleteContractById(@RequestParam int contractId){
    System.out.println("Fetching contracts by userId...\n");
    try{
      activeClient.deleteContract(contractId);
      return new ResponseEntity<Object>(HttpStatus.OK);
    }catch(Exception e){
      return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }
  }

}
