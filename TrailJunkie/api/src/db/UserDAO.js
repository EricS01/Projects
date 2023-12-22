const db = require('./database');
const User = require('./models/User');

class UserDAO {
  async getUserByUsername(username) {
    try {
      const { results } = await db.query(
        'SELECT User.*, bike_id, Bike.name AS bike_name, Bike.year AS bike_year, Bike.color AS bike_color, Bike.make AS bike_make, Bike.model AS bike_model, Bike.image AS bike_image FROM User LEFT JOIN Bike ON User.username = Bike.user_username WHERE User.username = ?',
        [username]
      );
      if (results.length === 0) {
        return null;
      }
      return new User(results[0]);
    } catch (err) {
      console.error(err);
      return null;
    }
  }

  async getUserBySimilarUsername(username) {
    try {
      const { results } = await db.query(
        'SELECT User.*, bike_id, Bike.name AS bike_name, Bike.year AS bike_year, Bike.color AS bike_color, Bike.make AS bike_make, Bike.model AS bike_model, Bike.image AS bike_image FROM User LEFT JOIN Bike ON User.username = Bike.user_username WHERE User.username LIKE ?',
        [username + '%']
      );
      if (results.length === 0) {
        return null;
      }
      return new User(results[0]);
    } catch (err) {
      console.error(err);
      return null;
    }
  }


  async createUser(userData) {
    try {
      const creationDate = new Date();
      await db.query('INSERT INTO User SET ?', {
        ...userData,
        creation_date: creationDate,
      });
      return new User({
        ...userData,
        creation_date: creationDate,
      });
    } catch (err) {
      console.error(err);
      return null;
    }
  }

  async updateUser(username, userData) {
    try {
      const result = await db.query('UPDATE User SET ? WHERE username = ?', [userData, username]);
      return result.affectedRows;
    } catch (err) {
      console.error(err);
      return null;
    }
  }

  async deleteUser(username) {
    try {
      const result = await db.query('DELETE FROM User WHERE username = ?', [username]);
      return result.affectedRows;
    } catch (err) {
      console.error(err);
      return null;
    }
  }

  async getSettings(username) {
    try {
      const results = await db.query('SELECT email, avatar FROM User WHERE username = ?', [username]);
      if (results.length === 0) {
        return null;
      }
      return {
        email: results.results[0].email,
        avatar: results.results[0].avatar
      }
    } catch (err) {
      console.error(err);
      return null;
    }
  }

  async updateSettings(username, data) {
    try {
      const result = await db.query(
        'UPDATE User SET ? WHERE username = ?',
        [{...data}, username]
    );
      return result.results.affectedRows;
    } catch (err) {
      console.error(err);
      return null;
    }
  }
}

module.exports = UserDAO;
