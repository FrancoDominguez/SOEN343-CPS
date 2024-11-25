import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

const CheckoutForm = () => {
  const navigate = useNavigate();
  const { id } = useParams(); // Extract the quotation ID from the route parameters

  const [amount, setAmount] = useState(500);
  const [paymentMethod, setPaymentMethod] = useState("creditCard");
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

  const [errors, setErrors] = useState({});

  useEffect(() => {
    const fetchQuotation = async () => {
      try {
        if (!id || isNaN(Number(id))) { // Validate that the ID is a number
          console.error("Quotation ID is missing or invalid.");
          return;
        }
  
        const response = await fetch(`http://localhost:8080/api/payment/quotation/${id}`);
        if (!response.ok) {
          throw new Error("Failed to fetch quotation.");
        }
  
        const data = await response.json();
        setAmount(parseFloat(data.amount) * 100); // Convert dollars to cents
      } catch (err) {
        console.error("Failed to fetch quotation:", err);
      }
    };
  
    fetchQuotation();
  }, [id]);

  const validateForm = () => {
    const newErrors = {};

    if (paymentMethod === "creditCard") {
      if (!/^\d{16}$/.test(cardDetails.cardNumber)) {
        newErrors.cardNumber = "Card number must be 16 digits.";
      }

      if (!/^\d{2}\/\d{2}$/.test(cardDetails.expirationDate)) {
        newErrors.expirationDate = "Expiration date must be in MM/YY format.";
      } else {
        const [month, year] = cardDetails.expirationDate.split("/").map(Number);
        const now = new Date();
        const currentMonth = now.getMonth() + 1;
        const currentYear = now.getFullYear() % 100;

        if (year < currentYear || (year === currentYear && month < currentMonth)) {
          newErrors.expirationDate = "Expiration date cannot be in the past.";
        }
      }

      if (!/^\d{3}$/.test(cardDetails.cvc)) {
        newErrors.cvc = "CVC must be 3 digits.";
      }
    }

    if (paymentMethod === "paypal") {
      if (
        !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
          paypalCredentials.username
        )
      ) {
        newErrors.username = "Please enter a valid email address.";
      }

      if (!paypalCredentials.password) {
        newErrors.password = "Password cannot be empty.";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(true);
    try {
      const requestBody = { method: paymentMethod, quotationPrice: amount / 100 };

      if (paymentMethod === "creditCard") {
        requestBody.cardDetails = cardDetails;
      } else if (paymentMethod === "paypal") {
        requestBody.paypalCredentials = paypalCredentials;
      }

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

  return (
    <div className="max-w-xl mx-auto p-8 bg-gray-100 rounded shadow min-h-[400px]">
      <form onSubmit={handleSubmit}>
        {/* Header and Logo Section */}
        <div className="mb-4 flex items-center justify-between">
          <h1 className="text-2xl font-bold">Make a Payment</h1>
          <div className="flex-shrink-0">
            <img
              src={paymentMethod === "creditCard" ? "/StripeLogo4.png" : "/PayPalLogo4.png"}
              alt={paymentMethod === "creditCard" ? "Stripe Logo" : "PayPal Logo"}
              className="w-80 h-auto"
            />
          </div>
        </div>

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
            value={(amount / 100).toFixed(2)}
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
              {errors.cardNumber && (
                <p className="text-red-600">{errors.cardNumber}</p>
              )}
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
              {errors.expirationDate && (
                <p className="text-red-600">{errors.expirationDate}</p>
              )}
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
              {errors.cvc && <p className="text-red-600">{errors.cvc}</p>}
            </div>
          </>
        )}

        {paymentMethod === "paypal" && (
          <>
            <div className="mb-4">
              <label htmlFor="username" className="block font-medium text-lg mb-2">
                PayPal Email:
              </label>
              <input
                id="username"
                name="username"
                type="text"
                value={paypalCredentials.username}
                onChange={handlePaypalChange}
                className="w-full h-10 p-2 border rounded text-lg"
              />
              {errors.username && (
                <p className="text-red-600">{errors.username}</p>
              )}
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
              {errors.password && (
                <p className="text-red-600">{errors.password}</p>
              )}
            </div>
          </>
        )}

        <button
          type="submit"
          className="w-full p-3 bg-blue-500 text-white rounded text-lg"
          disabled={loading}
        >
          {loading ? "Processing..." : `Pay with ${paymentMethod === "creditCard"? "Credit Card": "PayPal"}`}
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
