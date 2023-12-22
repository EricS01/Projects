const express = require('express');
const router = express.Router();
const auth = require("../middleware/auth");
const crypto = require('crypto');
const jwt = require('jsonwebtoken');
const UserDAO = require('../db/UserDAO');

const userDAO = new UserDAO();
const secret = process.env.API_SECRET_KEY;

router.post('/login', (req, res) => {
    userDAO.getUserByUsername(req.body.username).then((user) => {
        if (!user) {
            return res.status(404).json({ msg: 'User not found' });
        }
        if (req.body.username == "me") {
            return res.status(400).json({ msg: 'Reserved username' });
        }
        if (user.passwordHash !== hashPassword(req.body.password, user.salt)) {
            return res.status(401).json({ msg: 'Invalid username or password' });
        }
        const token = jwt.sign({ user }, secret, { expiresIn: '1h' });
        return res.cookie('token', token, {
            httpOnly: true, expires: new Date(Date.now() + 3600000), secure: true

        }).sendStatus(200);
    }).catch((err) => {
        console.error(err);
        return res.status(500).json({ msg: 'Internal server error' });
    });
});

router.post('/register', (req, res) => {
    if (!req.body.username || !req.body.email || !req.body.password) {
        return res.status(400).json({ msg: 'Missing required information' });
    }
    if (req.body.username == "me") {
        return res.status(400).json({msg: 'Reserved username'});
    }

    const emailRegex = /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+[A-Za-z]+$/;
    if (!emailRegex.test(req.body.email)) {
        return res.status(400).json({ msg: 'Invalid email' });
    }

    if (req.body.password.length < 8) {
        return res.status(400).json({ msg: 'Password must be at least 8 characters' });
    }

    userDAO.getUserByUsername(req.body.username)
        .then((user) => {
            if (user) {
                throw new Error('Username already in use');
            } else {
                const salt = generateSalt();
                const passwordHash = hashPassword(req.body.password, salt);
                const userData = {
                    username: req.body.username,
                    email: req.body.email.toLowerCase(),
                    password_hash: passwordHash,
                    salt,
                    avatar: "/test.png"
                };

                return userDAO.createUser(userData);
            }
        })
        .then((user) => {
            if (!user) {
                throw new Error('Internal server error');
            }
            return res.status(201).json({ msg: 'User created' });
        })
        .catch((err) => {
            console.error(err);
            res.status(400).json({ msg: err.message });
        });
});


router.delete('/logout', auth, (req, res) => {
    return res.clearCookie('token').sendStatus(200);
});

function generateSalt(length = 16) {
    return crypto.randomBytes(length).toString('hex');
}

function hashPassword(password, salt) {
    const iterations = 100000;
    const keylen = 64;
    const digest = 'sha512';
    const derivedKey = crypto.pbkdf2Sync(password, salt, iterations, keylen, digest);
    return derivedKey.toString('hex');
}

module.exports = router;