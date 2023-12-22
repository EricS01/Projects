import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../../components/Header';
import FriendsTimeline from './FriendsTimeline';
import PersonalTimeline from './PersonalTimeline';

function Timeline() {
    const { type } = useParams();
    const [timeline, setTimeline] = useState(type || 'friends');

    // clear the url after initial render
    if (type && type !== timeline) {
        window.history.replaceState(null, null, '/timeline');
    }

    return (
        <Header setTimeline={setTimeline} timeline={timeline}>
            {timeline === 'friends' ? <FriendsTimeline /> : <PersonalTimeline />}
        </Header>
    )
}

export default Timeline;