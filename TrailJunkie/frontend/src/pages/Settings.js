import React, { useState, useEffect } from "react";
import Header from "../components/Header";
import { offlineCode } from "../utils/constants";
import Offline from "./Offline";

function Settings() {
  const [email, setEmail] = useState("");
  const [avatar, setAvatar] = useState("");
  const [offline, setOffline] = useState(false);

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const response = await fetch("/api/user/me/settings");
        const data = await response.json();
        if (response.status === offlineCode) {
          setOffline(true);
        }
        setEmail(data.email);
        setAvatar(data.avatar);
      } catch (error) {
        console.error(error.message);
      }
    };

    fetchEvents();
  }, []);

  const updateSettings = () => {
    fetch("/api/user/me/settings", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email, avatar }),
    })
      .then((response) => {
        if (response.status != 200) {
          alert("Error updating settings, please try again");
        }
      })
      .catch((err) => {
        console.error(err);
        alert("Error updating settings, please try again");
      });
  };

  return (
    <Header>
      <div className="sm:max-w-5xl sm:m-auto font-Outfit">
        <div className="flex flex-col pt-6 pb-6 pl-3 pr-2">
          <h1 className="pt-6 pb-6 text-2xl text-center sm:text-4xl sm:text-center">
            Settings
          </h1>
          {offline ? (
            <Offline />
          ) : (
            <div className="pb-4">
              <div className="flex flex-col bg-white border-2 rounded-xl border-amber-800">
                <div className="flex flex-col flex-auto max-w-full min-w-0 p-4">
                  <div className="pb-6">
                    <label className="text-xl sm:text-3xl">Email: </label>
                    <input
                      type="text"
                      className="text-lg sm:text-3xl font-Outfit text-tj-black"
                      placeholder={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                  </div>

                  <div className="pb-4">
                    <label className="text-xl sm:text-3xl">Avatar URL: </label>
                    <input
                      type="text"
                      className="text-lg bg-white sm:text-3xl font-Outfit text-tj-black"
                      placeholder={avatar}
                      onChange={(e) => setAvatar(e.target.value)}
                    />
                  </div>

                  <button
                    className="block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-turquoise hover:bg-slate-900 hover:text-white"
                    onClick={updateSettings}
                  >
                    Save Changes
                  </button>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </Header>
  );
}

export default Settings;
