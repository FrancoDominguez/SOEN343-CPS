import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, Card, Container, Typography, TextField, Rating } from "@mui/material";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


function ReviewPage() {
  const [trackingID, setTrackingID] = useState("");
  const [showReviewForm, setShowReviewForm] = useState(false);
  const [comment, setComment] = useState("");
  const [rating, setRating] = useState(0);
  const navigate = useNavigate();

  const handleTrackingSubmit = (e) => {
    e.preventDefault();
    if (trackingID) {
      setShowReviewForm(true); 
    }
  };

  const handleReviewSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/reviews", {
        comment: comment,
        rating: rating,
      });
      toast.success("Review submitted successfully!");
      navigate("/home");
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to submit review");
    }
  };
  

  return (
    <Container
      className="h-screen flex items-center rounded justify-center"
      sx={{
        background: "linear-gradient(to bottom, #3d72b4, #525252)",
        zIndex: 10000,
      }}
    >
      <Card
        className="w-full max-w-lg p-6 flex flex-col items-center gap-6 rounded shadow-lg"
        sx={{
          backgroundColor: "rgba(255, 255, 255, 0.85)",
          borderRadius: "12px",
        }}
      >
        <Typography variant="h4" align="center" gutterBottom>
          Review Your Delivery
        </Typography>
        {!showReviewForm && (
          <>
            <Typography variant="body1" align="center" color="textSecondary">
              Please enter the tracking ID of the order you would like to review.
            </Typography>
            <Box
              component="form"
              className="w-full flex flex-col items-center gap-4"
              onSubmit={handleTrackingSubmit}
            >
              <TextField
                fullWidth
                variant="outlined"
                label="Tracking ID"
                placeholder="Enter your tracking ID here"
                value={trackingID}
                onChange={(e) => setTrackingID(e.target.value)}
                InputProps={{
                  style: { padding: "12px 16px", fontSize: "16px" },
                }}
                InputLabelProps={{
                  style: { fontSize: "14px" },
                }}
              />
              <Button
                type="submit"
                variant="contained"
                size="large"
                sx={{
                  width: "100%",
                  backgroundColor: "#3d72b4",
                  "&:hover": { backgroundColor: "#2a588a" },
                }}
              >
                Submit Tracking ID
              </Button>
            </Box>
          </>
        )}

        {showReviewForm && (
          <>
            <Typography variant="h5" align="center" gutterBottom>
              Thank you for leaving us a review!
            </Typography>
            <Box
              component="form"
              className="w-full flex flex-col items-center gap-4"
              onSubmit={handleReviewSubmit}
            >
              <Rating
                name="delivery-rating"
                value={rating}
                onChange={(e, newValue) => setRating(newValue)}
                size="large"
              />
              <TextField
                fullWidth
                variant="outlined"
                label="Comment"
                placeholder="Write your comments here"
                value={comment}
                onChange={(e) => setComment(e.target.value)}
                multiline
                rows={4}
                InputProps={{
                  style: { padding: "12px 16px", fontSize: "16px" },
                }}
                InputLabelProps={{
                  style: { fontSize: "14px" },
                }}
              />
              <Button
                type="submit"
                variant="contained"
                size="large"
                sx={{
                  width: "100%",
                  backgroundColor: "#3d72b4",
                  "&:hover": { backgroundColor: "#2a588a" },
                }}
              >
                Submit Review
              </Button>
            </Box>
          </>
        )}
      </Card>
    </Container>
  );
}

export default ReviewPage;
