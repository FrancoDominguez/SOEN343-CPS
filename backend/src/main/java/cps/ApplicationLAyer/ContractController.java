package cps.ApplicationLayer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DTO.RequestBodies.QuotationRequestBody;

@RestController
public class ContractController {

  @PostMapping("/contract")
  public void generateContract(@RequestBody  contract) {

  }
}
