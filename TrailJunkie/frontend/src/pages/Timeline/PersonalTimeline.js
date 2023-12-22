import React, { useEffect, useState } from 'react';
import EventCard from '../../components/Timeline/EventCard';
import PersonalRatingCard from '../../components/Timeline/PersonalRatingCard';
import Loading from '../../components/Loading';
import { offlineCode } from '../../utils/constants';
import Offline from '../Offline';

function PersonalTimeline() {
    const [loading, setLoading] = useState(true);
    const [events, setEvents] = useState([]);
    const [offline, setOffline] = useState(false);

    useEffect(() => {
        const fetchEvents = async () => {
            setLoading(true);
            try {
                const response = await fetch('/api/timeLine/personal');
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
                <h1 className='text-3xl'>Your Activity</h1>
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
                                return <PersonalRatingCard key={event.eventId} {...event} brief />;
                            case 'favorite':
                                return <EventCard key={event.eventId} {...event} eventTarget={"Favorites"} userId={"You"} />;
                            case 'planToRide':
                                return <EventCard key={event.eventId} {...event} eventTarget={"Plan to Ride"} userId={"You"} />;
                            default:
                                return <EventCard key={event.eventId} {...event} />;
                        }
                    })}
                </div>
            )}
        </div>
    )
}

export default PersonalTimeline;