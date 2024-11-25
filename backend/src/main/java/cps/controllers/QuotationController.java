package cps.controllers;

import cps.models.Quotation;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/quotation")
public class QuotationController {

    @GetMapping("/{id}")
    public Quotation getQuotation(@PathVariable("id") int id) {
        // Simulate fetching Quotation (replace with actual DB fetch)
        return new Quotation(BigDecimal.valueOf(150.75)); // Example: Quotation for $150.75
    }
}
