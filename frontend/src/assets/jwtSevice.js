export const parseJwt = (token) => {
  const base64Url = token.split(".")[1]; // Get the second part (the payload)
  const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/"); // Ensure it's correctly base64-encoded
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split("")
      .map(function (c) {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );

  return JSON.parse(jsonPayload); // Parse the JSON payload
};
