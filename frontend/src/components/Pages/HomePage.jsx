import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function HomePage() {
    const [trackingNumber, setTrackingNumber] = useState('');
    const [reviews, setReviews] = useState([]);
    const navigate = useNavigate();

    // Fetch reviews from the backend
    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const response = await axios.get("http://localhost:8080/reviews");
                setReviews(response.data); // Assuming the response data is an array of reviews
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

    return (
        <div className="flex flex-col items-start justify-start w-full h-full p-5">
            {/* Tracking box */}
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
                    <button type="submit" className="absolute right-2 top-1/2 transform -translate-y-1/2">
                        <img src="/logos/search.png" alt="Search" className="w-5 h-5" />
                    </button>
                </form>
            </div>

            {/* Reviews Section */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 w-full mt-5">
                {reviews.map((review, index) => (
                    <div
                        key={index}
                        className="bg-white bg-opacity-70 p-4 rounded-lg shadow-md"
                    >
                        <h3 className="text-xl font-semibold text-gray-800 mb-2">Review #{index + 1}</h3>
                        <p className="text-gray-700 mb-2">{review.comment}</p>
                        <p className="text-yellow-500 font-bold">Rating: {review.rating} / 5</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default HomePage;
