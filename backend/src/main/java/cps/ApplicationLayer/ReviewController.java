package cps.ApplicationLayer;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cps.DAO.ReviewDAO;
import cps.DTO.RequestBodies.ReviewRequestBody;
import cps.DomainLayer.ClientService;
import cps.DomainLayer.models.Review;

@RestController
public class ReviewController {
    ClientService clientService = new ClientService();

  @CrossOrigin(origins = "http://localhost:5173")


  @PostMapping("/reviews")
  public ResponseEntity<String> createReview(@RequestBody ReviewRequestBody reviewRequest) {
      try {
          ClientService.createReview(reviewRequest);
          return new ResponseEntity<>("Review submitted successfully!", HttpStatus.CREATED);
      } catch (Exception e) {
          e.printStackTrace();
          return new ResponseEntity<>("Failed to submit review", HttpStatus.BAD_REQUEST);
      }
  }

      @GetMapping("/reviews")
    public List<Review> getAllReviews() {
        try {
            return new ReviewDAO().fetchAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching reviews");
        }
    }

  
}
