import { useEffect } from "react";
import { useLocalStorage } from "./useLocalStorage";
import { useUser } from "./useUser";

export const useAuth = () => {
  const { user, addUser, removeUser, setUser } = useUser();
  const { getItem, setItem } = useLocalStorage();

  useEffect(() => {
    const storedUser = getItem("user");

    if (storedUser && !user) {
      // Ensure the storedUser is a valid object
      if (typeof storedUser === "object" && storedUser !== null) {
        addUser(storedUser);
      }
    }
  }, [addUser, getItem, user]); // Add `user` as a dependency to avoid infinite loop

  const login = (user, token) => {
    addUser(user); // Set user in context
    setItem("jwt", token); // Save token in localStorage
  };

  const logout = () => {
    removeUser(); // Remove user from context and localStorage
  };

  return { user, login, logout };
};
