const express = require('express');
const axios = require('axios');
const router = express.Router();
const auth = require("../middleware/auth");
const trailService = require('../services/trails');

const TrailDAO = require('../db/TrailDAO');
const trailDAO = new TrailDAO();

router.get('/trails', async (req, res) => {
    if (!req.query.location) {
        res.status(400).json({ "msg": "location is required" });
    }

    let longitude;
    let latitude;
    try {
        const geocodeUrl = `https://geocode.maps.co/search?q=${req.query.location}`;
        const geocodeResult = await axios.get(geocodeUrl);
        const geocodeData = geocodeResult.data;

        if (geocodeData.length == 0) {
            res.json({ "msg": "nothing found" });
            return;
        }

        latitude = parseFloat(geocodeData[0].lat);
        longitude = parseFloat(geocodeData[0].lon);
    } catch (error) {
        res.status(400).json({ "msg": "location is invalid" });
        return;
    };

    if (!longitude || !latitude) {
        res.status(400).json({ "msg": "location is invalid" });
        return;
    }

    try {
        const trailData = await trailService.getTrails(longitude, latitude, req.query.radius ? parseInt(req.query.radius) : 25, req.query.page ? parseInt(req.query.page) : 1);
        res.json({
            coords: {
                lat: latitude,
                lon: longitude
            },
            data: trailData
        });
    } catch (error) {
        res.json([]);
    }
});

router.get('/trails/*', auth, (req, res) => {
    const decodedTrailName = decodeURIComponent(req.params[0]);
    trailDAO.getTrailByName(decodedTrailName).then(trail => {
        return res.status(200).json(trail.results);
    }).catch(err => {
        console.error(err);
            res.status(500).json({ error: 'Internal server error' });
    });
});




module.exports = router;