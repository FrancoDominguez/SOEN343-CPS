import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { ApiContext } from "./components/ApiProvider";
import OrderForm from "./components/OrderForm";

function App() {
  const GoogleApiKey = useContext(ApiContext);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("api key", GoogleApiKey);
  }, [GoogleApiKey]);

  function handleButtonClick() {
    navigate("/order");
  }

  return (
    <div className="flex justify-center items-center">
      <Routes>
        <Route
          path="/"
          element={
            <div>
              <div>Landing page</div>
              <button onClick={handleButtonClick}>clickme!</button>
            </div>
          }
        />
        <Route path="/order" element={<OrderForm />} />
      </Routes>
    </div>
  );
}

export default App;
