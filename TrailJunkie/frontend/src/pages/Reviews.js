import React, { useEffect, useState } from 'react';
import Header from '../components/Header';
import Loading from '../components/Loading';
import PersonalRatingCard from '../components/Timeline/PersonalRatingCard';
import { offlineCode } from '../utils/constants';
import Offline from '../pages/Offline';

function Reviews() {
    const [ownProfile, setOwnProfile] = useState(false);
    const [loading, setLoading] = useState(true);
    const [events, setEvents] = useState([]);
    const [currentUser, setCurrentUser] = useState("");
    const [offline, setOffline] = useState(false);

    useEffect(() => {
        const fetchReviews = async (username) => {
            setLoading(true);
            try {
                const response = await fetch(`/api/review/${username}`);
                const data = await response.json();
                if (response.status === offlineCode) {
                    setOffline(true);
                }
                const sortedData = data.sort((b, a) => new Date(a.timestamp) - new Date(b.timestamp));
                setEvents(sortedData);
            } catch (error) {
                console.error(error.message);
            }
            setLoading(false);
        }

        const url = window.location.href;
        const lastIndexOfChar = url.lastIndexOf('/');
        const secondToLastIndexOfChar = url.lastIndexOf('/', lastIndexOfChar - 1);
        const username = url.substring(secondToLastIndexOfChar + 1, lastIndexOfChar);
        setOwnProfile(username === 'me');
        setCurrentUser(username === 'me' ? 'My' : (username + '\'s'));
        fetchReviews(username);
    }, []);


    return (
        <Header>
            <div className='sm:max-w-5xl sm:m-auto font-Outfit'>
                <div className='flex flex-col pt-6 pb-6 pl-3 pr-2'>

                    <h1 className='pt-6 pb-6 text-2xl text-center sm:text-4xl sm:text-center'>{currentUser} Reviews</h1>

                    {offline && (
                        <Offline />
                    )}
                    {loading && <Loading />}
                    {!loading && (
                        <div className='flex flex-col justify-between p-6 space-y-2'>
                            {events.map(event => {
                                return <PersonalRatingCard key={event.Id} {...event} />;
                            })}
                        </div>
                    )}
                </div>
            </div>
        </Header>
    );
}

export default Reviews;
