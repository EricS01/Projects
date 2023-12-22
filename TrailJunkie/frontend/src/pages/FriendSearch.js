import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import Loading from "../components/Loading";
import FriendCard from "../components/Friends/FriendSearchCard";
import { offlineCode } from "../utils/constants";

function FriendSearch() {
  const [friendQuery, setFriendQuery] = useState("");
  const [userData, setUserData] = useState({});
  const [loading, setLoading] = useState(false);
  const [notFound, setNotFound] = useState(false);
  const [notFoundMessage, setNotFoundMessage] = useState("No user found. Please try again.");

  const handleFriendSearch = async () => {
    setNotFound(false);
    setLoading(true);
    const res = await fetch(`/api/friendRequests/find/${friendQuery}`);
    const d = await res.json();
    if (res.status !== 200 && res.status !== 304) {
      setLoading(false);
      setNotFound(true);
      setUserData({});
      if (res.status === offlineCode) {
        setNotFoundMessage("This action could not be completed while offline.");
    } else {
        setNotFoundMessage("No user found. Please try again.");
    }
      return;
    }

    setUserData(d);
    setLoading(false);
  };

  return (
    <Header>
      <div className="sm:max-w-5xl sm:m-auto font-Outfit">
        <div className="flex mb-8 sm:mr-12 sm:justify-center sm:space-x-8">
          <div className="flex w-full h-12 mx-12 mt-2 sm:h-16 sm:mt-3">
            <Link to="/friend-search" className="w-3/5 h-full">
              <button className="block w-full h-full font-bold text-white border-r border-black rounded-tl-lg rounded-bl-lg shadow-lg bg-tj-dark-brown md:text-2xl sm:text-xl boldtext-xl hover:bg-tj-lightish-brown ">
                Search
              </button>
            </Link>
            <Link to="/requests" className="w-3/5 h-full rounded-full">
              <button className="block w-full h-full font-bold bg-white rounded-tr-lg rounded-br-lg shadow-lg text-tj-turquoise sm:text-xl md:text-2xl hover:bg-tj-turquoise hover:text-white">
                Requests
              </button>
            </Link>
          </div>
        </div>

        <div className="flex p-6">
          <div className="w-5/6 pr-2">
            <input
              className="w-full p-2 border-2 border-gray-300 rounded-md align-center"
              type="text"
              placeholder="Find a User"
              value={friendQuery}
              onChange={(e) => setFriendQuery(e.target.value)}
            />
          </div>
          <button
            onClick={handleFriendSearch}
            className="w-1/6 text-white rounded-lg shadow-lg bg-tj-turquoise"
          >
            Search
          </button>
        </div>

        {loading && <Loading />}
        {!loading && Object.keys(userData).length > 0 && (
          <div className="px-4 mb-4 space-y-4">
            <FriendCard user={userData} />
          </div>
        )}
        {notFound && <div className="text-center">{notFoundMessage}</div>}
      </div>
    </Header>
  );
}

export default FriendSearch;
