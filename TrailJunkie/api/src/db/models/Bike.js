module.exports = class Bike {
    id = null;
    name = null;
    year = null;
    color = null;
    make = null;
    model = null;
    image = null;

    constructor(data) {
        this.id = data.bike_id;
        this.name = data.name;
        this.year = data.year;
        this.color = data.color;
        this.make = data.make;
        this.model = data.model;
        this.image = data.image;
    }

    toJSON() {
        return {
            id: this.id,
            name: this.name,
            year: this.year,
            color: this.color,
            make: this.make,
            model: this.model,
            image: this.image
        };

    }
};
