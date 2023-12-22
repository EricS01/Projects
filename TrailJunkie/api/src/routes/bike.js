const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');

const BikeDAO = require('../db/BikeDAO');
const bikeDAO = new BikeDAO();

router.post('/bike', auth, (req, res) => {
    bikeDAO.updateBike(req.body, req.user.username).then(rows => {
        if (rows) {
            return res.status(200).json(rows);

        }
        return res.status(400).json({error: 'One of the fields is invalid.'})
    }).catch(err => {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error' });
    })
});

router.get('/bike/:username', auth, (req, res) => {
    let currentOrFriend = req.params.username;
    if(currentOrFriend == 'me') {
        currentOrFriend = req.user.username;
    }
    bikeDAO.getCurrentBike(currentOrFriend).then(bike => {
        return res.status(200).json(bike);
    }).catch(err => {
        console.error(err);
        return res.status(500).json({ error: 'Internal server error' });
    });
});


module.exports = router;