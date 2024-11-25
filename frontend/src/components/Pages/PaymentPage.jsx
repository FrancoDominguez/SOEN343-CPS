import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const CheckoutForm = () => {
  const navigate = useNavigate();

  const [amount] = useState(15075); // Example amount: 150.75 CAD (in cents)
  const [paymentMethod, setPaymentMethod] = useState("creditCard"); // Default method
  const [loading, setLoading] = useState(false);
  const [paymentStatus, setPaymentStatus] = useState(null);

  const [cardDetails, setCardDetails] = useState({
    cardNumber: "",
    expirationDate: "",
    cvc: "",
  });

  const [paypalCredentials, setPaypalCredentials] = useState({
    username: "",
    password: "",
  });

  const handleCardDetailsChange = (e) => {
    const { name, value } = e.target;
    setCardDetails((prev) => ({
      ...prev,
      [name]: value,
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
    setLoading(true);

    try {
      const requestBody = { method: paymentMethod, quotationPrice: amount / 100 };

      if (paymentMethod === "creditCard") {
        requestBody.cardDetails = cardDetails;
      } else if (paymentMethod === "paypal") {
        requestBody.paypalCredentials = paypalCredentials;
      }

      console.log("Request Payload:", requestBody);

      const response = await fetch("http://localhost:8080/api/payment/process", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(requestBody),
      });

      if (!response.ok) {
        throw new Error("Failed to process payment.");
      }

      const result = await response.json();
      setPaymentStatus(`Payment successful via ${result.method} for $${result.amount}`);
      alert(`Payment success! Processed via ${result.method} for $${result.amount}`);
      navigate("/");
    } catch (err) {
      console.error(err.message);
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
          src={paymentMethod === "creditCard" ? "/StripeLogo4.png" : "/PayPalLogo4.png"}
          alt={paymentMethod === "creditCard" ? "Credit Card Logo" : "PayPal Logo"}
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
                value="creditCard"
                checked={paymentMethod === "creditCard"}
                onChange={() => setPaymentMethod("creditCard")}
              />{" "}
              Credit Card
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

        {paymentMethod === "creditCard" && (
          <>
            <div className="mb-4">
              <label htmlFor="cardNumber" className="block font-medium text-lg mb-2">
                Card Number:
              </label>
              <input
                id="cardNumber"
                name="cardNumber"
                type="text"
                value={cardDetails.cardNumber}
                onChange={handleCardDetailsChange}
                className="w-full h-10 p-2 border rounded text-lg"
                placeholder="1234 5678 9012 3456"
              />
            </div>

            <div className="mb-4">
              <label htmlFor="expirationDate" className="block font-medium text-lg mb-2">
                Expiration Date:
              </label>
              <input
                id="expirationDate"
                name="expirationDate"
                type="text"
                value={cardDetails.expirationDate}
                onChange={handleCardDetailsChange}
                className="w-full h-10 p-2 border rounded text-lg"
                placeholder="MM/YY"
              />
            </div>

            <div className="mb-4">
              <label htmlFor="cvc" className="block font-medium text-lg mb-2">
                CVC:
              </label>
              <input
                id="cvc"
                name="cvc"
                type="text"
                value={cardDetails.cvc}
                onChange={handleCardDetailsChange}
                className="w-full h-10 p-2 border rounded text-lg"
                placeholder="123"
              />
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
          {loading ? "Processing..." : `Pay with ${paymentMethod === "creditCard" ? "Credit Card" : "PayPal"}`}
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

const PaymentPage = () => <CheckoutForm />;

export default PaymentPage;
