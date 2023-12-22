import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Header from "../components/Header";
import Loading from "../components/Loading";

function Profile() {
    const [ownProfile, setOwnProfile] = useState(false);
    const [loading, setLoading] = useState(true);
    const [data, setData] = useState({});

    const handleLogOut = () => {
        fetch('/api/auth/logout', {
            method: 'DELETE',
        }).then((response) => {
            if (response.status === 200) {
                window.location.href = '/';
            } else {
                alert('Error logging out, please try again');
            }
        }).catch((err) => {
            console.error(err);
            alert('Error logging out, please try again');
        });
    }

    useEffect(() => {
        const fetchUserData = async (username) => {
            setLoading(true);
            try {
                const response = await fetch(`/api/user/${username}`);
                if (response.status === 404) {
                    window.location.href = '/404';
                    return;
                }
                const data = await response.json();
                setData(data);
            } catch (error) {
                console.error(error.message);
            }
            setLoading(false);
        }

        // get url parameter for username
        const url = window.location.href;
        const username = url.substring(url.lastIndexOf('/') + 1);
        setOwnProfile(username === 'me');
        fetchUserData(username);
    }, []);

    return (
        <Header>
            {loading && <Loading />}
            {!loading && (
                <div>
                    <div className='px-12 pt-12 sm:hidden'>
                        <img src={data.avatar} width={100} height={100} alt="" className="w-full rounded-full shadow-xl" />
                        <div className='text-2xl font-extrabold text-center text-tj-dark-brown sm:hidden'>{data.username}</div>
                        <p className='italic text-center'>
                            Member since {new Date(data.creationDate).toDateString()}
                        </p>
                    </div>

                    <div className='px-4 pt-4 mb-4 sm:hidden'>
                        <Link to="bike">
                            <button className='block w-full h-12 p-4 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                Bike: {data.bike ? data.bike.model : 'None'}
                            </button>
                        </Link>
                        <Link to="reviews">
                            <button className='block w-full h-12 p-4 mt-4 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                Reviews
                            </button>
                        </Link>
                        <Link to="plan-to-ride">
                            <button className='block w-full h-12 p-4 mt-4 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                Plan-to-Ride List
                            </button>
                        </Link>
                        <Link to="favorites">
                            <button className='block w-full h-12 p-4 mt-4 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                Favorites
                            </button>
                        </Link>
                        {
                            ownProfile && (
                                <>
                                    <Link to="settings">
                                        <button className='block w-full h-12 p-4 mt-4 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise hover:text-white hover:bg-tj-turquoise md:text-xl'>
                                            Settings
                                        </button>
                                    </Link>
                                    <button className='block w-full h-12 p-4 mt-4 font-bold text-white bg-red-800 rounded-lg shadow-lg sm:text-lg hover:text-white md:text-xl'
                                        onClick={() => handleLogOut()}
                                    >
                                        Log Out
                                    </button>
                                </>
                            )
                        }
                    </div>

                    <div className='justify-center hidden sm:mt-12 sm:flex sm:max-w-7xl sm:m-auto'>
                        <div>
                            <img src={data.avatar} width={100} height={100} alt="profile" className="shadow-lg rounded-full w-[350px] h-[350px]" />
                            <div className='mt-1 text-3xl font-extrabold text-center text-tj-dark-brown'>
                                {data.username}
                            </div>
                            <p className='italic text-center'>
                                Member since {new Date(data.creationDate).toDateString()}
                            </p>
                        </div>
                        <div className='grid w-3/5 grid-cols-2 grid-rows-4 p-8 mt-6'>
                            <Link to="bike">
                                <button className='block w-full p-4 font-bold bg-white rounded-lg shadow-lg h-2/3 sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                    Bike: {data.bike ? data.bike.model : 'None'}
                                </button>
                            </Link>
                            <Link to="reviews" className=''>
                                <button className='block w-full p-4 mx-2 font-bold bg-white rounded-lg shadow-lg h-2/3 sm:text-lg text-tj-turquoise hover:text-white hover:bg-tj-turquoise md:text-xl'>
                                    Reviews
                                </button>
                            </Link>
                            <Link to="plan-to-ride">
                                <button className='block w-full p-4 font-bold bg-white rounded-lg shadow-lg h-2/3 sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                    Plan-to-Ride List
                                </button>
                            </Link>
                            <Link to="favorites">
                                <button className='block w-full p-4 mx-2 font-bold bg-white rounded-lg shadow-lg h-2/3 sm:text-lg text-tj-turquoise md:text-xl hover:text-white hover:bg-tj-turquoise'>
                                    Favorites
                                </button>
                            </Link>
                            {
                                ownProfile && (
                                    <>
                                        <Link to="settings" className='col-span-2'>
                                            <button className='block w-full mx-2 font-bold bg-white rounded-lg shadow-lg h-2/3 sm:text-lg text-tj-turquoise hover:text-white hover:bg-tj-turquoise md:text-xl'>
                                                Settings
                                            </button>
                                        </Link>
                                        <button className='block w-full col-span-2 mx-2 font-bold text-white bg-red-800 rounded-lg shadow-lg h-2/3 sm:text-lg hover:text-white md:text-xl'
                                            onClick={() => handleLogOut()}
                                        >
                                            Log Out
                                        </button>
                                    </>
                                )
                            }
                        </div>


                    </div>
                </div>
            )}
        </Header>
    )
}

export default Profile;