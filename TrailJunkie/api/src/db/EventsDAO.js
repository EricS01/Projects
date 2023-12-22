const db = require('./database');

const PlanToRide = require('./models/PlanToRide');
const Favorite = require('./models/Favorite');
const Review = require('./models/Review');

class EventsDAO {



    async addToFavorites(favorites) {
        try {
          const {results: existingEntry} = await db.query(
            'SELECT * FROM Favorite WHERE user_username = ? AND trail_id = ?',
            [favorites.user_username, favorites.trail_id]
          );
      
          if (existingEntry.length > 0) {

            console.log('Duplicate entry.');
            return null;
          }
      
          const creationDate = new Date();
          const {result} = await db.query(
            'INSERT INTO Favorite SET ?',
            { 
              ...favorites,
              timestamp: creationDate,
            }
          );
          return new Favorite({
            ...favorites,
            timestamp: creationDate,
          });
        } catch (err) {
          console.error(err);
          return null;
        }
      }
      
      
      async addToPlanToRide(plan) {
        try {
            const {results: existingEntry} = await db.query(
              'SELECT * FROM PlanToRide WHERE user_username = ? AND trail_id = ?',
              [plan.user_username, plan.trail_id]
            );
        
            if (existingEntry.length > 0) {
  
              console.log('Duplicate entry.');
              return null;
            }
        
            const creationDate = new Date();
            const {result} = await db.query(
              'INSERT INTO PlanToRide SET ?',
              { 
                ...plan,
                timestamp: creationDate,
              }
            );
            return new PlanToRide({
              ...plan,
              timestamp: creationDate,
            });
          } catch (err) {
            console.error(err);
            return null;
          }
      }

      async addToReview(review) {
        try {
            const {results: existingEntry} = await db.query(
              'SELECT * FROM Review WHERE user_username = ? AND trail_id = ?',
              [review.user_username, review.trail_id]
            );
        
            if (existingEntry.length > 0) {
  
              console.log('Duplicate entry.');
              return null;
            }
        
            const creationDate = new Date();
            const {result} = await db.query(
              'INSERT INTO Review SET ?',
              { 
                ...review,
                timestamp: creationDate,
              }
            );
            return new Review({
              ...review,
              timestamp: creationDate,
            });
          } catch (err) {
            console.error(err);
            return null;
          }
      }

      async getFavorites(username) {
            let favList = [];
            const { results: favs } = await db.query(
                'SELECT * FROM Favorite AS f JOIN Trail AS t ON t.trail_id = f.trail_id WHERE user_username = ?',
                [username]
            );

            
            if (favs.length === 0) {

            } else {
                favList = favs.map(fav => new Favorite(fav));
            }
            return favs;

      }

      async getPlanToRide(username) {
        let planList = [];
        const { results: plans } = await db.query(
            'SELECT * FROM PlanToRide AS p JOIN Trail AS t ON t.trail_id = p.trail_id WHERE user_username = ?',
            [username]
        );

        
        if (plans.length === 0) {

        } else {
            planList = plans.map(fav => new Favorite(fav));
        }
        return plans;

    }



    async getReviews(username) {
        let revList = [];
        const { results: revs } = await db.query(
            'SELECT r.*, t.rating AS trail_rating, t.thumbnail AS thumbnail, t.name AS trailName FROM Review AS r JOIN Trail AS t ON t.trail_id = r.trail_id WHERE r.user_username = ?',
            [username]
        );

        
        if (revs.length === 0) {

        } else {
            revList = revs.map(rev => new Review(rev));
        }
        return revs;

  }

  async removePlan(planId) {
    try {
        const { results } = await db.query(
            'DELETE FROM PlanToRide WHERE plan_to_ride_id = ?',
            [planId]
        );

        if (results.affectedRows > 0) {
            console.log("Plan with ID removed successfully");
        } else {
            console.log("No plan ID found");
        }
    } catch (error) {
        console.error('Error removing plan:', error);
    }
  }

  async removeFavorite(favoriteId) {
    try {
        const { results } = await db.query(
            'DELETE FROM Favorite WHERE favorite_id = ?',
            [favoriteId]
        );

        if (results.affectedRows > 0) {
            console.log("Favorite with ID removed successfully");
        } else {
            console.log("No Favorite ID found");
        }
    } catch (error) {
        console.error('Error removing favorite:', error);
    }
  }

}

module.exports = EventsDAO;