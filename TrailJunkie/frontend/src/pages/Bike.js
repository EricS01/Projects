import React from 'react';
import { useEffect, useState } from 'react';
import Header from '../components/Header';

function Bike() {

    const [ownProfile, setOwnProfile] = useState(false);
    const [name, setName] = React.useState('');
    const [year, setYear] = React.useState('');
    const [color, setColor] = React.useState('');
    const [make, setMake] = React.useState('');
    const [model, setModel] = React.useState('');
    const [image, setImage] = React.useState('');
    const [msgText, setMsgText] = React.useState('');
    const [msgColor, setMsgColor] = React.useState('');
    let isErr = false;

    useEffect(() => {
        const fetchEvents = async (username) => {
            try {
                const response = await fetch(`/api/bike/${username}`);
                const data = await response.json();
                setName(data.name);
                setYear(data.year);
                setColor(data.color);
                setMake(data.make);
                setModel(data.model);
                setImage(data.image);
            } catch (error) {
                console.error(error.message);
            }
        }

        const url = window.location.href;
        const lastIndexOfChar = url.lastIndexOf('/');
        const secondToLastIndexOfChar = url.lastIndexOf('/', lastIndexOfChar - 1);
        const username = url.substring(secondToLastIndexOfChar + 1, lastIndexOfChar);
        setOwnProfile(username === 'me');
        fetchEvents(username);
    }, []);
    const handleBike = () => {
        fetch('/api/bike', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, year, color, make, model, image }),
        }).then((response) => {
            if (response.status === 200) {
                console.log(response);
                setMsgColor("green");
                setMsgText("Bike updated successfully!");
            } else {
                isErr = true;
                return response.json();
            }
        }).then((errorResponse) => {
            if (isErr) {
                console.log(errorResponse.error);
                setMsgText(errorResponse.error);
                setMsgColor("red");
            }
        }).catch((err) => {
            console.error(err);
            alert('Error adding bike, please try again');
        });
    }

    return (
        <Header>
            <div className='sm:max-w-5xl sm:m-auto'>
                <div className='my-12 text-3xl text-center smtext-4xl lg:text-5xl sm:text-left'>Bike Make & Model</div>
                <div className='flex justify-center mb-5 sm:mx-2 sm:justify-normal sm:m-5'>
                    <img className='w-[250px] h-[250px] sm:mr-12' src={image} alt="bike" />
                    <div className='hidden w-3/5 grid-cols-3 gap-4 text-xl sm:grid'>
                        <div className='col-span-1 m-2'>Name:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={name} onChange={(e) => setName(e.target.value)} /></span></div>

                        <div className='col-span-1 m-2'>Year:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={year} pattern='[0-9]+' onChange={(e) => setYear(e.target.value)} /></span></div>

                        <div className='col-span-1 m-2'>Color:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={color} pattern='[a-zA-Z]+' onChange={(e) => setColor(e.target.value)} /></span></div>

                        <div className='col-span-1 m-2'>Make:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={make} onChange={(e) => setMake(e.target.value)} /></span></div>

                        <div className='col-span-1 m-2'>Model:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={model} onChange={(e) => setModel(e.target.value)} /></span></div>

                        <div className='col-span-1 m-2'>Image:</div>
                        <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={image} onChange={(e) => setImage(e.target.value)} /></span></div>


                        {
                                ownProfile && (
                                    <>
                                     <div className='w-full col-span-3 m-2'>
                            {msgText && <div className={`pl-2 text-${msgColor}-500 text-sm`}>{msgText}</div>}
                            <button className='block w-full h-12 p-4 mt-1 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise hover:text-white hover:bg-tj-turquoise md:text-xl' onClick={handleBike}>
                                Save
                            </button>
                        </div>
                                    </>
                                )
                        }           
                       
                    </div>
                </div>

                <div className='flex justify-center w-full sm:hidden'>
                    <div className='flex justify-center w-full text-2xl'>
                        <div className='grid w-3/5 grid-cols-3 gap-4'>
                            <div className='col-span-1 m-2'>Name:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={name} onChange={(e) => setName(e.target.value)} /></span></div>

                            <div className='col-span-1 m-2'>Year:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={year} onChange={(e) => setYear(e.target.value)} /></span></div>

                            <div className='col-span-1 m-2'>Color:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={color} onChange={(e) => setColor(e.target.value)} /></span></div>

                            <div className='col-span-1 m-2'>Make:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={make} onChange={(e) => setMake(e.target.value)} /></span></div>

                            <div className='col-span-1 m-2'>Model:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={model} onChange={(e) => setModel(e.target.value)} /></span></div>

                            <div className='col-span-1 m-2'>Image:</div>
                            <div className='col-span-2 m-2'><span className='p-2 border-2 border-black rounded-md'><input className='w-full bg-transparent border-none outline-none' readOnly={!ownProfile} type="text" name="" id="" placeholder={image} onChange={(e) => setImage(e.target.value)} /></span></div>

                            {
                                ownProfile && (
                                    <>
                                    <div className='w-full col-span-3 m-2'>
                                        {msgText && <div className={`pl-2 text-${msgColor}-500 text-sm`}>{msgText}</div>}
                                        <button className='block w-full h-12 p-4 mt-0 font-bold bg-white rounded-lg shadow-lg sm:text-lg text-tj-turquoise hover:text-white hover:bg-tj-turquoise md:text-xl' onClick={handleBike}>
                                            Save
                                        </button>
                                    </div>
                                    </>
                                )
                            }
                            

                        </div>
                    </div>
                </div>

            </div>
        </Header>
    )
}

export default Bike;