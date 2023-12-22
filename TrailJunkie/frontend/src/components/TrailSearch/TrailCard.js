import React, { useState } from 'react';
import { Link } from 'react-router-dom';


const difficultyColors = {
    "": "bg-gray-600",
    "Easiest": "bg-green-600",
    "Beginner": "bg-green-600",
    "Intermediate": "bg-yellow-600",
    "Advanced": "bg-red-600",
    "Expert": "bg-black"
}

function TrailCard(
    { data, distance }
) {
    const [expanded, setExpanded] = useState(false);
    
    const [addToFav, setAddToFav] = useState("Add to Favorites");
    const [addToPlan, setAddToPlan] = useState("Add to Plan-To-Ride");



    const {id, city, country, description, difficulty, directions, features, lat, length, lon, name, rating, region, thumbnail, url} = data;
    const trail_id = id;

    const handleAddFavorites = async () => {

        fetch(`/api/favorites`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({trail_id, name, url, length, description, directions, city, region, country, lat, lon, difficulty, features, rating, thumbnail }),
        }).then((response) => {
            setAddToFav("Added to Favorites")
        }).catch((err) => {
            console.error(err);
            alert(err);
        });
    };

    const handleAddPlan = async () => {

        fetch(`/api/plantoride`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({trail_id, name, url, length, description, directions, city, region, country, lat, lon, difficulty, features, rating, thumbnail }),
        }).then((response) => {
            setAddToPlan("Added to Plan-To-Ride")

        }).catch((err) => {
            console.error(err);
            alert(err);
        });
    };


    return (
        <div className={`p-2 transition duration-200 ease-in-out rounded-lg bg-tj-turquoise text-neutral-100 hover:${expanded ? 'scale-100' : 'scale-105'}`}
        >
            <div
                className='hover:cursor-pointer'
                onClick={() => {
                    setExpanded(!expanded);
                }}
            >
                <div className='flex justify-between'>
                    <div className='block'>
                        <div className='font-bold'>{name}</div>
                        <div className='text-neutral-200'>{parseInt(length)} miles</div>
                    </div>

                    <img
                        alt="trail image"
                        src={thumbnail ? thumbnail : `/trail-placeholder.webp`}
                        className='w-24 h-24 pt-2 ml-4 mr-2 rounded-md'
                    />
                </div>

                <div className='flex justify-between mt-4'>
                    <div className={`mr-1 text-sm p-1 rounded-md text-neutral-100 ${difficultyColors[difficulty]}`}>
                        {difficulty ? difficulty : "Unknown"}
                    </div>
                    <div className='mr-1 text-sm'>
                        <span className='font-bold '>
                            {distance.toFixed(2) < 5000.03 ?
                                distance.toFixed(2) :
                                "Unknown"}
                        </span> miles away
                    </div>
                </div>
            </div>

            {expanded && <>
                <div className='mt-2 border-t-2 border-gray-300'>
                    <div className='mt-2 italic'>{city}, {region}, {country}</div>

                    <div className='mt-2 font-bold'>Description:</div>
                    <div className='text-sm italic'>{data.description}</div>
                </div>

                <div className='flex space-x-4 text-sm justify-evenly'>
                    <button onClick={handleAddFavorites} className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg bg-tj-light-brown hover:bg-tj-lightish-brown'>
                        {addToFav}
                    </button>
                    <button onClick={handleAddPlan} className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg hover:bg-tj-lightish-brown bg-tj-light-brown'>
                        {addToPlan}
                    </button>

                    <div className='w-1/3 '>
                    <Link to={'/write-review'} state= { {trailData: data}}>
                        <button  className='w-full p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg hover:bg-tj-lightish-brown bg-tj-light-brown'>
                            Review
                        </button>
                    </Link>
                    </div>
                   
                </div>
            </>
            }
        </div>
    )
}

export default TrailCard;