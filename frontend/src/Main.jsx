import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { EnvProvider } from "./components/EnvProvider";
import { BrowserRouter as Router } from "react-router-dom";
import App from "./App.jsx";
import "./styles.css";

createRoot(document.getElementById("root")).render(
  <Router>
    <EnvProvider>
      <StrictMode>
        <App />
      </StrictMode>
    </EnvProvider>
  </Router>
);
