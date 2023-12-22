const db = require('./database');
const Bike = require('./models/Bike');



class BikeDAO {

    async updateBike(bike, username) {
        try {
            const {results: duplicate} = await db.query(
                'SELECT * FROM Bike WHERE user_username = ?',
                [username]
            );
            //if no bike, add one
            if (duplicate.length === 0) {
                const { results } = await db.query(
                    'INSERT INTO Bike SET ?',
                    {
                        ...bike,
                        user_username: username,
                    });
                if (results.length === 0) {
                    return null;
                }
                return new Bike(results[0]);
            } else {
                const {results: data} = await db.query(
                    'UPDATE Bike SET ? WHERE user_username = ?',
                    [{...bike, user_username: username}, username]
                );
                return data.affectedRows;
            }
        } catch (err) {
            console.error(err);
            return null;
        }
    }

    async getCurrentBike(username) {
        try {
            const {results} = await db.query(
                'SELECT * FROM Bike WHERE user_username = ?',
                [username]
            );
            if (results.length === 0) {
                return null;
            }
            return new Bike(results[0]);
        } catch (err) {
            console.error(err);
            return null;
        }
    }

}

module.exports = BikeDAO;