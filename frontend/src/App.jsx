import { useContext, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { EnvContext } from "./components/EnvProvider";
import DashboardPage from "./components/Pages/DashboardPage";
import DeliverPage from "./components/Pages/DeliverPage";
import ReceivingPage from "./components/Pages/ReceivingPage";
import TopNavBar from "./components/TopNavbar";
import LoginPage from "./components/Pages/LoginPage";
import { AuthProvider } from "../context/AuthContext";
import { useAuth } from "../hooks/useAuth";

const navigation = [
  { name: "Dashboard", href: "/", current: true },
  { name: "Deliver", href: "/deliver", current: false },
  { name: "Tracking", href: "/tracking", current: false },
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

  const { user, setUser } = useAuth();

  useEffect(() => {
    console.log("api key", GoogleApiKey);
  }, [GoogleApiKey]);

  return (
    <AuthProvider value={{ user, setUser }}>
      <div className="h-screen flex flex-col">
        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
          transition:Bounce
        />

        <div className="sticky top-0 z-50">
          <TopNavBar navigation={navigation} userNavigation={userNavigation} />
        </div>

        <div className="h-full overflow-y-auto">
          <div className="flex justify-center mx-auto max-w-7xl py-6 sm:px-6 lg:px-8 h-full">
            <Routes>
              <Route path="/" element={<DashboardPage />} />
              <Route path="/deliver" element={<DeliverPage />} />
              <Route path="/tracking" element={<ReceivingPage />} />
              <Route path="/login" element={<LoginPage />} />
            </Routes>
          </div>
        </div>
      </div>
    </AuthProvider>
  );
}

export default App;
