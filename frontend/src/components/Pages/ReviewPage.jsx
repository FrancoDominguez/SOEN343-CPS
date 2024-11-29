import React, { useState, useEffect } from "react";
import axios from "axios";
import { Box, Card, Container, Typography } from "@mui/material";

function ReviewPage() {
    const [reviews, setReviews] = useState([]);

    // Fetch reviews from the backend
    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await axios.get("http://localhost:8080/reviews");
                setReviews(response.data); // Assuming response.data is an array of reviews
            } catch (error) {
                console.error("Error fetching reviews:", error.response?.data || error);
            }
        };

        fetchReviews();
    }, []);

    return (
        <Container
            className="h-full w-full p-6 flex flex-col rounded items-center"
            sx={{
                background: "linear-gradient(to bottom, #3d72b4, #525252)",
                minHeight: "100vh",
                zIndex: 1000,
                paddingTop: "2rem",
                paddingBottom: "2rem",
            }}
        >
            <Typography
                variant="h4"
                align="center"
                gutterBottom
                sx={{ color: "white", marginBottom: "1.5rem" }}
            >
                Customer Reviews
            </Typography>

            <Box
                className="w-full grid gap-4"
                sx={{
                    display: "grid",
                    gridTemplateColumns: {
                        xs: "1fr",
                        sm: "1fr 1fr",
                        md: "1fr 1fr 1fr",
                    },
                    gap: "1.5rem",
                    width: "100%",
                    maxWidth: "1200px",
                }}
            >
                {reviews.length > 0 ? (
                    reviews.map((review, index) => (
                        <Card
                            key={index}
                            className="p-4 rounded shadow-lg"
                            sx={{
                                padding: "1.5rem",
                                borderRadius: "12px",
                                boxShadow: "0 4px 10px rgba(0, 0, 0, 0.2)",
                                backgroundColor: "rgba(255, 255, 255, 0.85)",
                            }}
                        >
                            <Typography
                                variant="h6"
                                gutterBottom
                                sx={{ fontWeight: "bold", color: "#3d72b4" }}
                            >
                                Review #{index + 1}
                            </Typography>
                            <Typography
                                variant="body1"
                                gutterBottom
                                sx={{
                                    fontSize: "1rem",
                                    color: "#333",
                                    marginBottom: "1rem",
                                    lineHeight: "1.5",
                                }}
                            >
                                {review.comment}
                            </Typography>
                            <Typography
                                variant="body2"
                                sx={{
                                    color: "#fbc02d",
                                    fontWeight: "bold",
                                    fontSize: "0.9rem",
                                }}
                            >
                                Rating: {review.rating} / 5
                            </Typography>
                        </Card>
                    ))
                ) : (
                    <Typography
                        variant="h6"
                        align="center"
                        sx={{ color: "white", marginTop: "2rem" }}
                    >
                        No reviews available.
                    </Typography>
                )}
            </Box>
        </Container>
    );
}

export default ReviewPage;
