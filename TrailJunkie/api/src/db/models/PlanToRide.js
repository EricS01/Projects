module.exports = class PlanToRide {
    id = null;
    eventType = null;
    userId = null;
    avatar = null;
    trailId = null;
    timestamp = null;
    trailName = null;

    constructor(data) {
        this.id = data.plan_to_ride_id;
        this.eventType = 'planToRide';
        this.userId = data.user_username;
        this.avatar = data.avatar
        this.trailId = data.trail_id;
        this.trailName = data.name;
        this.timestamp = data.timestamp;
    }

    toJSON() {
        return {
            id: this.id,
            eventType: this.eventType,
            userId: this.userId,
            avatar: this.avatar,
            trailId: this.trailId,
            trailName: this.trailName,
            timestamp: this.timestamp,
        };
    }
};
