const db = require('./database');
const Trail = require('../db/models/Trail');

class TrailDAO {

    async addToTrails(trail) {
        try {
            const {results: duplicate} = await db.query(
                'SELECT * FROM Trail WHERE trail_id = ?',
                [trail.trail_id]
            );
            if (duplicate.length === 0) {
                const { results } = await db.query(
                    'INSERT INTO Trail SET ?',
                    {
                        ...trail
                    });
                if (results.length === 0) {
                    return null;
                }
                return null;
            } else {
                //trail already found
                return null;
            }
        } catch (err) {
            console.error(err);
            return null;
        }
    }

    async getTrailByName(trailName) {
        try {
          const results = await db.query(
            'SELECT * FROM Trail WHERE name = ?',
            [trailName]
          );
          return results;
        } catch (err) {
          console.error(err);
          return null;
        }
      }

}

module.exports = TrailDAO;