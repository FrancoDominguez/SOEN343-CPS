import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import LoginButton from "../components/LoginButton";
import { NavLink } from "react-router-dom";
import { useState, useEffect } from "react";
import Cookies from "js-cookie";

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

function TopNavBar({ navigation, userNavigation }) {
  const [userDetails, setUserDetails] = useState(null);
  const [title, setTitle] = useState("Internal Tools");
  const [loading, setLoading] = useState(true);

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
          <img className="h-8 w-8" src="/logos/icon.png" />
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
          <div className="ml-4 flex items-center md:ml-6">
            <Menu as="div" className="relative ml-3">
              {loading ? (
                <div>
                  <img
                    className="h-8 w-8 rounded-full bg-slate-500 p-[1px]"
                    src="/logos/user.png"
                  />
                </div>
              ) : userDetails ? (
                <div>
                  <MenuButton
                    className="flex max-w-xs items-center rounded-full bg-gray-800 
               focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 
              focus:ring-offset-gray-800"
                  >
                    <img
                      className="h-8 w-8 rounded-full bg-slate-500 p-[1px]"
                      src={userDetails.picture || "/logos/user.png"}
                    />
                  </MenuButton>
                  <MenuItems className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                    {userNavigation.map((item) => (
                      <MenuItem key={item.name}>
                        {({ focus }) => (
                          <a
                            href={item.href}
                            onClick={item.onClick}
                            className={classNames(
                              focus ? "bg-gray-100" : "",
                              "block px-4 py-2 text-sm text-gray-700"
                            )}
                          >
                            {item.name}
                          </a>
                        )}
                      </MenuItem>
                    ))}
                  </MenuItems>
                </div>
              ) : (
                <LoginButton />
              )}
            </Menu>
          </div>
        </div>
      </div>
    </nav>
  );
}

export default TopNavBar;
