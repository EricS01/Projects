const express = require('express');
const router = express.Router({ mergeParams: true });

const authRouter = require('./auth');
const timelineRouter = require('./timeline');
const userRouter = require('./user');
const trailsRouter = require('./trails');
const friendsRouter = require('./friends');
const favoritesRouter = require('./favorites');
const plantorideRouter = require('./plantoride');
const reviewRouter = require('./review');
const bikeRouter = require('./bike');

router.use('/auth', authRouter);
router.use(timelineRouter);
router.use('/user/', userRouter);
router.use(favoritesRouter);
router.use(plantorideRouter);
router.use(reviewRouter);
router.use(trailsRouter);
router.use(friendsRouter);
router.use(bikeRouter);

module.exports = router;