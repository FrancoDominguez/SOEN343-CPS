package cps.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DTO.RequestBodies.QuotationRequestBody;

@RestController
public class QuotationController {

  @PostMapping("/quotation")
  public void generateQuotation(@RequestBody QuotationRequestBody quotationInfo) {
    
  }

}
