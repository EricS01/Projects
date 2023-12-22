const db = require('./database');
const PlanToRide = require('./models/PlanToRide');
const Favorite = require('./models/Favorite');
const Review = require('./models/Review');


class TimelineDAO {
    async getPersonalTimeline(username) {
        try {
            let reviewList = [];
            const { results: reviews } = await db.query(
                'SELECT r.*, t.name, t.thumbnail AS trail_rating FROM Review AS r JOIN Trail AS t ON t.trail_id = r.trail_id WHERE user_username = ?',
                [username]
            );
            if (reviews.length === 0) {
                reviewList = [];
            } else {
                reviewList = reviews.map(review => new Review(review));
            }

            let ptrList = [];
            const { results: ptrs } = await db.query(
                'SELECT * FROM PlanToRide AS p JOIN Trail AS t ON t.trail_id = p.trail_id WHERE user_username = ?',
                [username]
            );
            if (ptrs.length === 0) {

            } else {
                ptrList = ptrs.map(ptr => new PlanToRide(ptr));
            }

            let favList = [];
            const { results: favorites } = await db.query(
                'SELECT * FROM Favorite AS f JOIN Trail AS t ON t.trail_id = f.trail_id WHERE user_username = ?',
                [username]
            );
            if (favorites.length === 0) {
                favList = [];
            } else {
                favList = favorites.map(fav => new Favorite(fav));
            }


            return [...reviewList, ...favList, ...ptrList];
        } catch (err) {
            console.error(err);
            return null;
        }
    }

    async getFriendsTimeline(username) {
        try {
            let friendList = [];

            const { results: friends } = await db.query(
                `SELECT 
                    CASE 
                        WHEN user1_username = ? THEN user2_username 
                        ELSE user1_username 
                    END AS friendName 
                FROM Friendship 
                WHERE (user1_username = ? OR user2_username = ?) AND status = 'accepted'`,
                [username, username, username]
            );
            friendList = friends.map(friend => friend.friendName);          


            let reviewList = [];
            const { results: reviews } = await db.query(
                'SELECT r.*, t.name, t.url, t.thumbnail, u.* FROM Review AS r JOIN Trail AS t ON t.trail_id = r.trail_id JOIN User AS u on u.username = r.user_username WHERE r.user_username IN (?)',
                [friendList]
            );
            if (reviews.length === 0) {
                reviewList = [];
            } else {
                reviewList = reviews.map(review => new Review(review));
            }

            let ptrList = [];
            const { results: ptrs } = await db.query(
                'SELECT * FROM PlanToRide AS p JOIN Trail AS t ON t.trail_id = p.trail_id JOIN User AS u on u.username = p.user_username WHERE user_username IN (?)',
                [friendList]
            );
            if (ptrs.length === 0) {

            } else {
                ptrList = ptrs.map(ptr => new PlanToRide(ptr));
            }

            let favList = [];
            const { results: favorites } = await db.query(
                'SELECT * FROM Favorite AS f JOIN Trail AS t ON t.trail_id = f.trail_id JOIN User AS u on u.username = f.user_username WHERE user_username IN (?)',
                [friendList]
            );
            if (favorites.length === 0) {
                favList = [];
            } else {
                favList = favorites.map(fav => new Favorite(fav));
            }


            return [...reviewList, ...favList, ...ptrList];
        } catch (err) {
            console.error(err);
            return null;
        }
    }
}

module.exports = TimelineDAO;