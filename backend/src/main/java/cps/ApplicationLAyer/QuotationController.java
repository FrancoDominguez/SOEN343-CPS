package cps.ApplicationLayer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DomainLayer.models.RequestBodies.QuotationRequestBody;

@RestController
public class QuotationController {

  @PostMapping("/quotation")
  public void generateQuotation(@RequestBody QuotationRequestBody quotationInfo) {
    
  }

}
