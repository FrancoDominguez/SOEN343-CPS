import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import { EnvContext } from "./components/EnvProvider";
import DashboardPage from "./components/Pages/DashboardPage";
import DeliverPage from "./components/Pages/DeliverPage";
import ReceivingPage from "./components/Pages/ReceivingPage";
import TopNavBar from "./components/TopNavbar";
import LoginPage from "./components/Pages/LoginPage";
import PaymentPage from "./components/Pages/PaymentPage"; // Import the new PaymentPage component

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
      Cookies.remove("access_token");
    },
  },
];

function App() {
  const GoogleApiKey = useContext(EnvContext);
  const navigate = useNavigate();

  useEffect(() => {
    console.log("api key", GoogleApiKey);
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
            <Route path="/tracking" element={<ReceivingPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/payment" element={<PaymentPage />} /> {/* Add payment route */}
          </Routes>
        </div>
      </div>
    </div>
  );
}

export default App;
