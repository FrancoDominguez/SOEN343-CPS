import { useContext } from "react";
import { useLocalStorage } from "./useLocalStorage";
import { AuthContext } from "../context/AuthContext";

export const useUser = () => {
  const { user, setUser } = useContext(AuthContext);
  const { setItem, removeItem } = useLocalStorage();

  const addUser = (user) => {
    setUser(user); // Set user in context
    setItem("user", user); // Store user in localStorage
  };

  const removeUser = () => {
    setUser(null); // Clear user from context
    removeItem("user"); // Remove user from localStorage
    removeItem("jwt"); // Optionally remove token from localStorage
  };

  return { user, addUser, removeUser, setUser };
};
