import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
    Button,
    Box,
    Typography,
    Modal,
    Rating,
    TextField,
} from "@mui/material";
import { toast } from "react-toastify";

function HomePage() {
    const [trackingNumber, setTrackingNumber] = useState("");
    const [reviews, setReviews] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [comment, setComment] = useState("");
    const [rating, setRating] = useState(0);

    const navigate = useNavigate();

    // Fetch reviews from the backend
    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await axios.get("http://localhost:8080/reviews");
                setReviews(response.data); // Assuming response.data is an array of reviews
            } catch (error) {
                console.error("Error fetching reviews:", error);
            }
        };

        fetchReviews();
    }, []);

    const handleSearch = (e) => {
        e.preventDefault();
        if (trackingNumber) {
            navigate(`/tracking/${trackingNumber}`);
        }
    };

    const handleReviewSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post("http://localhost:8080/reviews", {
                comment,
                rating,
            });
            toast.success("Review submitted successfully!");
            setIsModalOpen(false);
        } catch (error) {
            toast.error("Failed to submit review");
        }
    };

    return (
        <div className="flex flex-col items-start justify-start w-full h-full p-5">
            {/* Tracking Box */}
            <div className="bg-white bg-opacity-70 p-5 rounded-lg shadow-lg max-w-xs mb-5">
                <h1 className="text-2xl font-bold text-gray-800 mb-2">Your Mail Matters</h1>
                <p className="text-gray-800">
                    From our hands to your doorstep, CPS delivers with a smile! Speed,
                    care, and a touch of Canadian love in every package since '55!
                </p>
                <form onSubmit={handleSearch} className="mt-4 relative">
                    <input
                        type="text"
                        placeholder="Track your Order"
                        value={trackingNumber}
                        onChange={(e) => setTrackingNumber(e.target.value)}
                        className="w-full p-2 pl-4 pr-10 rounded-full border border-gray-300 focus:outline-none"
                    />
                    <button
                        type="submit"
                        className="absolute right-2 top-1/2 transform -translate-y-1/2"
                    >
                        <img src="/logos/search.png" alt="Search" className="w-5 h-5" />
                    </button>
                </form>
            </div>

            {/* Leave a Review Section */}
            <div className="bg-white bg-opacity-70 p-5 rounded-lg shadow-lg w-full md:w-1/2">
                <Typography variant="h5" className="text-gray-800 mb-4">
                    Leave a Review
                </Typography>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={() => setIsModalOpen(true)}
                >
                    Open Review Form
                </Button>
            </div>

            {/* Modal for Reviews */}
            <Modal
                open={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                aria-labelledby="leave-review-modal"
                aria-describedby="modal-to-leave-a-review"
            >
                <Box
                    sx={{
                        position: "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                        bgcolor: "background.paper",
                        boxShadow: 24,
                        p: 4,
                        borderRadius: "12px",
                        width: 400,
                    }}
                >
                    <Typography id="leave-review-modal" variant="h6" gutterBottom>
                        Leave a Review
                    </Typography>
                    <form onSubmit={handleReviewSubmit} className="flex flex-col gap-4">
                        <Rating
                            name="delivery-rating"
                            value={rating}
                            onChange={(e, newValue) => setRating(newValue)}
                            size="large"
                        />
                        <TextField
                            label="Comment"
                            multiline
                            rows={4}
                            value={comment}
                            onChange={(e) => setComment(e.target.value)}
                            fullWidth
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                        >
                            Submit Review
                        </Button>
                    </form>
                </Box>
            </Modal>
        </div>
    );
}

export default HomePage;
