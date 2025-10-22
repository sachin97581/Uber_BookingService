
# Uber Booking Service

This service manages ride bookings for an Uber-like platform. It is built with **Java 21**, **Spring Boot**, **Gradle**, and uses **Eureka** for service discovery. The service interacts with other microservices (e.g., Location and Socket services) via **Retrofit**.

## Other Microservices
- **Auth Service**: This service provides secure authentication, JWT-based authorization, and user management for passengers.
- **Entity Service**: Works as a library which is provides all modules for other services.
- **Location Service**: Provides nearby driver locations.
- **WebSocket Service**: WebSocket service for real-time ride request and response handling
- **Service Discovery**: Acts as a Service Discovery Server using Netflix Eureka. Enabling communication between distributed components.
- **Review Service**: Manages passenger & Driver Review.
<img width="1375" height="724" alt="Screenshot 2025-10-22 121201" src="https://github.com/user-attachments/assets/de79f840-2e81-4bbb-9a46-f25242cbc16a" />


## Features

- **Booking Management**:  
  Create and update ride bookings for passengers.

- **Driver Assignment**:  
  Automatically finds and assigns nearby drivers using the Location Service.

- **Asynchronous Communication**:  
  Uses Retrofit for non-blocking API calls to external services.

- **Service Discovery**:  
  Integrates with Eureka for dynamic service endpoint resolution.

- **Database Integration**:  
  Uses Spring Data JPA for persistence with MySQL.

## Main Components

### Controllers

- **BookingController** (`src/main/java/com/example/uber_BookinService/controller/BookingController.java`):  
  Exposes REST endpoints to create and update bookings.

### Services

- **BookingService / BookingServiceImp** (`src/main/java/com/example/uber_BookinService/service/BookingService.java`, `BookingServiceImp.java`):  
  Business logic for booking creation, driver assignment, and booking updates.

### APIs (Retrofit Interfaces)

- **LocationServiceApi** (`apis/LocationServiceApi.java`):  
  Calls the Location Service to fetch nearby drivers.

- **UberSocketApi** (`apis/UberSocketApi.java`):  
  Notifies the Socket Service about new ride requests.

### DTOs

- **CreateBookingDto / CreateBookingResponseDto**:  
  Data transfer objects for booking creation and responses.

- **UpdateBookingRequestDto / UpdateBookingResponseDto**:  
  For updating booking status and driver assignment.

- **DriverLocationDto / NearByDriversRequestDto / RideRequestDto**:  
  For inter-service communication.

### Repositories

- **BookingRepository, DriverRepository, PassengerRepository**:  
  JPA repositories for database operations.

### Configuration

- **RetrofitConfig**:  
  Configures Retrofit clients for external service communication, using Eureka for service URLs.

## How It Works

1. **Create Booking**:
    - User sends a booking request.
    - Service saves booking with status `ASSIGNING_DRIVER`.
    - Fetches nearby drivers from Location Service.
    - Notifies Socket Service to raise a ride request.

2. **Update Booking**:
    - Updates booking status and assigns a driver.

## Build & Run

- Build:  
  `./gradlew build`
- Run:  
  `./gradlew bootRun`

## Dependencies

- Spring Boot (Web, Data JPA, WebSocket)
- Retrofit2 (with Gson converter)
- Eureka Client
- MySQL Connector
- Lombok

---

This documentation provides an overview and can be extended with API endpoint details and usage examples as needed.
