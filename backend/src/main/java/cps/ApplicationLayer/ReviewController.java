package cps.ApplicationLayer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cps.DTO.RequestBodies.ReviewRequestBody;
import cps.DomainLayer.ClientService;

@RestController
public class ReviewController {
    ClientService clientService = new ClientService();

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/review")
  public ResponseEntity<String> submitReview(@RequestBody ReviewRequestBody reviewRequest) {
    System.out.println("Processing review submission...\n");
    try {
      // Call the service layer to handle review creation
      clientService.createReview(reviewRequest);

      // Return a success message
      System.out.println("Review submission completed\n");
      return new ResponseEntity<>("Review submitted successfully", HttpStatus.CREATED);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>("Failed to submit review", HttpStatus.BAD_REQUEST);
    }
  }
}
