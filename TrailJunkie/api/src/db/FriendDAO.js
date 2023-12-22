const db = require('./database');
const User = require('./models/Friendship');

class FriendDAO {
    async createFriendship(user1, user2) {
      try {
        if (user1 == user2) {
          return null;
        }

        const existingFriendship = await db.query(
          'SELECT * FROM Friendship WHERE (user1_username = ? AND user2_username = ?) OR (user1_username = ? AND user2_username = ?) AND (status = "accepted" OR status = "pending")',
          [user1, user2, user2, user1]
        );

        if (existingFriendship.results.length > 0) {
          console.log('Friendship already exists');
          return null;
        }

        const creationDate = new Date();
        const result = await db.query('INSERT INTO Friendship SET ?', {
          user1_username: user1,
          user2_username: user2,
          status: 'pending',
        });

        const friendshipId = result.insertId;

        return {
          friendship_id: friendshipId,
          user1_username: user1,
          user2_username: user2,
          status: 'pending',
          creation_date: creationDate,
        };
      } catch (err) {
        console.error(err);
        return null;
      }
    }
  
    async getFriendshipRequests(user1) {
      try {
        const results = await db.query(
          'SELECT Friendship.*, User.avatar FROM Friendship JOIN User ON Friendship.user1_username = User.username WHERE Friendship.user2_username = ? AND Friendship.status = ?',
          [user1, 'pending']
        );
    
        const friendshipRequests = Array.isArray(results.results)
          ? results.results.map((row) => {
              return {
                friendship_id: row.friendship_id,
                user1_username: row.user1_username,
                user2_username: row.user2_username,
                status: row.status,
                creation_date: row.creation_date,
                avatar: row.avatar, 
              };
            })
          : [];
    
        return friendshipRequests;
      } catch (err) {
        console.error(err);
        return null;
      }
    }
    
      
      

      async updateFriendshipStatus(currentUser, requestedUser, status) {
        try {
          const result = await db.query(
            'UPDATE Friendship SET status = ? WHERE user2_username = ? AND user1_username = ?',
            [status, currentUser, requestedUser]
          );
      
          return result.affectedRows;
        } catch (err) {
          console.error(err);
          return null;
        }
      }
      


}

module.exports = FriendDAO;
