import React, { useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import {
  Elements,
  useStripe,
  useElements,
  CardNumberElement,
  CardExpiryElement,
  CardCvcElement,
} from "@stripe/react-stripe-js";

// Load Stripe with your Publishable Key
const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();

  const [amount, setAmount] = useState(5000); // Default: 50 CAD (in cents)
  const [paymentStatus, setPaymentStatus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [cardErrors, setCardErrors] = useState({}); // Stores validation errors

  const handleCardChange = (event, field) => {
    setCardErrors((prev) => ({
      ...prev,
      [field]: event.error ? event.error.message : "",
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!stripe || !elements) return;

    if (amount <= 0) {
      setPaymentStatus("Amount must be greater than 0.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch("/api/payment/create", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ amount }), // Send the amount in cents to the backend
      });

      const { clientSecret } = await response.json();

      const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: elements.getElement(CardNumberElement),
        },
      });

      if (error) {
        setPaymentStatus(error.message);
      } else {
        setPaymentStatus(`Payment successful: ${paymentIntent.status}`);
      }
    } catch (err) {
      setPaymentStatus("An error occurred during payment.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto p-6 bg-gray-100 rounded shadow">
      <h2 className="text-lg font-bold mb-4">Make a Payment</h2>

      <form onSubmit={handleSubmit}>
        {/* Payment Amount Field */}
        <div className="mb-4">
          <label htmlFor="amount" className="block font-medium mb-2">
            Amount (CAD):
          </label>
          <input
            id="amount"
            type="number"
            value={amount / 100} // Convert cents to CAD for display
            onChange={(e) => {
              const value = e.target.value * 100; // Convert CAD to cents
              setAmount(value >= 0 ? value : 0); // Prevent negative values
            }}
            className="w-full p-2 border rounded"
            placeholder="Enter amount"
            required
          />
          {amount <= 0 && (
            <p className="text-red-500 text-sm">Amount must be greater than 0.</p>
          )}
        </div>

        {/* Card Number Field */}
        <div className="mb-4">
          <label className="block font-medium mb-2">Card Number:</label>
          <div
            className={`p-2 border rounded ${
              cardErrors.number ? "border-red-500" : ""
            }`}
          >
            <CardNumberElement
              onChange={(e) => handleCardChange(e, "number")}
            />
          </div>
          {cardErrors.number && (
            <p className="text-red-500 text-sm">{cardErrors.number}</p>
          )}
        </div>

        {/* Expiration Date Field */}
        <div className="mb-4">
          <label className="block font-medium mb-2">Expiration Date:</label>
          <div
            className={`p-2 border rounded ${
              cardErrors.expiry ? "border-red-500" : ""
            }`}
          >
            <CardExpiryElement
              onChange={(e) => handleCardChange(e, "expiry")}
            />
          </div>
          {cardErrors.expiry && (
            <p className="text-red-500 text-sm">{cardErrors.expiry}</p>
          )}
        </div>

        {/* CVC Field */}
        <div className="mb-4">
          <label className="block font-medium mb-2">CVC:</label>
          <div
            className={`p-2 border rounded ${
              cardErrors.cvc ? "border-red-500" : ""
            }`}
          >
            <CardCvcElement onChange={(e) => handleCardChange(e, "cvc")} />
          </div>
          {cardErrors.cvc && (
            <p className="text-red-500 text-sm">{cardErrors.cvc}</p>
          )}
        </div>

        {/* Submit Button */}
        <button
          type="submit"
          className="w-full p-2 bg-blue-500 text-white rounded"
          disabled={!stripe || loading}
        >
          {loading ? "Processing..." : "Pay Now"}
        </button>
      </form>

      {/* Payment Status Message */}
      {paymentStatus && (
        <div className="mt-4 p-2 bg-gray-200 rounded">
          <p>{paymentStatus}</p>
        </div>
      )}
    </div>
  );
};

const PaymentPage = () => (
  <Elements stripe={stripePromise}>
    <CheckoutForm />
  </Elements>
);

export default PaymentPage;
