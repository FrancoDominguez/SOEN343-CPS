import { createContext } from "react";

const EnvContext = createContext();
const GoogleApiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

function EnvProvider({ children }) {
  return (
    <EnvContext.Provider value={{ GoogleApiKey }}>
      {children}
    </EnvContext.Provider>
  );
}

export { EnvProvider, EnvContext };

