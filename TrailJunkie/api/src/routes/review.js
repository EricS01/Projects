const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const TrailDAO = require('../db/TrailDAO');
const trailDAO = new TrailDAO();

const EventsDAO = require('../db/EventsDAO');
const eventsDAO = new EventsDAO();


router.post('/review', auth, (req, res) => {
    
    const {ratings, id, comment, ...trail} = req.body;
    
    trailDAO.addToTrails(trail);
    const review =  {
        rating: ratings,
        trail_id: id,
        comment: comment,
        user_username: req.user.username
    }
    
    eventsDAO.addToReview(review).then((event) => {
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

router.get('/review/:username', auth, (req, res) => {
    let currentOrFriend = req.params.username;
    if(currentOrFriend == 'me') {
        currentOrFriend = req.user.username;
    }
    eventsDAO.getReviews(currentOrFriend).then((event) => {
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