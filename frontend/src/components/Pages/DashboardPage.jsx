// DashboardPage.js

import React from 'react';
import './DashboardPage.css';

function DashboardPage() {
    return (
        <div className="dashboard-container">
            <div className="dashboard-content">
                <div className="text-box">
                    <h1>Your Mail Matters</h1>
                    <p>
                        From our hands to your doorstep, CPS delivers with a smile! Speed,
                        care, and a touch of Canadian love in every package since '55!
                    </p>
                </div>

                <div className="track-order">
                    <input
                        type="text"
                        placeholder="Track your Order"
                        className="track-order-input"
                    />
                    <button className="track-order-button">
                        <img src="/logos/search.png" alt="Search" />
                    </button>
                </div>
            </div>
        </div>
    );
}

export default DashboardPage;