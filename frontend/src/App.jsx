import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { EnvContext } from "./components/EnvProvider";
import DashboardPage from "./components/Pages/DashboardPage";
import DeliverPage from "./components/Pages/DeliverPage";
import ReceivingPage from "./components/Pages/ReceivingPage";
import TopNavBar from "./components/TopNavbar";
import LoginPage from "./components/Pages/LoginPage";
import PaymentPage from "./components/Pages/PaymentPage"; // Import the PaymentPage component
import TrackingInfo from "./components/Pages/TrackingInfo";

const navigation = [
  { name: "Dashboard", href: "/", current: true },
  { name: "Deliver", href: "/deliver", current: false },
  { name: "Tracking", href: "/tracking", current: false },
  { name: "Payment", href: "/payment", current: false }, // Add Payment to navigation
];

const userNavigation = [
  {
    name: "Sign out",
    href: "/",
    onClick: () => {
      // Handle sign out logic
      Cookies.remove("access_token");
    },
  },
];

function App() {
  const GoogleApiKey = useContext(EnvContext); // Access environment variable
  const navigate = useNavigate(); // For navigation programmatically

  useEffect(() => {
    console.log("Google API Key:", GoogleApiKey);
  }, [GoogleApiKey]);

  return (
    <div className="h-screen flex flex-col">
      <div className="sticky top-0 z-50">
        <TopNavBar navigation={navigation} userNavigation={userNavigation} />
      </div>

      <div className="h-full overflow-y-auto">
        <div className="flex justify-center mx-auto max-w-7xl py-6 sm:px-6 lg:px-8">
          <Routes>
            <Route path="/" element={<DashboardPage />} />
            <Route path="/deliver" element={<DeliverPage />} />
            <Route path="/tracking" element={<TrackingInfo />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/payment" element={<PaymentPage />} /> {/* Add Payment route */}
          </Routes>
        </div>
      </div>
    </div>
  );
}

export default App;
