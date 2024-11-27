import { createContext, useEffect, useState } from "react";
import { useLocalStorage } from "../hooks/useLocalStorage";

export const AuthContext = createContext({
  user: null,
  setUser: () => {},
});

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const { getItem } = useLocalStorage();

  // Load user from localStorage on initial render
  useEffect(() => {
    const storedUser = getItem("user");
    if (storedUser) {
      setUser(storedUser);
    }
  }, []); // This will only run once after the initial render

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
};
