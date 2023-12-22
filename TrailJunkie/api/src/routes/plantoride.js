const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const TrailDAO = require('../db/TrailDAO');
const trailDAO = new TrailDAO();

const EventsDAO = require('../db/EventsDAO');
const eventsDAO = new EventsDAO();


router.post('/plantoride', auth, (req, res) => {
    trailDAO.addToTrails(req.body);
    const plan =  {
        user_username: req.user.username,
        trail_id: req.body.trail_id
    }
    eventsDAO.addToPlanToRide(plan).then((event) => {
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

router.get('/plantoride/:username', auth, (req, res) => {
    let currentOrFriend = req.params.username;
    if(currentOrFriend == 'me') {
        currentOrFriend = req.user.username;
    }
    eventsDAO.getPlanToRide(currentOrFriend).then((event) => {
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

router.delete('/plantoride/:planId', auth, (req, res) => {
    let planId = req.params.planId;
    eventsDAO.removePlan(planId).then((event) => {
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