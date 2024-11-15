import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { NavLink, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Cookies from "js-cookie";

import LoginButton from "../components/LoginButton";
import { useUser } from "../../hooks/useUser";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

function TopNavBar({ navigation, userNavigation }) {
  const [userDetails, setUserDetails] = useState(null);
  const [title, setTitle] = useState("Internal Tools");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { user } = useUser();

  const getUserDetails = async (accessToken) => {
    const response = await fetch(
      `https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=${accessToken}`
    );
    const data = await response.json();
    setUserDetails(data);
    setLoading(false);
  };

  useEffect(() => {
    const accessToken = Cookies.get("access_token");
    if (accessToken !== undefined) {
      getUserDetails(accessToken);
    } else {
      setLoading(false);
    }
  }, []);

  function handleNav(title) {
    setTitle(title);
  }

  return (
    <nav className="bg-slate-900">
      <div className="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
        <div className="flex h-14 items-center justify-between">
          <img
            className="h-8 w-8 cursor-pointer"
            src="/logos/icon.png"
            onClick={() => navigate("/")}
          />
          {!!user && (
            <div className="ml-10 flex items-baseline space-x-4">
              {navigation.map((item) => (
                <NavLink
                  onClick={() => handleNav(item.name)}
                  key={item.name}
                  to={item.href}
                  className={({ isActive }) =>
                    classNames(
                      isActive
                        ? "bg-gray-700 text-white"
                        : "text-gray-300 hover:bg-gray-700 hover:text-white",
                      "rounded-md px-3 py-2 text-sm font-medium"
                    )
                  }
                >
                  {item.name}
                </NavLink>
              ))}
            </div>
          )}

          <div className="ml-4 flex items-center md:ml-6">
            <LoginButton />
          </div>
        </div>
      </div>
    </nav>
  );
}

export default TopNavBar;
