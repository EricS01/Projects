import React, { useState, useMemo } from 'react';
import Header from '../components/Header';
import TrailCard from '../components/TrailSearch/TrailCard';
import Loading from "../components/Loading";
import {offlineCode} from "../utils/constants";

function Search() {
    const [coords, setCoords] = useState({ lat: 0, lon: 0 });
    const [query, setQuery] = useState("");
    const [loading, setLoading] = useState(false);
    const [radius, setRadius] = useState(25);
    const [trailData, setTrailData] = useState([]);
    const [notFound, setNotFound] = useState(false);
    const [notFoundMessage, setNotFoundMessage] = useState("No trails found. Please try again.");

    const radiusOptions = [5, 10, 15, 20, 25, 50];

    const handleSearch = async () => {
        setNotFound(false);
        setLoading(true);
        const res = await fetch(`/api/trails?location=${query}&radius=${radius}`);
        const d = await res.json();
        if (!d.data?.data || d.data.data.length === 0) {
            setLoading(false);
            setNotFound(true);
            setTrailData([]);
            if (res.status === offlineCode) {
                setNotFoundMessage("This action could not be completed while offline.");
            } else {
                setNotFoundMessage("No trails found. Please try again.");
            }
            return;
        };
        setCoords(d.coords);
        setTrailData(d.data.data);
        setLoading(false);
    }

    const calculateDistance =
        (lat, lon) => {
            const lat1 = coords.lat;
            const lon1 = coords.lon;
            const lat2 = lat;
            const lon2 = lon;

            // black magic math via https://www.movable-type.co.uk/scripts/latlong.html
            const R = 6371e3;
            const φ1 = lat1 * Math.PI / 180;
            const φ2 = lat2 * Math.PI / 180;
            const Δφ = (lat2 - lat1) * Math.PI / 180;
            const Δλ = (lon2 - lon1) * Math.PI / 180;

            const a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            const d = R * c;

            return d * 0.000621371;
        }


    return (
        <Header>
            <div className='sm:max-w-5xl sm:m-auto font-Outfit'>
                <h1 className='pt-6 text-2xl text-center sm:text-4xl sm:text-center'>Search for bike trails nearby...</h1>

                <div className='flex p-6'>
                    <input
                        className='w-4/6 p-2 border-2 border-gray-300 rounded-md'
                        type='text'
                        placeholder='City/Address'
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                    />
                    <select
                        className='w-1/6 p-2 mx-2 text-sm border-2 border-gray-300 rounded-md'
                        value={radius}
                        onChange={(e) => setRadius(Number(e.target.value))}
                    >
                        {radiusOptions.map((option) => (
                            <option key={option} value={option}>
                                {option} miles
                            </option>
                        ))}
                    </select>
                    <button
                        onClick={handleSearch}
                        className="w-1/6 text-white rounded-lg shadow-lg bg-tj-turquoise"
                    >
                        GO
                    </button>
                </div>


                {loading && <Loading />}
                {!loading && trailData.length > 0 &&
                    <div className='px-4 mb-4 space-y-4'>
                        {trailData.sort(
                            (a, b) => calculateDistance(a.lat, a.lon) - calculateDistance(b.lat, b.lon)
                        ).map((trail, i) => (
                            <TrailCard
                                key={i}
                                data={trail}
                                distance={calculateDistance(trail.lat, trail.lon)}
                            />
                        ))
                        }
                    </div>
                }
                {notFound && <div className='text-center'>{notFoundMessage}</div>}

            </div>
        </Header >
    )
}

export default Search;