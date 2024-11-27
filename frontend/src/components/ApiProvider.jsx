import { createContext } from "react";

const ApiContext = createContext();
const GoogleApiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

function ApiProvider({ children }) {
  return (
    <ApiContext.Provider value={{ GoogleApiKey }}>
      {children}
    </ApiContext.Provider>
  );
}

export { ApiProvider, ApiContext };
