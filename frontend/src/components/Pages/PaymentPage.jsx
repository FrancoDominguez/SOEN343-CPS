import React, { useState } from "react";

function PaymentPage() {
  const [method, setMethod] = useState("stripe");
  const [quotationPrice] = useState(150.75); // Example quotation price
  const [cardDetails, setCardDetails] = useState({
    cardNumber: "",
    cvv: "",
    expirationDate: "",
  });
  const [paypalCredentials, setPaypalCredentials] = useState({
    email: "",
    password: "",
  });

  const handlePayment = async () => {
    const requestBody = {
      method,
      quotationPrice, // Pass the quotation price from backend or input
    };

    if (method === "stripe") {
      requestBody.cardDetails = cardDetails;
    } else if (method === "paypal") {
      requestBody.paypalCredentials = paypalCredentials;
    }

    const response = await fetch("http://localhost:8080/api/payment/process", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody),
    });

    const data = await response.json();
    alert(`Payment ${data.status}! Processed via ${data.method} for $${data.amount}`);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (method === "stripe") {
      setCardDetails((prev) => ({ ...prev, [name]: value }));
    } else if (method === "paypal") {
      setPaypalCredentials((prev) => ({ ...prev, [name]: value }));
    }
  };

  return (
    <div>
      <h1>Payment Page</h1>
      <select onChange={(e) => setMethod(e.target.value)} value={method}>
        <option value="stripe">Stripe</option>
        <option value="paypal">PayPal</option>
      </select>

      {method === "stripe" && (
        <div>
          <h3>Credit Card Details</h3>
          <input
            type="text"
            name="cardNumber"
            placeholder="Card Number"
            value={cardDetails.cardNumber}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="cvv"
            placeholder="CVV"
            value={cardDetails.cvv}
            onChange={handleInputChange}
          />
          <input
            type="text"
            name="expirationDate"
            placeholder="Expiration Date (MM/YY)"
            value={cardDetails.expirationDate}
            onChange={handleInputChange}
          />
        </div>
      )}

      {method === "paypal" && (
        <div>
          <h3>PayPal Credentials</h3>
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={paypalCredentials.email}
            onChange={handleInputChange}
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={paypalCredentials.password}
            onChange={handleInputChange}
          />
        </div>
      )}

      <button onClick={handlePayment}>Pay</button>
    </div>
  );
}

export default PaymentPage;
