import React, { useMemo } from "react";
import { timeSinceDate } from "../../utils/dateCalculations";
import { Link } from 'react-router-dom';

function PersonalRatingCard({
    trailName,
    rating,
    trailUrl,
    thumbnail,
    comment,
    timestamp,
    brief
}) {
    const timeSince = useMemo(() => timeSinceDate(timestamp), [timestamp]);
    if (brief) {
        return (
            <BriefCard
                trailName={trailName}
                rating={rating}
                timestamp={timestamp}
            />
        )
    }

    return (
        <div className="w-full p-4 text-white border border-black shadow-lg rounded-2xl bg-tj-turquoise">
            <div className="inline-block w-full">
                <div className="w-full ">
                    <p className="text-base text-right sm:text-xl">
                        {timeSince}
                    </p>
                </div>
                <div className="flex flex-col mt-4 sm:flex-row">
                    <Link to={trailUrl} target="_blank" className="bg-red-500 contents">
                        <img src={thumbnail ? thumbnail : '/trail-placeholder.webp'} className="w-full h-full mb-4 duration-100 ease-out rounded-md shadow-md sm:h-[200px] sm:w-1/2 sm:mb-0 hover:scale-105 trasnition" />
                    </Link>

                    <div className="inline-block sm:w-3/4 sm:ml-2 sm:mr-8">
                        <h2 className="text-xl font-bold sm:text-3xl">
                            {trailName}
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
    );
}

function BriefCard({
    trailName,
    rating,
    timestamp }) {
    const timeSince = useMemo(() => timeSinceDate(timestamp), [timestamp]);
    return (
        <div className="flex justify-between w-full p-4 text-white border border-black shadow-lg rounded-2xl bg-tj-green">
            <p className="mr-4 text-left">
                You rated <span className="font-extrabold text-tj-light-brown">{trailName}</span>
                <span className="ml-1 text-xl text-yellow-400">
                    {Array(Number.parseInt(rating)).fill("★").map((star, index) => (
                        <span key={index}>{star}</span>
                    ))}
                </span>
            </p>
            <p>
                {timeSince}
            </p>
        </div>
    );
}

export default PersonalRatingCard;