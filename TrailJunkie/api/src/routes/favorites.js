const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const TrailDAO = require('../db/TrailDAO');
const trailDAO = new TrailDAO();

const EventsDAO = require('../db/EventsDAO');
const eventsDAO = new EventsDAO();


router.post('/favorites', auth, (req, res) => {
    trailDAO.addToTrails(req.body);
    const favorite =  {
        user_username: req.user.username,
        trail_id: req.body.trail_id
    }
    eventsDAO.addToFavorites(favorite).then((event) => {
        if (!event) {
            return res.status(404).json({ error: 'Event not found' });
        }

        res.status(200).json(event);
    })
    .catch((err) => {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error' });
    });
});

router.get('/favorites/:username', auth, (req, res) => {
    let currentOrFriend = req.params.username;
    if(currentOrFriend == 'me') {
        currentOrFriend = req.user.username;
    }
    eventsDAO.getFavorites(currentOrFriend).then((event) => {
        if (!event) {
            return res.status(404).json({ error: 'Event not found' });
        }
        res.status(200).json(event);
    })
    .catch((err) => {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error' });
    });
});

router.delete('/favorites/:favoriteId', auth, (req, res) => {
    let favoriteId = req.params.favoriteId;
    eventsDAO.removeFavorite(favoriteId).then((event) => {
        if (!event) {
            return res.status(404).json({ error: 'Event not found' });
        }
        res.status(200).json(event);
    })
    .catch((err) => {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error' });
    });
});

module.exports = router;