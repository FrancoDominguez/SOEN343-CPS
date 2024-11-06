import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ApiProvider } from "./components/ApiProvider";
import { BrowserRouter as Router } from "react-router-dom";
import App from "./App.jsx";
import "./styles.css";

createRoot(document.getElementById("root")).render(
  <Router>
    <ApiProvider>
      <StrictMode>
        <App />
      </StrictMode>
    </ApiProvider>
  </Router>
);
