## Experience Swiper

This project aims to bring people together by swiping through different experiences. 
It is implemented using Javalin and JPA

### ER Diagram
Event
- Name
- Estimated Price
- Dress code
- Descriptions
- OneToMany eventGroup

EventGroup
- Location
- Name (location + event name?)
- Date
- Time
- ManyToOne event
- ManyToMany users

Users
- Name
- Address?
- Age
- Phone number
- Email
- ManyToMany EventGroup

### Endpoints
#### Event
- getEvent
- getEvent/{id}
- createEvent
- updateEvent
- deleteEvent
- sortEvents/{eventType}
- sortByPrice

#### User
- createUser
- deleteUser
- updateUser
- viewUser



### How to run

1. Create a database in your local Postgres instance called `event`
2. Run the main method in the config.Populate class to populate the database with some data
3. Run the main method in the Main class to start the server on port 7070
4. See the routes in your browser at `http://localhost:7070/routes`
5. Request the `http://localhost:7070/events` endpoint in your browser to see the list of events and rooms
6. Use the dev.http file to test the routes, GET/POST/PUT/DELETE requests are available

## Docker commands

```bash
docker-compose up -d
docker-compose down
docker logs -f  watchtower
docker logs watchtower
docker logs hotelAPI
docker logs db
docker container ls
docker rmi <image_id>
docker stop <container_id>
docker rm <container_id>
```
