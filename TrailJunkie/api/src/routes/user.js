const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const UserDAO = require('../db/UserDAO');
const userDAO = new UserDAO();

router.get('/me', auth, (req, res) => {
    userDAO.getUserByUsername(req.user.username)
        .then((user) => {
            if (!user) {
                return res.status(404).json({ error: 'User not found' });
            }

            return res.status(200).json(user);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({ error: 'Internal server error' });
        });
});

router.get('/:username', auth, (req, res) => {
    userDAO.getUserByUsername(req.params.username)
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

router.put('/:username', auth, (req, res) => {
    userDAO.updateUser(req.params.username, req.body)
        .then((user) => {
            if (!user) {
                return res.status(404).json({ error: 'User not found' });
            }

            res.status(200).json(user);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

router.get('/me/settings', auth, (req, res) => {
    userDAO.getSettings(req.user.username)
        .then((result) => {
            if (!result) {
                return res.status(404).json({ error: 'User not found' });
            }

            res.status(200).json(result);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

router.post('/me/settings', auth, (req, res) => {
    userDAO.updateSettings(req.user.username, req.body)
        .then((result) => {
            if (!result) {
                return res.status(400).json({ error: 'Could not update settings' });
            }

            res.status(200).json(result);
        })
        .catch((err) => {
            console.error(err);
            return res.status(500).json({ error: 'Internal server error' });
        });
});

module.exports = router;