import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, Card, Container, Typography, TextField } from "@mui/material";

function ReviewPage() {
    const [trackingID, setTrackingID] = useState("");
    const navigate = useNavigate();

    const handleReviewSubmit = (e) => {
        e.preventDefault();
        if (trackingID.trim()) {
            navigate(`/review/${trackingID}`);
        }
    };

    return (
        <Container
            className="h-screen flex items-center justify-center"
            sx={{
                background: "linear-gradient(to bottom, #ece9e6, #ffffff)",
            }}
        >
            <Card
                className="w-full max-w-lg p-6 flex flex-col items-center gap-6 shadow-lg"
                sx={{
                    backgroundColor: "rgba(255, 255, 255, 0.85)",
                    borderRadius: "12px",
                }}
            >
                <Typography variant="h4" align="center" gutterBottom>
                    Review Your Delivery
                </Typography>
                <Typography variant="body1" align="center" color="textSecondary">
                    Please enter the tracking ID of the order you would like to review.
                </Typography>
                <Box
                    component="form"
                    className="w-full flex flex-col items-center gap-4"
                    onSubmit={handleReviewSubmit}
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
            </Card>
        </Container>
    );
}

export default ReviewPage;
