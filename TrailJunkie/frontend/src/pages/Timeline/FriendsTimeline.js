import React, { useState, useEffect } from 'react';
import FriendRatingCard from '../../components/Timeline/FriendRatingCard';
import EventCard from '../../components/Timeline/EventCard';
import { Link } from 'react-router-dom';
import Loading from '../../components/Loading';
import { offlineCode } from '../../utils/constants';
import Offline from '../Offline';

function FriendsTimeline() {
    const [loading, setLoading] = useState(true);
    const [events, setEvents] = useState([]);
    const [offline, setOffline] = useState(false);

    useEffect(() => {
        const fetchEvents = async () => {
            setLoading(true);
            try {
                const response = await fetch('/api/timeLine/friends');
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

        fetchEvents();
    }, []);

    return (
        <div className='sm:max-w-5xl sm:m-auto font-Outfit'>
            <div className='flex justify-between p-6'>
                <h1 className='text-3xl'>New From Friends</h1>
                <Link to='/friend-search'>
                    <svg className='w-8 hover:cursor-pointer' viewBox="0 0 64 64" xmlns="http://www.w3.org/2000/svg" stroke-width="3" stroke="#000000" fill="none"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"><circle cx="29.22" cy="16.28" r="11.14"></circle><path d="M41.32,35.69c-2.69-1.95-8.34-3.25-12.1-3.25h0A22.55,22.55,0,0,0,6.67,55h29.9"></path><circle cx="45.38" cy="46.92" r="11.94"></circle><line x1="45.98" y1="39.8" x2="45.98" y2="53.8"></line><line x1="38.98" y1="46.8" x2="52.98" y2="46.8"></line></g></svg>
                </Link>
            </div>

            {offline && (
                        <Offline />
            )}
            {loading && <Loading />}
            {!loading && (
                <div className='flex flex-col justify-between p-6 space-y-2'>
                    {events.map(event => {
                        switch (event.eventType) {
                            case 'review':
                                return <FriendRatingCard key={event.Id} {...event} />;
                            case 'favorite':
                                return <EventCard key={event.Id} {...event} eventTarget={"Favorites"} friend/>;
                            case 'planToRide':
                                return <EventCard key={event.Id} {...event} eventTarget={"Plan to Ride"} friend/>;
                            default:
                                return <EventCard key={event.Id} {...event} />;
                        }
                    })}
                </div>
            )}
        </div>
    )
}

export default FriendsTimeline;