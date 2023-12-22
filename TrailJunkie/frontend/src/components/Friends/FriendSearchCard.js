import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";

function FriendSearchCard({ user }) {
  const [request, setRequest] = useState("Add Friend");

  const sendRequest = async () => {
    const res = await fetch(`/api/user/me`);
    const d = await res.json();
    const user1 = d.username;

    fetch(`/api/friendRequests/${user.username}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        currentUser: user1,
      }),
    }).catch((error) => {
      console.error(error);
    });
    setRequest("Requested");
  };

  return (
    <div className="flex items-center justify-between w-full p-4 text-white border border-black shadow-lg rounded-2xl bg-tj-green">
      <Link to={`/profile/${user.username}`}>
        <div className="flex space-x-2">
          <img src={user.avatar} className="w-8 h-8 rounded-full" />
          <p className="mr-4 text-left">{user.username}</p>
        </div>
      </Link>

      <button
        onClick={sendRequest}
        className="w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg bg-tj-light-brown hover:bg-tj-lightish-brown"
      >
        {request}
      </button>
    </div>
  );
}

export default FriendSearchCard;
