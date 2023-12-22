const axios = require('axios');

const getTrails = async (longitude, latitude, radius, page) => {
    const options = {
        method: 'GET',
        url: 'https://trailapi-trailapi.p.rapidapi.com/trails/explore/',
        params: {
            lon: longitude,
            lat: latitude,
            radius: radius,
            per_page: 10,
            page: page,
        },
        headers: {
            'X-RapidAPI-Key': process.env.RAPID_API_KEY,
            'X-RapidAPI-Host': 'trailapi-trailapi.p.rapidapi.com'
        }
    };

    try {
        const response = await axios.request(options);
        return response.data;
    } catch (error) {
        throw error;
    }
};

module.exports = {
    getTrails
};
