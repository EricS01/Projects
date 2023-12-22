import {React, useState} from 'react';
import Header from '../components/Header';
import { Link , useLocation} from 'react-router-dom';
import StarRating from "../components/Rating/StarRating";


const difficultyColors = {
    "": "bg-gray-600",
    "Easiest": "bg-green-600",
    "Beginner": "bg-green-600",
    "Intermediate": "bg-yellow-600",
    "Advanced": "bg-red-600",
    "Expert": "bg-black"
}

function WriteAReview() {
    const location = useLocation();
    const { trailData } = location.state;
    const {id, url, description, directions, city, region, country, lat, lon,  features, rating, name, difficulty, length, thumbnail} = trailData;
    const trail_id = id;
    const [ratings, setRatings] = useState(0);
    const [comment, setComment] = useState('');
    const handleCommentChange = (event) => {
        setComment(event.target.value);
    };

    const handleAddReview = async () => {




        fetch(`/api/review`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( {ratings, id, comment,trail_id, city, country, description, difficulty, directions, features, lat, length, lon, name, rating, region, thumbnail, url}),

            }).then((response) => {
            
        }).catch((err) => {
            console.error(err);
            alert(err);
        });
    };
    

    
    return (
        <Header>
        <div className='sm:max-w-5xl sm:m-auto font-Outfit'>
          

            <div className='flex flex-col pt-6 pb-6 pl-3 pr-2'>

                <div className='flex flex-row pl-2 pr-3'>
                    <img
                        alt="trail image"
                        src={thumbnail ? thumbnail : `/trail-placeholder.webp`}
                        className='w-40 h-40 ml-4 mr-2 border-4 border-yellow-900 rounded-3xl'
                    />
                    {/* <img src='lake-johnson.jpg' alt='Trail Preview' width={100} height={100} className='w-1/3 border-4 border-yellow-900 rounded-3xl '/>    */}

                    <div className='flex flex-col pl-3 pr-3'>
                        <p className='flex justify-center pl-3 text-2xl font-bold sm:text-5xl font-Outfit text-tj-dark-brown'>{name}</p>
                        <div class='flex flex-row pt-2'>
                            <p className='pl-3 pr-16 text-lg font-bold justify-left sm:text-3xl font-Outfit text-tj-light-brown'>{length} miles</p>
                            <div className=''>
                                <p className={`text-lg bg-red-300 border border-red-700 rounded justify-left sm:text-3xl font-Outfit text-tj-black ${difficultyColors[difficulty]}`}>{difficulty ? difficulty : "Unknown"}</p>
                            </div>
                        </div>
                    </div>

                </div>
                <hr class="h-px my-8 bg-gray-200 border-0 dark:bg-gray-700" />

                <h2 className='pb-6 text-2xl text-center sm:text-4xl sm:text-center'>Review</h2>

                <h3 className='pb-4 text-xl text-left sm:text-3xl sm:text-left fs-1'>Rating:</h3>

                <div className="flex items-center justify-center pb-8">
                    <StarRating ratings={ratings} setRatings={setRatings}/>
                    
                </div>
    
                
                <h3 className='pb-2 text-xl text-left sm:text-3xl sm:text-left'>Comment:</h3>

                <div className='relative pt-6'>
                    <textarea onChange={handleCommentChange} id="review" name="message" className="w-full h-40 p-2 text-xl border border-gray-300 rounded-md sm:text-2xl focus:ring focus:ring-tj-turquoise focus:border-tj-turquoise" placeholder="How was your experience?"></textarea>                        
                </div>

                <div className='relative pt-6'>
                    <Link to='/timeline'>
                        <button onClick={handleAddReview} className='block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-turquoise hover:bg-tj-turquoise'>Post Review</button>
                    </Link>
                </div>

            </div>
        </div>
        </Header>
        
    )
}

export default WriteAReview;