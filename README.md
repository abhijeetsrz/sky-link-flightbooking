# SkyLink – Online Flight Booking REST API

SkyLink is a backend REST API for an online flight booking system built using Spring Boot, Maven, Spring JDBC (JdbcTemplate), and MySQL.

The project is designed as a UI-independent backend service that can later be integrated with web, mobile, or partner applications.
It demonstrates clean architecture, real-world business logic, and SQL-based persistence without using JPA/Hibernate.

# Features
## User Management
- Register new users
- Fetch user profile by ID

## Flight Management
- Add new flights (Admin)
- View flight details
- List all available flights
- Search flights by source, destination, and journey date
- Real-time seat availability tracking

## Booking Management

- Create bookings with multiple passengers
- Seat availability validation before booking
- Unique PNR generation
- Store passenger details per booking
- Fetch booking by booking ID
- Fetch all bookings for a user
- Atomic booking process using database transactions

## Technology Stack
- Language: Java
- Framework: Spring Boot
- Build Tool: Maven
- Database: MySQL
- Persistence: Spring JDBC (JdbcTemplate)
- Architecture: MVC (Controller → Service → Repository)
- API Style: REST (JSON)

## Project Structure

## Project Structure
- controller – REST API endpoints
- service – Business logic and validations
- repository – SQL queries using JdbcTemplate
- model – Domain models
- dto – Request and response DTOs
- SkylinkFlightBookingApplication.java – Application entry point

 ## Database Design
### Users
- id
- name
- email
- phone
- role
- created_at

### Flights
- id
- flight_number
- airline
- source_airport
- destination_airport
- departure_time
- arrival_time
- total_seats
- available_seats
- fare
- status

### Bookings
- id
- pnr
- user_id
- flight_id
- booking_time
- total_amount
- status
- payment_status

### Passengers
- id
- booking_id
- name
- age
- gender
- id_proof
- id_number

## REST API Endpoints
### User APIs
| Method | Endpoint          | Description    |
| ------ | ----------------- | -------------- |
| POST   | `/api/users`      | Register user  |
| GET    | `/api/users/{id}` | Get user by ID |

### Flight APIs
| Method | Endpoint              | Description        |
| ------ | --------------------- | ------------------ |
| POST   | `/api/flights`        | Add flight (Admin) |
| GET    | `/api/flights/{id}`   | Get flight by ID   |
| GET    | `/api/flights`        | List all flights   |
| GET    | `/api/flights/search` | Search flights     |

### Booking APIs
| Method | Endpoint                      | Description             |
| ------ | ----------------------------- | ----------------------- |
| POST   | `/api/bookings`               | Create booking          |
| GET    | `/api/bookings/{id}`          | Get booking by ID       |
| GET    | `/api/bookings/user/{userId}` | Get bookings for a user |

### Sample Booking Request

```json
{
  "userId": 1,
  "flightId": 1,
  "numberOfSeats": 2,
  "passengers": [
    {
      "name": "Abhijeet Kumar",
      "age": 22,
      "gender": "Male",
      "idProof": "Aadhaar",
      "idNumber": "1234-5678-9999"
    },
    {
      "name": "Rahul Singh",
      "age": 25,
      "gender": "Male",
      "idProof": "PAN",
      "idNumber": "ABCDE1234F"
    }
  ]
}

```

## Configuration
### application.properties
- server.port = 8080
- spring.datasource.url = jdbc:mysql://localhost:3306/skylink_db
- spring.datasource.username = root
- spring.datasource.password = your_password
- spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver

## How to Run
- Clone the repository
- git clone https://github.com/your-username/skylink-flight-booking.git
- Create database
- CREATE DATABASE skylink_db;
- Update database credentials in application.properties
- Run the application
- mvn spring-boot: run
- Test APIs using Postman

## Highlights (Why this project stands out)

- Uses Spring JDBC instead of JPA (clear SQL understanding)
- Proper transaction management for bookings
- Clean layered architecture
- Real-world business rules (seat validation, PNR generation)
- Easily extensible for JWT, Redis, Docker, and cloud deployment

## Future Enhancements
- JWT authentication & authorization
- Booking cancellation and refund handling
- Role-based admin access
- Swagger / OpenAPI documentation
- Docker & AWS deployment
- Redis caching for flight search

## Author
Abhijeet Kumar | 
B.Tech, IIT Guwahati
