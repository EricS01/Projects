import React, { useState , useMemo} from 'react';
import { timeSinceDate } from "../../utils/dateCalculations";


const difficultyColors = {
    "": "bg-gray-600",
    "Easiest": "bg-green-600",
    "Beginner": "bg-green-600",
    "Intermediate": "bg-yellow-600",
    "Advanced": "bg-red-600",
    "Expert": "bg-black"
}

function PlanToRideCard(
    { data, ownProfile }
) {
    const {timestamp, name, difficulty, length, thumbnail} = data;
    const timeSince = useMemo(() => timeSinceDate(timestamp), [timestamp]);
    const [expanded, setExpanded] = useState(false);
    const [removePlan, setRemovePlan] = useState("Remove From Plan-To-Ride");


    const handleRemovePlan = async () => {
        fetch(`/api/plantoride/${data.plan_to_ride_id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
        }).catch((err) => {
            console.error(err);
            alert(err);
        });
        setRemovePlan("Removed");
        setTimeout(() => {
            window.location.reload();
        }, 2000);

    };

    return (
        <div className={`p-2 transition duration-200 ease-in-out rounded-lg bg-tj-turquoise text-neutral-100 hover:scale-105`}>
            <div className='hover:cursor-pointer'
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
                        {timeSince} ago
                        </span> 
                    </div>
                </div>
            </div>

            {expanded && ownProfile && <>
                

                <div className='mt-2 border-t-2 border-gray-300 flex space-x-4 text-sm justify-evenly'>
                    <button onClick={handleRemovePlan}  className='w-1/3 p-2 mt-2 text-white align-middle transition duration-200 rounded-lg shadow-lg bg-tj-light-brown hover:bg-tj-lightish-brown'>
                        {removePlan}
                    </button>
                </div>
            </>
            }

        </div>
    )
}

export default PlanToRideCard;