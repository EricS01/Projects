module.exports = class Trail {
    id = null;
    name = null;
    url = null;
    length = null;
    description = null;
    directions = null;
    city = null;
    region = null;
    country = null;
    lat = null;
    lon = null;
    difficulty = null;
    features = null;
    rating = null;
    thumbnail = null;

    constructor(data) {
        this.id = data.trail_id;
        this.name = data.name;
        this.url = data.url;
        this.length = data.length;
        this.description = data.description;
        this.directions = data.directions;
        this.city = data.city;
        this.region = data.region;
        this.country = data.country;
        this.lat = data.lat;
        this.lon = data.lon;
        this.difficulty = data.difficulty;
        this.features = data.features;
        this.rating = data.rating;
        this.thumbnail = data.thumbnail;
    }
};
