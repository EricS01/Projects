import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";

// Check if service workers are supported
if ("serviceWorker" in navigator) {
  window.addEventListener("load", () => {
    navigator.serviceWorker
      .register("/service-worker.js", {
        scope: "/",
      })
      .then(
        (registration) => {
          // Registration was successful
          console.log(
            "ServiceWorker registration successful with scope: ",
            registration.scope
          );
        },
        (err) => {
          // Registration failed :(
          console.log("ServiceWorker registration failed: ", err);
        }
      );
  });
}

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
