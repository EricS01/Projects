import React, { useState, useEffect } from 'react';

const StarRating = ({ ratings, setRatings }) => {
  const [hover, setHover] = useState(0);

  const handleRatingClick = (ratingValue) => {
    setRatings(ratingValue);
  };

  useEffect(() => {
    setHover(ratings);
  }, [ratings]);

  return (
    <div className="flex">
      {[...Array(5)].map((star, i) => {
        const ratingValue = i + 1;

        return (
          <button
            key={i}
            className={`cursor-pointer text-6xl ${
              ratingValue <= (hover || ratings)
                ? 'text-yellow-500'
                : 'text-gray-300'
            }`}
            onClick={() => handleRatingClick(ratingValue)}
            onMouseEnter={() => setHover(ratingValue)}
            onMouseLeave={() => setHover(0)}
          >
            ★
          </button>
        );
      })}
    </div>
  );
};

export default StarRating;
