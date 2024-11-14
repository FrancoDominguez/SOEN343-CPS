import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate, useLocation } from "react-router-dom";
import { EnvContext } from "./components/EnvProvider";
import DashboardPage from "./components/Pages/DashboardPage";
import DeliverPage from "./components/Pages/DeliverPage";
import ReceivingPage from "./components/Pages/ReceivingPage";
import TopNavBar from "./components/TopNavbar";
import LoginPage from "./components/Pages/LoginPage";
import TrackingInfo from "./components/Pages/TrackingInfo";
import HomePage from "./components/Pages/HomePage";

const navigation = [
  { name: "Home", href: "/", current: true },
  { name: "Dashboard", href: "/dashboard", current: false },
  { name: "Deliver", href: "/deliver", current: false },
];

function App() {
  const GoogleApiKey = useContext(EnvContext);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    console.log("api key", GoogleApiKey);
  }, [GoogleApiKey]);

  const isHomePage = location.pathname === "/";

  return (
      <div
          className={`h-screen flex flex-col ${isHomePage ? "overflow-hidden" : ""}`}
          style={{
            backgroundImage: isHomePage ? "url('/logos/Pic.png')" : "none",
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
      >
        <div className="sticky top-0 z-50">
          <TopNavBar navigation={navigation} />
        </div>

        <div className={`h-full ${isHomePage ? "" : "overflow-y-auto"}`}>
          <div className={`${isHomePage ? "flex items-start justify-start p-5" : "flex justify-center mx-auto max-w-7xl py-6 sm:px-6 lg:px-8"}`}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/dashboard" element={<DashboardPage />} />
              <Route path="/deliver" element={<DeliverPage />} />
              <Route path="/tracking/:trackingNumber" element={<TrackingInfo />} />
              <Route path="/login" element={<LoginPage />} />
            </Routes>
          </div>
        </div>
      </div>
  );
}

export default App;