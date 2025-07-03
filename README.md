# Yujie Delivery Platform

A food delivery platform backend system based on Spring Boot.

## Tech Stack

- **Backend Framework**: Spring Boot 3.2.3
- **Database**: MySQL 8.0
- **Security**: Spring Security + JWT
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Java Version**: 17

## Features

### User Management
- User registration/login
- Role management (User, Restaurant Owner, Delivery Person, Admin)
- User profile management

### Restaurant Management
- Restaurant information management
- Restaurant search and filtering
- Restaurant status management

### Menu Management
- Menu item management
- Dish categorization
- Dish availability management

### Order Management
- Order creation and management
- Order status tracking
- Delivery person assignment

### Security
- JWT authentication
- Role-based access control
- CORS support

## Quick Start

### Prerequisites

- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Database Configuration

1. Create a MySQL database
2. Edit the database connection info in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/yujiedelivery?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run the Project

1. Clone the repository
```bash
git clone <repository-url>
cd yujiedelivery
```

2. Compile the project
```bash
mvn clean compile
```

3. Run the project
```bash
mvn spring-boot:run
```

4. Access the application
- App: http://localhost:8080
- API Docs: http://localhost:8080/swagger-ui.html

## API Documentation

After starting the application, you can access the API docs at:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## Main API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/signup` - User registration

### User Management
- `GET /api/users/me` - Get current user info
- `PUT /api/users/{id}` - Update user info
- `GET /api/users` - Get all users (admin)

### Restaurant Management
- `GET /api/restaurants` - Get all restaurants
- `GET /api/restaurants/{id}` - Get restaurant details
- `POST /api/restaurants` - Create restaurant (owner)
- `PUT /api/restaurants/{id}` - Update restaurant info

### Menu Management
- `GET /api/restaurants/{restaurantId}/menu-items` - Get restaurant menu
- `POST /api/restaurants/{restaurantId}/menu-items` - Add menu item
- `PUT /api/restaurants/{restaurantId}/menu-items/{id}` - Update menu item

### Order Management
- `POST /api/orders` - Create order
- `GET /api/orders/{id}` - Get order details
- `PUT /api/orders/{id}/status` - Update order status

## User Roles

- **USER**: Regular user, can place orders
- **RESTAURANT_OWNER**: Restaurant owner, can manage restaurants and menus
- **DELIVERY_PERSON**: Delivery person, can deliver orders
- **ADMIN**: Administrator, has all permissions

## Project Structure

```
src/main/java/com/yujiedelivery/
├── config/          # Configuration classes
├── controller/      # Controllers
├── dto/             # Data Transfer Objects
├── exception/       # Exception handling
├── model/           # Entity classes
├── repository/      # Data access layer
├── security/        # Security-related
└── service/         # Business logic layer
```

### Database Tables

- `users` - Users
- `restaurants` - Restaurants
- `menu_items` - Menu items
- `orders` - Orders
- `order_items` - Order items

## License

Apache License 2.0 