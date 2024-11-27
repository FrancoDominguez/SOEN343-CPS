import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loadStripe } from "@stripe/stripe-js";
import {
  Elements,
  useStripe,
  useElements,
  CardNumberElement,
  CardExpiryElement,
  CardCvcElement,
} from "@stripe/react-stripe-js";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);

const CheckoutForm = ({ onPaymentSuccess }) => {
  const stripe = useStripe();
  const elements = useElements();

  const [amount, setAmount] = useState(3000); // Default amount: 30 CAD (in cents)
  const [paymentMethod, setPaymentMethod] = useState("stripe"); // Default method: Stripe
  const [paymentStatus, setPaymentStatus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [cardErrors, setCardErrors] = useState({});
  const [paypalCredentials, setPaypalCredentials] = useState({
    username: "",
    password: "",
  });
  const [paypalErrors, setPaypalErrors] = useState({});

  const handleCardChange = (event, field) => {
    setCardErrors((prev) => ({
      ...prev,
      [field]: event.error ? event.error.message : "",
    }));
  };

  const handlePaypalChange = (e) => {
    const { name, value } = e.target;
    setPaypalCredentials((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const validatePaypal = () => {
    const errors = {};
    if (
      !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
        paypalCredentials.username
      )
    ) {
      errors.username = "Please enter a valid email address.";
    }

    if (!paypalCredentials.password) {
      errors.password = "Password cannot be empty.";
    }

    setPaypalErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handlePaypalSubmit = (e) => {
    e.preventDefault();

    if (!validatePaypal()) {
      return; // Stop if validation fails
    }

    // Simulated PayPal payment success
    alert(
      `PayPal payment processed with username: ${paypalCredentials.username}`
    );
    if (onPaymentSuccess) {
      onPaymentSuccess(); // Notify parent component
    }
  };

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
        body: JSON.stringify({ amount }), // Include the amount in the request body
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error("PaymentIntent creation error:", errorText);
        throw new Error("Failed to create PaymentIntent");
      }

      const { clientSecret } = await response.json();
      console.log("Client Secret:", clientSecret);

      const { error, paymentIntent } = await stripe.confirmCardPayment(
        clientSecret,
        {
          payment_method: {
            card: elements.getElement(CardNumberElement),
          },
        }
      );

      if (error) {
        console.error("Stripe Payment Error:", error.message);
        setPaymentStatus(error.message);
      } else {
        console.log("PaymentIntent:", paymentIntent);
        setPaymentStatus(`Payment successful: ${paymentIntent.status}`);

        if (paymentIntent.status === "succeeded") {
          alert("Stripe Payment Successful");
          if (onPaymentSuccess) {
            onPaymentSuccess(); // Notify parent component
          }
        }
      }
    } catch (err) {
      console.error("Error:", err.message);
      setPaymentStatus(`Error: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto p-8 bg-gray-100 rounded shadow min-h-[400px]">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-2xl font-bold">Make a Payment</h2>
        <img
          src={
            paymentMethod === "stripe" ? "/StripeLogo4.png" : "/PayPalLogo4.png"
          }
          alt={paymentMethod === "stripe" ? "Stripe Logo" : "PayPal Logo"}
          className="w-40 h-auto"
        />
      </div>

      <div className="mb-4">
        <label
          htmlFor="paymentMethod"
          className="block font-medium text-lg mb-2"
        >
          Select Payment Method:
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
          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">
              Card Number:
            </label>
            <div
              className={`p-2 border rounded h-10 ${
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

          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">
              Expiration Date:
            </label>
            <div
              className={`p-2 border rounded h-10 ${
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

          <div className="mb-4">
            <label className="block font-medium text-lg mb-2">CVC:</label>
            <div
              className={`p-2 border rounded h-10 ${
                cardErrors.cvc ? "border-red-500" : ""
              }`}
            >
              <CardCvcElement onChange={(e) => handleCardChange(e, "cvc")} />
            </div>
            {cardErrors.cvc && (
              <p className="text-red-500 text-sm">{cardErrors.cvc}</p>
            )}
          </div>

          <button
            type="submit"
            className="w-full p-3 bg-blue-500 text-white rounded text-lg"
            disabled={loading}
          >
            {loading ? "Processing..." : "Pay with Stripe"}
          </button>
        </form>
      )}

      {paymentMethod === "paypal" && (
        <form onSubmit={handlePaypalSubmit}>
          <div className="mb-4">
            <label
              htmlFor="username"
              className="block font-medium text-lg mb-2"
            >
              PayPal Username (Email):
            </label>
            <input
              id="username"
              name="username"
              type="text"
              value={paypalCredentials.username}
              onChange={handlePaypalChange}
              className={`w-full h-10 p-2 border rounded text-lg ${
                paypalErrors.username ? "border-red-500" : ""
              }`}
            />
            {paypalErrors.username && (
              <p className="text-red-500 text-sm">{paypalErrors.username}</p>
            )}
          </div>

          <div className="mb-4">
            <label
              htmlFor="password"
              className="block font-medium text-lg mb-2"
            >
              PayPal Password:
            </label>
            <input
              id="password"
              name="password"
              type="password"
              value={paypalCredentials.password}
              onChange={handlePaypalChange}
              className={`w-full h-10 p-2 border rounded text-lg ${
                paypalErrors.password ? "border-red-500" : ""
              }`}
            />
            {paypalErrors.password && (
              <p className="text-red-500 text-sm">{paypalErrors.password}</p>
            )}
          </div>

          <button
            type="submit"
            className="w-full p-3 bg-yellow-500 text-white rounded text-lg"
          >
            Pay with PayPal
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

const PaymentPage = ({ onPaymentSuccess }) => (
  <Elements stripe={stripePromise}>
    <CheckoutForm onPaymentSuccess={onPaymentSuccess} />
  </Elements>
);

export default PaymentPage;
