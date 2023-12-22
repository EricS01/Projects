import React, { useState, useMemo, useEffect } from "react";
import { timeSinceDate } from "../../utils/dateCalculations";
import { Link } from "react-router-dom";

function EventCard({
    userId,
    avatar,
    trailName,
    eventTarget,
    timestamp,
    friend
}) {
    const timeSince = useMemo(() => timeSinceDate(timestamp), [timestamp]);
    let margin = "";
    if (friend) {
        margin = "mt-4";
    }

    const [expanded, setExpanded] = useState(false);
    const [trailData, setTrailData] = useState({});
    const [addToFav, setAddToFav] = useState("Add to Favorites");
    const [addToPlan, setAddToPlan] = useState("Add to Plan-To-Ride");

    


    useEffect(() => {
        const fetchData = async () => {
          try {
            const trailNameEncode = encodeURIComponent(trailName);
            console.log(trailNameEncode);
            const response = await fetch(`/api/trails/${trailNameEncode}`);
            const data = await response.json();
            setTrailData(data[0]);
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
        <div className={`flex flex-col justify-between w-full p-4 text-white border border-black shadow-lg rounded-2xl bg-tj-green hover:${expanded ? 'scale-100' : 'scale-105'}`}>
            <div className='flex justify-between w-full hover:cursor-pointer'
                onClick={() => {
                    setExpanded(!expanded);
                }}
            >
                <p className="flex mr-4 text-center">
                    <p className="w-auto min-w-max">
                        {friend && <Link to={`/profile/${userId}`} target="_blank"><img src={avatar} className="w-10 h-10 mr-2 border border-black rounded-full shadow-lg sm:mr-3 sm:border-2 sm:w-14 sm:h-14" /></Link>}
                    </p>

                    <p className={`sm:${margin}`}>
                        {userId} added <span className="font-extrabold text-tj-light-brown">{trailName}</span> to <span className="italic">{eventTarget}</span>
                    </p>


                </p>

                <p >
                    {timeSince}
                </p>

            </div>

            {expanded && trailData && <>
                <div className='mt-2 '>
                    <div className='mt-2 italic'>{trailData.city}, {trailData.region}, {trailData.country}</div>

                    <div className='mt-2 font-bold'>Description:</div>
                    <div className='text-sm italic'>{trailData.description}</div>
                </div>

                <div className='flex space-x-4 text-sm justify-evenly'>
                    <button  onClick={handleAddFavorites}  className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg bg-tj-light-brown hover:bg-tj-lightish-brown'>
                        {addToFav}
                    </button>
                    <button  onClick={handleAddPlan}  className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg hover:bg-tj-lightish-brown bg-tj-light-brown'>
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

export default EventCard;