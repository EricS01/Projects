module.exports = class Review {
    id = null;
    eventType = null;
    rating = 0;
    trailId = null;
    trailName = null;
    trailUrl = null;
    trailThumbnail = null;;
    comment = null;
    userId = null;
    avatar = null;
    timestamp = null;

    constructor(data) {
        this.id = data.review_id;
        this.eventType = 'review';
        this.rating = data.rating;
        this.trailId = data.trail_id;
        this.trailName = data.name;
        this.trailUrl = data.url;
        this.trailThumbnail = data.thumbnail;
        this.comment = data.comment;
        this.userId = data.user_username;
        this.avatar = data.avatar;
        this.timestamp = data.timestamp;
    }

    toJSON() {
        return {
            id: this.id,
            eventType: this.eventType,
            rating: this.rating,
            trailId: this.trailId,
            trailName: this.trailName,
            trailUrl: this.trailUrl,
            trailThumbnail: this.trailThumbnail,
            comment: this.comment,
            userId: this.userId,
            avatar: this.avatar,
            timestamp: this.timestamp,
        };

    }
};
