import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function SignUp() {
    const [email, setEmail] = React.useState('');
    const [username, setUsername] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [message, setMessage] = useState(null);
    const [messageColor, setMessageColor] = useState("");

    const handleSignUp = () => {
        // regex valid email
        let emailRegex = /^[a-zA-Z0-9]+@(?:[a-zA-Z0-9]+\.)+[A-Za-z]+$/;
        if (!emailRegex.test(email)) {
            setMessageColor("red");
            setMessage("Invalid email");
            return;
        }

        fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, email, password }),
        }).then(async (response) => {
            if (response.status === 201) {
                setMessageColor("green");
                setMessage("Account created successfully!");
            } else {
                setMessageColor("red");
                let data = await response.json();
                setMessage(data.msg);
            }
        }).catch(async (err) => {
            console.log(err);
        });
    }

    return (
        <div className='sm:max-w-5xl sm:m-auto'>
            <Link to='/'>
                <h1 className='p-8 text-3xl font-bold sm:p-4 font-Outfit text-tj-turquoise'>Trail
                    <span className='text-tj-green'>Junkie</span>
                </h1>
            </Link>
            <div className='sm:flex mt-[-70px] sm:mt-0'>
                <img src='logo512.png' alt='logo' width={300} height={300} className='w-full mx-auto sm:w-1/2' />
                <div className='sm:mt-20'>
                    <span className='flex justify-center text-3xl font-bold sm:text-5xl sm:p-0 font-Outfit text-tj-dark-brown'>Create an Account</span>
                    <p className='p-8 -mt-8 text-xl font-bold text-center sm:text-2xl sm:mt-0 sm:p-0 font-Outfit text-tj-light-brown'>Join the fastest growing trail community today!</p>
                    <div className='hidden sm:flex sm:p-4 sm:-ml-4 sm:justify-start sm:space-x-8'>
                        <div className='relative w-full'>
                            <input type="text" id="floating_email" class="block w-full p-4 text-xl font-bold rounded-lg shadow-lg focus:outline-none focus:ring-0 peer" placeholder=""
                                onChange={(e) => setEmail(e.target.value)}
                            />
                            <label for="floating_email" class="absolute font-bold cursor-text text-xl text-gray-500  duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-gray-400 peer-focus:dark:text-gray-400 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 left-1">Email Address</label>
                        </div>
                    </div>
                    <div className='hidden sm:flex sm:p-4 sm:-ml-4 sm:justify-start sm:space-x-8'>
                        <div className='relative w-3/5'>
                            <input type="text" id="floating_user" class="block w-full p-4 text-xl font-bold rounded-lg shadow-lg focus:outline-none focus:ring-0 peer" placeholder=""
                                onChange={(e) => setUsername(e.target.value)}
                            />
                            <label for="floating_user" class="absolute font-bold cursor-text text-xl text-gray-500 duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-gray-400 peer-focus:dark:text-gray-400 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 left-1">Username</label>
                        </div>
                        <div className='relative w-3/5'>
                            <input type="password" id="floating_pass" class="block w-full p-4 text-xl font-bold rounded-lg shadow-lg focus:outline-none focus:ring-0  peer" placeholder=""
                                minLength={8}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                            <label for="floating_pass" class="absolute font-bold cursor-text text-xl text-gray-500 duration-300 transform -translate-y-4 scale-75 top-2 z-10 origin-[0] bg-white px-2 peer-focus:px-2 peer-focus:text-gray-400 peer-focus:dark:text-gray-400 peer-placeholder-shown:scale-100 peer-placeholder-shown:-translate-y-1/2 peer-placeholder-shown:top-1/2 peer-focus:top-2 peer-focus:scale-75 peer-focus:-translate-y-4 left-1">Password</label>
                        </div>
                    </div>
                    {message && <p className={`mb-1 -mt-2 font-bold text-center text-${messageColor}-500 hidden sm:block`}>{message}</p>}
                    <div className='hidden sm:flex sm:p-4 sm:-ml-4 sm:justify-start sm:space-x-8'>
                        <div className='relative w-full'>
                            <button className='block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-dark-brown hover:bg-tj-lightish-brown'
                                onClick={handleSignUp}
                            >Join Now</button>
                        </div>

                    </div>
                </div>
            </div>



            <div className='p-8 mx-auto -mt-8 sm:hidden'>
                <div className='mb-4 w-5/5' >
                    <input type="email" placeholder="Email" className='block w-full p-4 text-xl font-bold rounded-lg shadow-lg'
                        required
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div className='mb-4 w-5/5'>
                    <input type="text" placeholder="Username" className='block w-full p-4 text-xl font-bold rounded-lg shadow-lg'
                        required
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div className='mb-4 w-5/5'>
                    <input type="password" placeholder="Password" className='block w-full p-4 text-xl font-bold rounded-lg shadow-lg'
                        required
                        minLength={8}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                {message && <p className={`mb-1 -mt-2 font-bold text-center text-${messageColor}-500`}>{message}</p>}
                <button className='block w-full p-4 text-xl font-bold text-white rounded-lg shadow-lg bg-tj-dark-brown hover:bg-tj-lightish-brown'
                    onClick={handleSignUp}
                >Join Now</button>
            </div>
        </div>

    )
}

export default SignUp;