import React from 'react';

const TrackingInfo = () => {
    // Sample hardcoded tracking data
    const trackingData = {
        trackingId: "1234567890",
        status: "Item delayed - stay tuned for updates",
        sender: "Unavailable",
        deliveryPreference: "None",
        updates: [
            {
                date: "Oct. 29",
                time: "5:25 PM",
                progress: "Electronic information submitted by shipper"
            }
        ]
    };

    return (
        <div className="max-w-md mx-auto p-6 bg-white border border-gray-300 rounded-lg shadow-md">
            <h2 className="text-2xl font-semibold text-center mb-4 text-gray-800">Track Your Package</h2>

            <div className="mb-4">
                <p className="text-lg text-gray-700"><strong>Tracking ID:</strong> {trackingData.trackingId}</p>
                <p className="text-lg text-gray-700"><strong>Status:</strong> {trackingData.status}</p>
                <p className="text-lg text-gray-700"><strong>Sender:</strong> {trackingData.sender}</p>
                <p className="text-lg text-gray-700"><strong>Delivery Preference:</strong> {trackingData.deliveryPreference}</p>
            </div>

            <div>
                <h3 className="text-xl font-semibold text-gray-800 mb-2">Latest Updates</h3>
                {trackingData.updates.map((update, index) => (
                    <div key={index} className="p-3 border-t border-gray-200">
                        <p className="text-md text-gray-600"><strong>Date:</strong> {update.date}</p>
                        <p className="text-md text-gray-600"><strong>Time:</strong> {update.time}</p>
                        <p className="text-md text-gray-600"><strong>Progress:</strong> {update.progress}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default TrackingInfo;