<h1 align="center">TrailJunkie</h1>

<h2 align="center">Final Project</h2>

<h4 align="center">John Cirincione, Cade Cuddy, Eric Samuel</h4>

_To access the app, visit [this link](https://csc342-502.csc.ncsu.edu/) if you're not on a NCSU network/VPN, or [this link](http://csc342-502-host.csc.ncsu.edu/) if you are. **You can login by pressing the login button without credentials.**_

#### What's done:

For final milestone, we now have offline functionality that allows for a fluid user flow when disconnected from the internet. With the use of service workers and the Cache API, visited timelines and personal event pages will remain accessible when disconnected. During this milestone we expanded our existing manifest to allow for a more comprehensive PWA when installed. We were also able to achieve some "stretch goals" this milestone such as the ability to view the profile / metadata of users that you're friends with. This really helped us sell the "social media" vision that TrailJunkie is trying to accomplish. We utilize a persistent database to read from and write data to. As a result, all of our endpoints are now returning _real_ data rather than mock data. Our user account & authentication system has now been implemented as well with the use of JWTs. On the frontend side of things, all unimplemented wireframe views from Milestone 1 and 2 now have concrete implementations and dynamically change with the data they interaction with (if any).

#### What's not done:

All intended behavior and functionality is implemented. There are no known bugs or issues currently in the application.

#### Authentication:

Our authentication approach is very straightforward. Upon account creation, hashed user passwords and their salts are stored in the database. When an existing user goes to login, the server creates a JWT with a playload containing non-sensitive user metadata and signs it with the server's secret key. This JWT is sent back to the user via a cookie in the response where it persists in the user's browser (not a session cookie). Both the token and the cookie expire after an hour. This token is then read by the authentication middleware that wraps all of our privileged backend endpoints, ensuring only authenticated users are able to make such requests. On the frontend, a wrapper component wraps all privileged routes and redirects back to the sign-in page whenever a non-authenticated user tries to access a privileged frontend route.

#### Caching Strategy:
As the user browses the application online, the response of every GET request made is cached. If a new request from the same endpoint ever happens while online (i.e. a timeline updating with new events), the most recent response from that endpoint will be cached. There are some exceptions to our GET request caching: trail searches and friend request queries. Both of these operations use GET requests with query parameters, making them "dynamic" in such a way that not all can be cached/accounted for. As such, they're not cached.

All transformative HTTP methods like POST/PUT/DELETE are not cached, and any attempts to make such a request offline will return a uniform status 503 error: "This action could not be completed while offline.", letting the user know they're offline and can't make these requests.

#### Database ER Diagram

![](../Milestone2/342_erd.png)

### Pages and their Implementation Status

| Page                | Status | Wireframe | Offline Functionality |
| ------------------- | ------ | -------------------------------------------------------- | ------ |
| Landing Page        | ✅     | [Wireframe](../Proposal/Wireframes/landing.JPG)          |  Will load|
| Signup              | ✅     | [Wireframe](../Proposal/Wireframes/signup.JPG)           | |
| Login               | ✅     | [Wireframe](../Proposal/Wireframes/login.JPG)            | |
| Friend Timeline     | ✅     | [Wireframe](../Proposal/Wireframes/timeline.JPG)         | Will load data if browsed online |
| Personal Timeline   | ✅     | [Wireframe](../Proposal/Wireframes/personaltimeline.JPG) | Will load data if browsed online|
| User Profile (main) | ✅     | [Wireframe](../Proposal/Wireframes/userprofile.JPG)      | Will load data if browsed online|
| Friend Search       | ✅     | [Wireframe](../Proposal/Wireframes/friend_search.JPG)    | Search will not work offline|
| My Reviews          | ✅     | [Wireframe](../Proposal/Wireframes/myreviews.JPG)        | Will load data if browsed online|
| Friend Request      | ✅     | [Wireframe](../Proposal/Wireframes/friend_request.JPG)   | Will load data if browsed online (but cannot accept/decline)|
| Trail Focus Page    | ✅     | [Wireframe](../Proposal/Wireframes/trail_details.JPG)    | Will load data if trail expanded online|
| Write a Review      | ✅     | [Wireframe](../Proposal/Wireframes/write_review.JPG)     | Will load data if browsed online, submitting a review will not work|
| Current Bike        | ✅     | [Wireframe](../Proposal/Wireframes/bike.JPG)             | Will load data if browsed online, cannot change bike|
| Plan-to-Ride        | ✅     | [Wireframe](../Proposal/Wireframes/plantoride.JPG)       | Will load data if browsed online, can't remove trails|
| My Favorites        | ✅     | [Wireframe](../Proposal/Wireframes/favorites.JPG)        | Will load data if browsed online, can't remove trails|
| Settings            | ✅     | [Wireframe](../Proposal/Wireframes/settings.JPG)         | Will load data if browsed online, cannot change settings|

### API Endpoints and Their Behavior

| Method   | Route                                          | Description                                                                                                      | Query Params           |
| -------- | ---------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- | ---------------------- |
| `POST`   | `/login`                                       | Sends a login request with an email and password combination                                                     |                        |
| `POST`   | `/event`                                       | Creates an event object, which can be a rating, plan-to-ride, or favorite for a specific trail                   |                        |
| `POST`   | `/register`                                    | Creates a user. Receives a username, email, and password                                                         |                        |
| `GET`    | `/user/me`                                     | Get information for requesting user, assuming they're authenticated                                              |
| `GET`    | `/user/me/settings`                            | Get email and avatar (configurable values) of requesting user                                                    |
| `POST`   | `/user/me/settings`                            | Changes email or avatar (or both) of requesting user                                                             |
| `GET`    | `/user/:username`                              | Gets a user by username and returns the user's username, bike name, and profile picture for use in their profile |                        |
| `PUT`    | `/user/:username`                              | Updates a user's information given a new bike name and/or profile picture                                        |                        |
| `POST`   | `/favorites`                                   | Adds a favorite trail to user's list                                                                             |                        |
| `GET`    | `/favorites`                                   | Retrieves a list user's favorite trails                                                                          |                        |
| `DELETE`    | `/favorites/:favoriteId`                                   | Deletes a user's favorite trail                                                                          |                        |
| `POST`   | `/plantoride`                                  | Adds a plan to ride trail to user's list                                                                         |                        |
| `GET`    | `/plantoride`                                  | Retrieves a list user's plan to ride trails                                                                      |                        |
| `DELETE`    | `/plantoride/:planId`                                   | Deletes a user's planned trail                                                                          |                        |
| `POST`   | `/review`                                      | Adds a trail review to user's list                                                                               |                        |
| `GET`    | `/review`                                      | Retrieves a list user's trail reviews                                                                            |                        |
| `DELETE` | `/event/id/:id`                                | Removes an event by id                                                                                           |                        |
| `GET`    | `/timeline/personal`                           | Retreives a list of the authenticated user's most recent events                                                  |                        |
| `GET`    | `/timeline/friends`                            | Retreives a list of the user's friends most recent events                                                        |                        |
| `GET`    | `/trails`                                      | Retrieves a list of bike trails close to the queried location                                                    | location, radius, page |
| `POST`   | `/trails`                                      | Adds a trail to the database upon user creating an event                                                         |                        |
| `GET`    | `/friendRequests/find/:username`               | Retrieves a list of a user's friend requests given their relative username                                       |                        |
| `GET`    | `/friendRequests/:username`                    | Retrieves a list of a user's friend requests given their username                                                |                        |
| `POST`   | `/friendRequests/:username`                    | Sends a friend request to a user given their username                                                            |                        |
| `POST`   | `/friendRequests/:username/request/:requestId` | Responds to a friend request from a user                                                                         |                        |

#### Individual Contributions for Project Lifespan

| Name            | Final Milestone | Milestone 2 | Milestone 1                                                                                                        
| --------------- | ----------------|-------------|-------------------------------------------------------------------------------------------------------------------|
| Cade Cuddy      |  Service worker implementation, Manifest optimization, Added profile links to friend search          | Authentication Middleware, Frontend auth wrapper, Trail Search page, Database Setup, DB Model definitions, Authentication Endpoints, User endpoints       | Splash Page, Landing Page, Timeline Page, Header, Server Structure, Linked Frontend together, Auth Endpoints, User Endpoints, Timeline Endpoints, Event Endpoints, Trail Endpoints                                                                                                               |
| John Cirincione | Fixed bike page to alert user when bike is updated. Added a profile picture with a clickable link to every entry on friends timeline. Disabled the creation of reserved username 'me'.  | Trails backend, personal and friends timeline backend, Current Bike frontend page, Friend Request frontend page, Plan-To-Ride frontend       | User Profile Page, Friend Search Page, Trails Endpoint |                                                                                      
| Eric Samuel     | Updated user profiles for friends, Made remaining trail cards expandable, Added remove endpoints and functionality for plan-to-ride and favorites            | Friend Request/Search backend, Friend Request/Search dynamic frontend, Event backend and frontend dynamic frontend (Favorites/Review/Plan-to-Ride)       | Login Page, Signup Page, FriendRequest Endpoints
                                                                                                               

