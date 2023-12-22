import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/Header';
import Loading from "../components/Loading";
import Friend from '../components/Friends/FriendRequestsCard';

function FriendRequest() {
    const [userData, setUserData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [notFound, setNotFound] = useState(false);

    const handleFriendRequests = async () => {
        setNotFound(false);
        setLoading(true);
        const res = await fetch(`/api/user/me`);
        const d = await res.json();
        const current = d.username;

        const reqRes = await fetch(`api/friendRequests/${current}`);
        const reqD = await reqRes.json();

        if ((reqRes.status !== 200 && reqRes.status !== 304) || reqD.length===0 ) {
            setLoading(false);
            setNotFound(true);
            setUserData([]);
            return;
        }

    
        setUserData(reqD);
        setLoading(false);
    };

    useEffect(() => {
        handleFriendRequests();
    }, []); 

    return (
        <Header>
            <div className='sm:max-w-5xl sm:m-auto font-Outfit'>
                <div className='flex mb-8 sm:mr-12 sm:justify-center sm:space-x-8'>

                    <div className="flex w-full h-12 mx-12 mt-2 sm:h-16 sm:mt-3">
                        <Link to="/friend-search" className='w-3/5 h-full'>
                            <button className='block w-full h-full font-bold bg-white border-r border-black rounded-tl-lg rounded-bl-lg shadow-lg text-tj-turquoise md:text-2xl sm:text-xl boldtext-xl hover:bg-tj-turquoise hover:text-white'>Search</button>
                        </Link>
                        <Link to="/requests" className='w-3/5 h-full rounded-full'>
                            <button className='block w-full h-full font-bold text-white rounded-tr-lg rounded-br-lg shadow-lg sm:text-xl bg-tj-dark-brown md:text-2xl hover:bg-tj-lightish-brown'>Requests</button>
                        </Link>

                    </div>

                </div>


                
                

                {loading &&  <Loading />}
                {!loading &&  userData.length > 0 &&
                    <div className='px-4 mb-4 space-y-4'>
                        {userData.map(requestUserData => (
                            <Friend
                                key={requestUserData.user1_username}
                                requestUser={requestUserData.user1_username}
                                avatar = {requestUserData.avatar}
                            />
                        ))}                     
                    </div>
                }
                {notFound && <div className='text-center'>No Friend Requests</div>}


           
            </div>
        </Header>

    );
}

export default FriendRequest;

        



