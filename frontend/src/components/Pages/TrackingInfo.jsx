import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const TrackingInfo = () => {
    const { trackingNumber } = useParams();
    const [trackingData, setTrackingData] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const statusSteps = ["Pending", "Shipped", "Out for Delivery", "Delivered"];

    const getCurrentStep = (status) => {
        const index = statusSteps.findIndex(
            (step) => step.toLowerCase() === status.toLowerCase()
        );
        return index !== -1 ? index : 0;
    };

    const formatDate = (dateString) => {
        const options = { year: "numeric", month: "long", day: "numeric" };
        return new Date(dateString).toLocaleDateString("en-US", options);
    };

    useEffect(() => {
        const fetchTrackingData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/delivery/status?trackingId=${trackingNumber}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch tracking data");
                }
                const data = await response.json();
                setTrackingData(data);
                setError(null);
            } catch (err) {
                setError(err.message);
                setTrackingData(null);
            } finally {
                setLoading(false);
            }
        };

        fetchTrackingData();
    }, [trackingNumber]);

    if (loading) {
        return <div className="text-center">Loading...</div>;
    }

    if (error) {
        return <div className="text-center text-red-500">{error}</div>;
    }

    const currentStep = trackingData ? getCurrentStep(trackingData.status) : 0;

    return (
        <div className="w-full h-screen flex flex-col justify-start items-center pt-10">
            <h2 className="text-4xl font-bold text-gray-800 mb-6">Track Your Package</h2>

            {trackingData ? (
                <div className="w-full max-w-4xl">
                    <div className="mb-6">
                        <p className="text-lg text-gray-700"><strong>Tracking ID:</strong> {trackingNumber}</p>
                        <p className="text-lg text-gray-700"><strong>Status:</strong> {trackingData.status}</p>
                        <p className="text-lg text-gray-700"><strong>ETA:</strong> {formatDate(trackingData.eta)}</p>
                        <p className="text-lg text-gray-700"><strong>Destination:</strong></p>
                        <p className="text-md text-gray-600">
                            {`${trackingData.destination.streetAddress}, ${trackingData.destination.city}, ${trackingData.destination.country} ${trackingData.destination.postalCode}`}
                        </p>
                    </div>

                    {/* Progress Bar */}
                    <div>
                        <div className="flex justify-between mb-4">
                            {statusSteps.map((step, idx) => (
                                <span
                                    key={idx}
                                    className={`text-sm font-medium ${
                                        idx <= currentStep ? "text-blue-600" : "text-gray-400"
                                    }`}
                                >
                                    {step}
                                </span>
                            ))}
                        </div>
                        <div className="relative w-full h-4 bg-gray-200 rounded-full">
                            <div
                                className="absolute top-0 left-0 h-full bg-blue-600 rounded-full transition-all duration-500"
                                style={{ width: `${((currentStep + 1) / statusSteps.length) * 100}%` }}
                            ></div>
                        </div>
                    </div>
                </div>
            ) : (
                <p className="text-center text-gray-700">No tracking information available.</p>
            )}
        </div>
    );
};

export default TrackingInfo;
