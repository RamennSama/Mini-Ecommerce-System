# Mini E-Commerce System

A RESTful E-Commerce backend system built with Spring Boot, featuring payment gateway integration and email notifications.

## Tech Stack

- **Java 21** & **Spring Boot 4.0.2**
- **MySQL** - Database
- **Spring Data JPA** - ORM
- **Spring Security** - Authentication
- **MapStruct** - Object mapping
- **VNPay** - Payment gateway
- **Thymeleaf** - Email templates
- **Swagger/OpenAPI** - API documentation

## Features

- ✅ User, Product, Category, Order management (CRUD)
- ✅ VNPay payment integration with secure signature verification
- ✅ Email notifications with HTML templates
- ✅ Stock management with automatic deduction
- ✅ Order tracking with status updates
- ✅ RESTful API with Swagger documentation

## Prerequisites

- Java 21
- MySQL 8.x
- Maven 3.6+

## Setup

1. **Clone the repository**
```bash
git clone https://github.com/RamennSama/Mini-Ecommerce-System.git
cd Mini-Ecommerce-System
```

2. **Configure database in `application.yml`**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/minisystem?createDatabaseIfNotExist=true
    username: your_username
    password: your_password
```

3. **Configure email (optional)**
```yaml
spring:
  mail:
    username: your-email@gmail.com
    password: your-app-password
```

4. **Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

## API Documentation

Access Swagger UI at: `http://localhost:8080/docs`

## API Endpoints

### User Management
- `GET /api/users` - Get all users
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/search?name={name}` - Search products
- `POST /api/products` - Create product

### Order Management
- `GET /api/orders` - Get all orders
- `POST /api/orders` - Create order
- `GET /api/orders/user/{userId}` - Get user orders

### Payment (VNPay)
- `POST /api/vnpay/create-payment/{orderId}` - Generate payment URL
- `GET /api/vnpay/check-payment/{txnRef}` - Check payment status

## Payment Flow

1. Create order → Status: `PENDING`
2. Generate VNPay payment URL
3. User completes payment on VNPay
4. System receives callback and updates order → Status: `PAID`
5. Send confirmation email

## Project Structure

```
src/main/java/com/ramennsama/springboot/system/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/             # Request/Response DTOs
├── entity/          # JPA entities
├── mapper/          # MapStruct mappers
├── repository/      # JPA repositories
├── service/         # Business logic
└── utils/           # Utility classes
```

## Database Schema

- `users` - User accounts
- `products` - Product catalog
- `categories` - Product categories
- `orders` - Order information
- `order_items` - Order line items
- `payments` - Payment records

## Security

- BCrypt password encryption
- HMAC-SHA512 for VNPay signature verification
- Spring Security configuration

## License

This project is for educational purposes.

## Author

[RamennSama](https://github.com/RamennSama)
