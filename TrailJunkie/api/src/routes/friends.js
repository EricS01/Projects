const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const FriendDAO = require('../db/FriendDAO');
const friendDAO = new FriendDAO();

const UserDAO = require('../db/UserDAO');
const userDAO = new UserDAO();

router.get('/friendRequests/find/:username', auth, (req, res) => {
    userDAO.getUserBySimilarUsername(req.params.username)
        .then((user) => {
            if (user === null) {
                return res.status(404).json({ error: 'User not found' });
            }

            return res.status(200).json(user);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

router.get('/friendRequests/:username', auth, (req, res) => {
    friendDAO.getFriendshipRequests(req.params.username)
        .then((user) => {
            if (user === null) {
                return res.status(404).json({ error: 'User not found' });
            }

            return res.status(200).json(user);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

router.post('/friendRequests/:username', auth, (req, res) => {
    const currentUser = req.body.currentUser;
    
    const requestedUser = req.params.username;
    friendDAO.createFriendship(currentUser, requestedUser)
        .then((user) => {
            if (user === null) {
                return res.status(404).json({ error: 'User not found' });
            }

            return res.status(200).json(user);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

router.post('/friendRequests/:username/request/:requestId', auth, (req, res) => {
    const currentUser = req.body.currentUser;
    const requestedUser = req.params.username;
    const status = req.params.requestId;
    console.log("HIT");
    friendDAO.updateFriendshipStatus(currentUser, requestedUser, status)
        .then((user) => {
            if (user === null) {
                return res.status(404).json({ error: 'User not found' });
            }

            return res.status(200).json(user);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});



module.exports = router;