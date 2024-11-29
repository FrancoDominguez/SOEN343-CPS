import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Chatbot from '../Chatbot'; // Adjust the path based on your directory structure

function HomePage() {
    const [trackingNumber, setTrackingNumber] = useState('');
    const navigate = useNavigate();

    const handleSearch = (e) => {
        e.preventDefault();
        if (trackingNumber) {
            navigate(`/tracking/${trackingNumber}`);
        }
    };

    return (
        <div className="relative flex items-start justify-start w-full h-full p-5">
            {/* Main content */}
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
                    <button type="submit" className="absolute right-2 top-1/2 transform -translate-y-1/2">
                        <img src="/logos/search.png" alt="Search" className="w-5 h-5" />
                    </button>
                </form>
            </div>

            {/* Chatbot */}
            <Chatbot />
        </div>
    );
}

export default HomePage;
