import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../Auth/AuthProvider';
import Loading from '../components/Loading';

function Landing() {
    const { isAuthenticated, isLoading } = useAuth();

    if (isLoading) return (
        <Loading />
    )

    if (isAuthenticated && !isLoading) {
        window.location.href = '/timeline';
    }

    return (
        <div className='sm:max-w-5xl sm:m-auto'>
            <h1 className='p-8 text-3xl font-bold sm:p-4 font-Outfit text-tj-turquoise'>Trail
                <span className='text-tj-green'>Junkie</span>
            </h1>
            <div className='sm:flex'>
                <img src='logo512.png' alt='logo' width={300} height={300} className='w-full mx-auto sm:w-1/2' />
                <div className='sm:mt-32'>
                    <span className='p-8 text-3xl font-bold sm:text-5xl sm:p-0 font-Outfit text-tj-dark-brown'>Find your next trail</span>
                    <p className='p-8 -mt-8 text-xl font-bold sm:text-2xl sm:mt-0 sm:p-0 font-Outfit text-tj-light-brown'>Discover the thrill of the outdoors, one trail at a time.</p>
                    <div className='hidden sm:flex sm:p-8 sm:-ml-8 sm:justify-start sm:space-x-8'>
                        <Link to='/login' className='w-3/5'>
                            <button className='block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-dark-brown hover:bg-tj-lightish-brown'>Log In</button>
                        </Link>
                        <Link to='/signup' className='w-2/5'>
                            <button className='block w-full p-4 text-xl font-bold bg-white rounded-lg shadow-lg text-tj-turquoise hover:bg-tj-turquoise hover:text-white'>Get Started</button>
                        </Link>
                    </div>
                </div>
            </div>

            <div className='p-8 mx-auto -mt-8 sm:hidden'>
                <Link to='/login'>
                    <button className='block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-dark-brown hover:bg-tj-lightish-brown'>Log In</button>
                </Link>
                <Link to='/signup'>
                    <button className='block w-full p-4 mt-2 text-xl font-bold bg-white rounded-lg shadow-lg text-tj-turquoise hover:bg-tj-turquoise hover:text-white'>Get Started</button>
                </Link>
            </div>
        </div>
    )
}

export default Landing;