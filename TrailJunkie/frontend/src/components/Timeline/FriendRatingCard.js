import React, { useMemo, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { timeSinceDate } from "../../utils/dateCalculations";

function FriendRatingCard({
    userId,
    avatar,
    trailName,
    trailUrl,
    trailThumbnail,
    rating,
    comment,
    timestamp,
}) {

    const timeSince = useMemo(() => timeSinceDate(timestamp), [timestamp]);
    const [expanded, setExpanded] = useState(false);
    const [trailData, setTrailData] = useState({});
    const [addToFav, setAddToFav] = useState("Add to Favorites");
    const [addToPlan, setAddToPlan] = useState("Add to Plan-To-Ride");


    useEffect(() => {
        const fetchData = async () => {
          try {
            const trailNameEncode = encodeURIComponent(trailName);
            const response = await fetch(`/api/trails/${trailNameEncode}`);
            const data = await response.json();
            const modifiedData = {
                ...data[0],
                id: data[0].trail_id,
            };
            setTrailData(modifiedData);
        } catch (error) {
            console.error(error.message);
        };
    }
        fetchData();
        }, [trailName]);

    const handleAddFavorites = async () => {

        fetch(`/api/favorites`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                trail_id: trailData.trail_id,
                name: trailData.name,
                url: trailData.url,
                length: trailData.length,
                description: trailData.description,
                directions: trailData.directions,
                city: trailData.city,
                region: trailData.region,
                country: trailData.country,
                lat: trailData.lat,
                lon: trailData.lon,
                difficulty: trailData.difficulty,
                features: trailData.features,
                rating: trailData.rating,
                thumbnail: trailData.thumbnail,
                }),
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
            body: JSON.stringify({
                trail_id: trailData.trail_id,
                name: trailData.name,
                url: trailData.url,
                length: trailData.length,
                description: trailData.description,
                directions: trailData.directions,
                city: trailData.city,
                region: trailData.region,
                country: trailData.country,
                lat: trailData.lat,
                lon: trailData.lon,
                difficulty: trailData.difficulty,
                features: trailData.features,
                rating: trailData.rating,
                thumbnail: trailData.thumbnail,
                }),
        }).then((response) => {
            setAddToPlan("Added to Plan-To-Ride")

        }).catch((err) => {
            console.error(err);
            alert(err);
        });
    };

    return (
        <div className={`w-full p-4 text-white border border-black shadow-lg rounded-2xl bg-tj-turquoise hover:${expanded ? 'scale-100' : 'scale-105'}`}>

            <div
                className='hover:cursor-pointer'
                onClick={() => {
                    setExpanded(!expanded);
                }}
            >
                <div className="inline-block w-full">
                    <div className="flex justify-between w-full">
                        <img src={avatar} className="w-10 h-10 border border-black rounded-full shadow-lg sm:border-2 sm:w-14 sm:h-14" />
                        <p className="text-base sm:text-xl">
                            {timeSince}
                        </p>
                    </div>
                    <div className="flex flex-col mt-4 sm:flex-row">
                        <Link to={trailUrl} target="_blank" className="bg-red-500 contents">
                            <img src={trailThumbnail ? trailThumbnail : 'trail-placeholder.webp'} className="w-full h-full mb-4 duration-100 ease-out rounded-md shadow-md sm:h-[200px] sm:w-1/2 sm:mb-0 hover:scale-105 trasnition" />
                        </Link>

                        <div className="inline-block sm:w-3/4 sm:ml-2 sm:mr-8">
                            <h2 className="text-xl font-bold sm:text-3xl">
                                {userId} rated {trailName}
                            </h2>
                            <p className="text-base italic sm:text-lg text-neutral-200">
                                "{comment}"
                            </p>
                        </div>
                    </div>

                    <div class="float-right text-4xl sm:text-5xl text-yellow-500">
                        {Array(Number.parseInt(rating)).fill("★").map((star, index) => (
                            <span key={index}>{star}</span>
                        ))}
                    </div>

                </div>
            </div>
            {expanded && <>
                <div className='mt-2 border-t-2 border-gray-300'>
                    <div className='mt-2 italic'>{trailData.city}, {trailData.region}, {trailData.country}</div>

                    <div className='mt-2 font-bold'>Description:</div>
                    <div className='text-sm italic'>{trailData.description}</div>
                </div>

                <div className='flex space-x-4 text-sm justify-evenly'>
                    <button onClick={handleAddFavorites} className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg bg-tj-light-brown hover:bg-tj-lightish-brown'>
                        {addToFav}
                    </button>
                    <button onClick={handleAddPlan} className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg hover:bg-tj-lightish-brown bg-tj-light-brown'>
                        {addToPlan}
                    </button>

                    <div className='w-1/3 '>
                    <Link to={'/write-review'} state= { {trailData: trailData}}>
                        <button  className='w-full p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg hover:bg-tj-lightish-brown bg-tj-light-brown'>
                            Review
                        </button>
                    </Link>
                    </div>
                   
                </div>
            </>
            }




        </div>
    );
}

export default FriendRatingCard;