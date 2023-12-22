import React, { useState } from 'react';
import {Link} from 'react-router-dom';

function FriendRequestCard({
    requestUser, avatar
}) {
    const [accept, setAccept] = useState("Accept");
    const [decline, setDecline] = useState("Decline");
    const [status, setStatus] = useState("");
    const [declineBack, setDeclineBack] = useState("bg-red-600");
    const [acceptBack, setAcceptBack] = useState("bg-green-500");

    const handleResponse = async (responseStatus) => {
        if (status === "") {
            setStatus(responseStatus);
            if(responseStatus === 'accepted') {
                setAccept("Accepted");
                setDeclineBack("bg-red-200");

            }
            if(responseStatus === 'declined') {
                setDecline("Declined");
                setAcceptBack("bg-green-100");
            }
        }

        const res = await fetch(`/api/user/me`);
        const d = await res.json();
        const user1 = d.username;
        fetch(`/api/friendRequests/${requestUser}/request/${responseStatus}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                currentUser:user1
            })
        }).catch((error) => {
            console.error(error);
        });

        setTimeout(() => {
            window.location.reload();
        }, 2000);


    };

    return (
        <div className='flex p-4 text-xl bg-white border border-black rounded-xl sm:text-2xl' id='request-container'>
            <Link to={`/profile/${requestUser}`}>
            <img src={avatar} className='w-[80px] h-[80px] rounded-full mr-3' alt="user_profile" />
      </Link>
            <div className='self-start m-auto'>{requestUser} wants to be friends!</div>
            <div className='flex justify-end sm:hidden'>
                <button onClick={() => handleResponse("accepted")} className='mr-2 text-5xl text-green-500' disabled={status !== ""}>&#x2713;</button>
                <button onClick={() => handleResponse("declined")} className='text-5xl text-red-600' disabled={status !== ""}>X</button>
            </div>
            <div className='hidden sm:flex'>
                <button onClick={() => handleResponse("accepted")} className={`px-2 my-3 mr-2 text-2xl ${acceptBack} border-2 border-black rounded-lg hover:${acceptBack}`} disabled={status !== ""}>{accept}</button>
                <button onClick={() => handleResponse("declined")} className={`px-2 my-3 mr-2 text-2xl ${declineBack} border-2 border-black rounded-lg hover:${declineBack}`} disabled={status !== ""}>{decline}</button>
            </div>
        </div>
    );
}

export default FriendRequestCard;
