import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ApiProvider } from "./components/ApiProvider";
import App from "./App.jsx";
import "./styles.css";

createRoot(document.getElementById("root")).render(
  <ApiProvider>
    <StrictMode>
      <App />
    </StrictMode>
  </ApiProvider>
);
