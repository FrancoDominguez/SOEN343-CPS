export const useLocalStorage = () => {
  // Helper function to read a value from localStorage
  const getItem = (key) => {
    try {
      const storedValue = localStorage.getItem(key);
      // Check if the value is a valid JSON string
      if (
        storedValue &&
        storedValue !== "undefined" &&
        storedValue !== "null"
      ) {
        return JSON.parse(storedValue);
      }
      return null; // Return null if the value is not valid JSON
    } catch (error) {
      console.error("Error reading from localStorage for key:", key, error);
      return null; // If there's an error, return null
    }
  };

  // Helper function to set a value to localStorage
  const setItem = (key, value) => {
    try {
      // Ensure the value is serialized to JSON string
      const serializedValue = JSON.stringify(value);
      localStorage.setItem(key, serializedValue);
    } catch (error) {
      console.error("Error setting value in localStorage for key:", key, error);
    }
  };

  // Helper function to remove a key-value pair from localStorage
  const removeItem = (key) => {
    try {
      localStorage.removeItem(key); // Remove the value from localStorage
    } catch (error) {
      console.error(
        "Error removing value from localStorage for key:",
        key,
        error
      );
    }
  };

  return { getItem, setItem, removeItem };
};
