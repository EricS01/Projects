import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';
import Loading from '../components/Loading';

function DesktopHeader({ children, user, timeline = 'none', setTimeline = () => { } }) {

    return (
        <div>
            <nav className='flex items-center justify-between p-4 rounded-b-sm sm:p-6 bg-tj-lightish-brown'>
                <Link to='/'>
                    <h1 className='text-2xl font-bold sm:text-3xl font-Outfit text-tj-turquoise'>
                        Trail<span className='text-tj-green'>Junkie</span>
                    </h1>
                </Link>

                <div className='items-center hidden space-x-6 sm:flex'>
                    <Link to='/search' className='transition transform hover:scale-105'>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 50 50" className='w-8 h-8 text-white sm:w-10 sm:h-10'>
                            <path d="M 21 3 C 11.601563 3 4 10.601563 4 20 C 4 29.398438 11.601563 37 21 37 C 24.355469 37 27.460938 36.015625 30.09375 34.34375 L 42.375 46.625 L 46.625 42.375 L 34.5 30.28125 C 36.679688 27.421875 38 23.878906 38 20 C 38 10.601563 30.398438 3 21 3 Z M 21 7 C 28.199219 7 34 12.800781 34 20 C 34 27.199219 28.199219 33 21 33 C 13.800781 33 8 27.199219 8 20 C 8 12.800781 13.800781 7 21 7 Z"></path>
                        </svg>
                    </Link>

                    {timeline !== 'none' ? (
                        <div className="flex space-x-4">
                            <button
                                onClick={() => setTimeline('friends')}
                                className={`py-2 px-4 border-2 border-black rounded-lg ${timeline === 'friends' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                            >
                                Friends
                            </button>
                            <button
                                onClick={() => setTimeline('personal')}
                                className={`py-2 px-4 border-2 border-black rounded-lg ${timeline === 'personal' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                            >
                                You
                            </button>
                        </div>
                    ) : (
                        <div className="flex space-x-4">
                            <Link to="/timeline/friends" className='px-4 py-2 text-white border-2 border-black rounded-lg hover:bg-white hover:text-tj-dark-brown'>
                                Friends
                            </Link>
                            <Link to="/timeline/personal" className='px-4 py-2 text-white border-2 border-black rounded-lg hover:bg-white hover:text-tj-dark-brown'>
                                You
                            </Link>
                        </div>
                    )}

                    <Link to='/profile/me' className='ml-4'>
                        <img src={user.avatar} alt='pfp' className='w-12 h-12 transition transform rounded-full shadow-lg hover:scale-105' />
                    </Link>
                </div>
            </nav>
            {children}
        </div>
    )
}

function MobileHeader({ children, user, timeline = 'none', setTimeline = () => { } }) {
    return (
        <div>
            <nav className='flex flex-wrap items-center p-6 rounded-b-sm justify-evenly sm:justify-evenly sm:space-x-12 bg-tj-lightish-brown'>
                <h1 className='hidden text-3xl font-bold sm:text-left sm:block font-Outfit text-tj-turquoise'>Trail
                    <span className='text-tj-green'>Junkie</span>
                </h1>
                <div className='flex items-center flex-shrink-0 mr-6 text-white transition duration-150 ease-in hover:scale-105'>
                    <Link to='/search'>
                        <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" viewBox="0 0 50 50" className='w-10 h-10'>
                            <path d="M 21 3 C 11.601563 3 4 10.601563 4 20 C 4 29.398438 11.601563 37 21 37 C 24.355469 37 27.460938 36.015625 30.09375 34.34375 L 42.375 46.625 L 46.625 42.375 L 34.5 30.28125 C 36.679688 27.421875 38 23.878906 38 20 C 38 10.601563 30.398438 3 21 3 Z M 21 7 C 28.199219 7 34 12.800781 34 20 C 34 27.199219 28.199219 33 21 33 C 13.800781 33 8 27.199219 8 20 C 8 12.800781 13.800781 7 21 7 Z"></path>
                        </svg>
                    </Link>
                </div>

                {timeline !== 'none' ? (
                    <>
                        <div className="flex">
                            <button
                                onClick={() => setTimeline('friends')}
                                className={`py-2 w-3/5 px-4 rounded-l-lg border-l-2 border-r-2 border-y-2 border-black ${timeline === 'friends' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                            >
                                Friends
                            </button>
                            <button
                                onClick={() => setTimeline('personal')}
                                className={`py-2 w-2/5 px-4 rounded-r-lg border-r-2 border-y-2 border-black ${timeline === 'personal' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                            >
                                You
                            </button>
                        </div>
                    </>) : (
                    <>
                        <div className="flex">
                            <Link to="/timeline/friends" className='w-3/5'>
                                <button
                                    className={`py-2 w-full px-4 rounded-l-lg border-l-2 border-r-2 border-y-2 border-black ${timeline === 'friends' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                                >
                                    Friends
                                </button>
                            </Link>
                            <Link to="/timeline/personal" className='w-2/5'>
                                <button
                                    className={`py-2 w-full px-4 rounded-r-lg border-r-2 border-y-2 border-black ${timeline === 'personal' ? 'bg-white text-tj-dark-brown' : 'text-white'}`}
                                >
                                    You
                                </button>
                            </Link>
                        </div>
                    </>)}

                <Link to='/profile/me'>
                    <div className='flex items-center ml-6 text-white transition duration-150 ease-in shadow-2xl hover:scale-105 sm:ml-4'>
                        <img src={user.avatar} alt='pfp' className='w-12 h-12 shadow-2xl rounded-3xl' />
                    </div>
                </Link>


            </nav>
            {children}
        </div>
    )
}

function Header({ children, timeline = 'none', setTimeline = () => { } }) {
    const [mobile, setMobile] = useState(window.innerWidth < 640);
    const { isAuthenticated, isLoading, user } = useAuth();

    if (!isLoading && !isAuthenticated) {
        window.location.href = '/login';
    }

    window.addEventListener('resize', () => {
        setMobile(window.innerWidth < 640);
    });

    if (isLoading) {
        return <Loading />
    }

    return (
        <div>
            {mobile ? (
                <MobileHeader timeline={timeline} setTimeline={setTimeline} user={user}>
                    {children}
                </MobileHeader>
            ) : (
                <DesktopHeader timeline={timeline} setTimeline={setTimeline} user={user}>
                    {children}
                </DesktopHeader>
            )}
        </div>
    )
}

export default Header;