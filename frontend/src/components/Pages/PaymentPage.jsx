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

const CheckoutForm = () => {
  const stripe = useStripe();
  const elements = useElements();
  const navigate = useNavigate();

  const [amount, setAmount] = useState(3000); // Default amount: 30 CAD (in cents)
  const [paymentMethod, setPaymentMethod] = useState("stripe"); // Default method: Stripe
  const [paymentStatus, setPaymentStatus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [cardErrors, setCardErrors] = useState({});
  const [paypalCredentials, setPaypalCredentials] = useState({
    username: "",
    password: "",
  });

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

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (paymentMethod === "paypal") {
      // Placeholder logic for PayPal
      alert(`PayPal payment with:\nUsername: ${paypalCredentials.username}\nPassword: ${paypalCredentials.password}`);
      return;
    }

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

      const { error, paymentIntent } = await stripe.confirmCardPayment(clientSecret, {
        payment_method: {
          card: elements.getElement(CardNumberElement),
        },
      });

      if (error) {
        console.error("Stripe Payment Error:", error.message);
        setPaymentStatus(error.message);
      } else {
        console.log("PaymentIntent:", paymentIntent);
        setPaymentStatus(`Payment successful: ${paymentIntent.status}`);

        if (paymentIntent.status === "succeeded") {
          alert("Payment Successful");
          navigate("/");
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
          src={paymentMethod === "stripe" ? "/StripeLogo4.png" : "/PayPalLogo4.png"}
          alt={paymentMethod === "stripe" ? "Stripe Logo" : "PayPal Logo"}
          className="w-40 h-auto"
        />
      </div>

      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="paymentMethod" className="block font-medium text-lg mb-2">
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

        <div className="mb-4">
          <label htmlFor="amount" className="block font-medium text-lg mb-2">
            Amount (CAD):
          </label>
          <input
            id="amount"
            type="text"
            value={(amount / 100).toFixed(2)} // Display amount in dollars
            className="w-full h-10 p-2 border rounded text-lg"
            readOnly
          />
        </div>

        {paymentMethod === "stripe" && (
          <>
            <div className="mb-4">
              <label className="block font-medium text-lg mb-2">Card Number:</label>
              <div
                className={`p-2 border rounded h-10 ${
                  cardErrors.number ? "border-red-500" : ""
                }`}
              >
                <CardNumberElement onChange={(e) => handleCardChange(e, "number")} />
              </div>
              {cardErrors.number && (
                <p className="text-red-500 text-sm">{cardErrors.number}</p>
              )}
            </div>

            <div className="mb-4">
              <label className="block font-medium text-lg mb-2">Expiration Date:</label>
              <div
                className={`p-2 border rounded h-10 ${
                  cardErrors.expiry ? "border-red-500" : ""
                }`}
              >
                <CardExpiryElement onChange={(e) => handleCardChange(e, "expiry")} />
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
          </>
        )}

        {paymentMethod === "paypal" && (
          <>
            <div className="mb-4">
              <label htmlFor="username" className="block font-medium text-lg mb-2">
                PayPal Username:
              </label>
              <input
                id="username"
                name="username"
                type="text"
                value={paypalCredentials.username}
                onChange={handlePaypalChange}
                className="w-full h-10 p-2 border rounded text-lg"
              />
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
                onChange={handlePaypalChange}
                className="w-full h-10 p-2 border rounded text-lg"
              />
            </div>
          </>
        )}

        <button
          type="submit"
          className="w-full p-3 bg-blue-500 text-white rounded text-lg"
          disabled={loading}
        >
          {loading ? "Processing..." : `Pay with ${paymentMethod === "stripe" ? "Stripe" : "PayPal"}`}
        </button>
      </form>

      {paymentStatus && (
        <div className="mt-4 p-3 bg-gray-200 rounded text-lg">
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