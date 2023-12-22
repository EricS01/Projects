const Bike = require('./Bike');

module.exports = class User {
    username = null;
    email = null;
    creationDate = null;
    avatar = null;
    bike = null;
    #passwordHash = null;
    #salt = null;

    constructor(data) {
        this.username = data.username;
        this.email = data.email;
        this.passwordHash = data.password_hash;
        this.salt = data.salt;
        this.creationDate = data.creation_date;
        this.avatar = data.avatar;

        if (data.bike_id != null) {
            this.bike = new Bike({
                bike_id: data.bike_id,
                name: data.bike_name,
                year: data.bike_year,
                color: data.bike_color,
                make: data.bike_make,
                model: data.bike_model,
                image: data.bike_image,
            });
        }
    }

    toJSON() {
        return {
            username: this.username,
            email: this.email,
            creationDate: this.creationDate,
            avatar: this.avatar,
            bike: this.bike,
        };
    }
};
