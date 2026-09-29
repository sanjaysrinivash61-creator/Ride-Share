# RideShare

Minimal office/college carpool matching platform.

## Stack
- Java 21
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- MySQL
- Bean Validation
- Minimal HTML/CSS/JavaScript UI

## Structure
src/main/java/com/rideshare/
- controllers
- repositories
- services
- models
- dto

## Run
1. Make sure MySQL is running.
2. In `application.properties`, change `spring.datasource.username/password` if needed.
3. From this folder:
   `mvn spring-boot:run`
4. Open `http://localhost:8080/`

The configured URL uses `createDatabaseIfNotExist=true`, so the `rideshare` database is created automatically when the MySQL user has permission.

## Main flow
Create users -> Offer ride -> Search ride -> Request seat -> Driver approves/rejects.

Business rules include duplicate request prevention, driver self-request prevention, departure-time validation, seat availability checks, and driver-only approval/rejection.
