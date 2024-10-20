import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ApiContext } from "./components/ApiProvider";

function App() {
  const GoogleApiKey = useContext(ApiContext);

  useEffect(() => {
    console.log("api key", GoogleApiKey);
  }, [GoogleApiKey]);

  return (
    <Router>
      <div className="flex justify-center items-center min-h-screen">
        <Routes>
          <Route path="/" element={<div>LANDING PAGE</div>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
