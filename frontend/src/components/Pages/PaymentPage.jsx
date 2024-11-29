import React, { useState } from "react";
import { Elements, useStripe, useElements, CardNumberElement, CardExpiryElement, CardCvcElement } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";

// Get the Stripe Publishable Key
const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

// Initialize Stripe context
const PaymentPage = ({ amount, contractId, onPaymentSuccess }) => (
  <Elements stripe={stripePromise}>
    <CheckoutForm amount={amount} contractId={contractId} onPaymentSuccess={onPaymentSuccess} />
  </Elements>
);

const CheckoutForm = ({ amount, contractId, onPaymentSuccess }) => {
  const stripe = useStripe();
  const elements = useElements();

  const [paymentAmount, setPaymentAmount] = useState(amount); // Initialize with dynamic amount
  const [paymentMethod, setPaymentMethod] = useState("stripe"); // Default method: Stripe
  const [paymentStatus, setPaymentStatus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [cardErrors, setCardErrors] = useState({});
  const [paypalCredentials, setPaypalCredentials] = useState({ username: "", password: "" });
  const [paypalErrors, setPaypalErrors] = useState({});

  const handleCardChange = (event, field) => {
    setCardErrors((prev) => ({
      ...prev,
      [field]: event.error ? event.error.message : "",
    }));
  };

  // Handle Stripe payment submission
  const handleStripeSubmit = async (e) => {
    e.preventDefault();
    if (!stripe || !elements) {
      setPaymentStatus("Stripe is not loaded yet.");
      return;
    }
    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/payment/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount: paymentAmount * 100, method: "stripe" }), // Convert amount to cents
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Failed to create PaymentIntent");
      }

      const { clientSecret } = await response.json();
      const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
        payment_method: { card: elements.getElement(CardNumberElement) },
      });

      if (error) {
        throw new Error(error.message);
      }

      setPaymentStatus(`Payment successful: ${paymentIntent.status}`);
      if (paymentIntent.status === "succeeded") {
        onPaymentSuccess();
      }
    } catch (err) {
      setPaymentStatus(`Error: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  // Handle PayPal payment submission
  const handlePaypalSubmit = async (e) => {
    e.preventDefault();
  
    // Validate the PayPal credentials
    if (!paypalCredentials.username || !paypalCredentials.password) {
      setPaypalErrors({
        username: !paypalCredentials.username ? "Email is required" : "",
        password: !paypalCredentials.password ? "Password is required" : "",
      });
      return;
    }
    setLoading(true);
    try {
      // Send the PayPal payment request to the backend
      const response = await fetch("http://localhost:8080/api/payment/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          amount: paymentAmount * 100, // Convert to cents
          method: "paypal",
        }),
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Failed to process PayPal payment");
      }
  
      const { message } = await response.json();
  
      setPaymentStatus(message); // Show the mock PayPal response message
      alert(message); // Display success/failure message from the backend
      onPaymentSuccess(); // Notify parent component about payment success
    } catch (err) {
      setPaymentStatus(`Error: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };
  

  return (
    <div className="max-w-xl mx-auto p-8 bg-gray-100 rounded shadow">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold">Make a Payment</h2>
        <img
          src={paymentMethod === "stripe" ? "/StripeLogo4.png" : "/PayPalLogo4.png"}
          alt={paymentMethod === "stripe" ? "Stripe Logo" : "PayPal Logo"}
          className="w-40 h-auto"
        />
      </div>

      <div className="mb-4">
        <label htmlFor="paymentMethod" className="block font-medium text-lg mb-2">
          Select Payment Method
        </label>
        <div className="flex items-center space-x-4">
          <label>
            <input
              type="radio"
              name="paymentMethod"
              value="stripe"
              checked={paymentMethod === "stripe"}
              onChange={() => setPaymentMethod("stripe")}
            />{" "}
            Stripe
          </label>
          <label>
            <input
              type="radio"
              name="paymentMethod"
              value="paypal"
              checked={paymentMethod === "paypal"}
              onChange={() => setPaymentMethod("paypal")}
            />{" "}
            PayPal
          </label>
        </div>
      </div>

      {paymentMethod === "stripe" && (
        <form onSubmit={handleStripeSubmit}>
          <p className="text-lg mb-4">Amount to Pay: ${paymentAmount.toFixed(2)}</p>
          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">Card Number:</label>
            <div
              className={`p-2 border rounded h-10 ${cardErrors.number ? "border-red-500" : ""}`}
            >
              <CardNumberElement onChange={(e) => handleCardChange(e, "number")} />
            </div>
            {cardErrors.number && <p className="text-red-500 text-sm">{cardErrors.number}</p>}
          </div>
          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">Expiration Date:</label>
            <div
              className={`p-2 border rounded h-10 ${cardErrors.expiry ? "border-red-500" : ""}`}
            >
              <CardExpiryElement onChange={(e) => handleCardChange(e, "expiry")} />
            </div>
            {cardErrors.expiry && <p className="text-red-500 text-sm">{cardErrors.expiry}</p>}
          </div>
          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">CVC:</label>
            <div className={`p-2 border rounded h-10 ${cardErrors.cvc ? "border-red-500" : ""}`}>
              <CardCvcElement onChange={(e) => handleCardChange(e, "cvc")} />
            </div>
            {cardErrors.cvc && <p className="text-red-500 text-sm">{cardErrors.cvc}</p>}
          </div>
          <button type="submit" className="w-full p-3 bg-blue-500 text-white rounded text-lg" disabled={loading}>
            {loading ? "Processing..." : `Pay $${paymentAmount.toFixed(2)} with Stripe`}
          </button>
        </form>
      )}

      {paymentMethod === "paypal" && (
        <form onSubmit={handlePaypalSubmit}>
          <p className="text-lg mb-4">Amount to Pay: ${paymentAmount.toFixed(2)}</p>
          <div className="mb-4">
            <label htmlFor="username" className="block font-medium text-lg mb-2">
              PayPal Username (Email):
            </label>
            <input
              id="username"
              name="username"
              type="text"
              value={paypalCredentials.username}
              onChange={(e) => setPaypalCredentials({ ...paypalCredentials, username: e.target.value })}
              className={`w-full h-10 p-2 border rounded text-lg ${paypalErrors.username ? "border-red-500" : ""}`}
            />
            {paypalErrors.username && <p className="text-red-500 text-sm">{paypalErrors.username}</p>}
          </div>
          <div className="mb-4">
            <label htmlFor="password" className="block font-medium text-lg mb-2">
              PayPal Password:
            </label>
            <input
              id="password"
              name="password"
              type="password"
              value={paypalCredentials.password}
              onChange={(e) => setPaypalCredentials({ ...paypalCredentials, password: e.target.value })}
              className={`w-full h-10 p-2 border rounded text-lg ${paypalErrors.password ? "border-red-500" : ""}`}
            />
            {paypalErrors.password && <p className="text-red-500 text-sm">{paypalErrors.password}</p>}
          </div>
          <button type="submit" className="w-full p-3 bg-yellow-500 text-white rounded text-lg" disabled={loading}>
            {loading ? "Processing..." : `Pay $${paymentAmount.toFixed(2)} with PayPal`}
          </button>
        </form>
      )}

      {paymentStatus && (
        <div className="mt-4 p-3 bg-gray-200 rounded text-lg">
          <p>{paymentStatus}</p>
        </div>
      )}
    </div>
  );
};

export default PaymentPage;
