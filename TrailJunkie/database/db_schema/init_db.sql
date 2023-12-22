CREATE TABLE Trail (
  trail_id INT PRIMARY KEY,
  name VARCHAR(255),
  url TEXT,
  length VARCHAR(255),
  description TEXT,
  directions TEXT,
  city VARCHAR(255),
  region VARCHAR(255),
  country VARCHAR(255),
  lat VARCHAR(255),
  lon VARCHAR(255),
  difficulty VARCHAR(255),
  features TEXT,
  rating FLOAT,
  thumbnail TEXT
);

CREATE TABLE User (
  username VARCHAR(255) PRIMARY KEY,
  email VARCHAR(255) UNIQUE,
  password_hash VARCHAR(255),
  salt VARCHAR(255),
  creation_date DATETIME,
  avatar TEXT
);

CREATE TABLE Bike (
  bike_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  year INT,
  color VARCHAR(255),
  make VARCHAR(255),
  model VARCHAR(255),
  image TEXT,
  user_username VARCHAR(255),
  FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE Review (
  review_id INT PRIMARY KEY AUTO_INCREMENT,
  rating INT,
  trail_id INT,
  comment TEXT,
  user_username VARCHAR(255),
  timestamp DATETIME,
  FOREIGN KEY (trail_id) REFERENCES Trail(trail_id),
  FOREIGN KEY (user_username) REFERENCES User(username)
);

CREATE TABLE PlanToRide (
  plan_to_ride_id INT PRIMARY KEY AUTO_INCREMENT,
  user_username VARCHAR(255),
  trail_id INT,
  timestamp DATETIME,
  FOREIGN KEY (user_username) REFERENCES User(username),
  FOREIGN KEY (trail_id) REFERENCES Trail(trail_id)
);

CREATE TABLE Favorite (
  favorite_id INT PRIMARY KEY AUTO_INCREMENT,
  user_username VARCHAR(255),
  trail_id INT,
  timestamp DATETIME,
  FOREIGN KEY (user_username) REFERENCES User(username),
  FOREIGN KEY (trail_id) REFERENCES Trail(trail_id)
);

CREATE TABLE Friendship (
  friendship_id INT PRIMARY KEY AUTO_INCREMENT,
  user1_username VARCHAR(255),
  user2_username VARCHAR(255),
  status VARCHAR(255),
  FOREIGN KEY (user1_username) REFERENCES User(username),
  FOREIGN KEY (user2_username) REFERENCES User(username)
);
