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
import Chatbot from '../Chatbot'; // Adjust the path based on your directory structure

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
        <div className="flex items-start justify-start w-full h-full p-5">
            <div className="bg-white bg-opacity-70 p-5 rounded-lg shadow-lg max-w-xs">
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
        </div>
    );
}

export default HomePage;
