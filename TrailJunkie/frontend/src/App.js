import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

import Landing from "./pages/Landing";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Timeline from "./pages/Timeline/Timeline";
import TrailSearch from "./pages/TrailSearch";
import Profile from "./pages/Profile";
import FriendSearch from "./pages/FriendSearch";
import Reviews from "./pages/Reviews";
import Bike from "./pages/Bike";
import PlanToRide from "./pages/PlanToRide";
import Favorites from "./pages/Favorites";
import Settings from "./pages/Settings";
import FriendRequest from "./pages/FriendRequest";
import WriteAReview from "./pages/WriteAReview";
import AuthProvider from "./Auth/AuthProvider";
import Error404 from "./pages/404";

function App() {
  return (
    <div className="min-h-screen bg-tj-bg">
      <AuthProvider>
        <Router>
          <Routes>
            <Route path="/" element={<Landing />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/timeline/" element={<Timeline />} />
            <Route path="/timeline/:type" element={<Timeline />} />
            <Route path="/search" element={<TrailSearch />} />
            <Route path="/profile/:id" element={<Profile />} />
            <Route path="/profile/:id/bike" element={<Bike />} />
            <Route path="/profile/:id/reviews" element={<Reviews />} />
            <Route path="/profile/:id/plan-to-ride" element={<PlanToRide />} />
            <Route path="/profile/:id/favorites" element={<Favorites />} />
            <Route path="/profile/:id/settings" element={<Settings />} />
            <Route path="/friend-search" element={<FriendSearch />} />
            <Route path="/requests" element={<FriendRequest />} />
            <Route path="/write-review" element={<WriteAReview />} />
            <Route path="/favorites" element={<Favorites />} />
            <Route path="/settings" element={<Settings />} />
            <Route path="*" element={<Error404 />} />
          </Routes>
        </Router>
      </AuthProvider>
    </div>
  );
}

export default App;
