const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const TimelineDAO = require('../db/TimelineDAO');
const timelineDAO = new TimelineDAO();

router.get('/timeline/friends', auth, (req, res) => {
    timelineDAO.getFriendsTimeline(req.user.username).then(timeline => {
        return res.status(200).json(timeline);
    }).catch(err => {
        console.error(err);
            res.status(500).json({ error: 'Internal server error' });
    });
});

router.get('/timeline/personal', auth, (req, res) => {

    timelineDAO.getPersonalTimeline(req.user.username).then(timeline => {
        return res.status(200).json(timeline);
    }).catch(err => {
        console.error(err);
            res.status(500).json({ error: 'Internal server error' });
    });
});

module.exports = router;