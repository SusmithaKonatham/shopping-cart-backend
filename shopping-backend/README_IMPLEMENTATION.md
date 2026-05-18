# Shopping Backend - Enterprise-Grade E-Commerce API

A production-ready Spring Boot REST API for an e-commerce platform with comprehensive error handling, validation, logging, and best practices.

## 🚀 Features

- **RESTful API** with proper versioning (`/api/v1/`)
- **Input Validation** using Jakarta Bean Validation
- **Global Exception Handling** with consistent error responses
- **Structured Logging** for debugging and monitoring
- **DTOs** for request/response separation
- **H2 Database** for development (can be switched to MySQL/PostgreSQL)
- **Layered Architecture** (Controller → Service → Repository)
- **Service Interfaces** for loose coupling and testability
- **CORS Configuration** for cross-origin requests
- **Environment Profiles** (dev, prod)
- **Transaction Management** with @Transactional
- **JPA Relationships** with proper entity mapping
- **Comprehensive Tests** with MockMvc

## 📋 Project Structure

```
shopping-backend/
├── src/main/java/com/shopping/shoppingbackend/
│   ├── config/              # Configuration classes (CORS, Transaction, ObjectMapper)
│   ├── controller/          # REST Controllers (ProductController, CartController)
│   ├── dto/                 # Data Transfer Objects (Request, Response, API Response)
│   ├── entity/              # JPA Entities (Product, CartItem)
│   ├── exception/           # Custom exceptions and global exception handler
│   ├── mapper/              # DTO to Entity mappers
│   ├── repository/          # Spring Data JPA Repositories
│   ├── service/             # Service interfaces and implementations
│   ├── DataLoader.java      # Initial data loader
│   └── ShoppingBackendApplication.java
├── src/main/resources/
│   ├── application.properties       # Default configuration
│   ├── application-dev.properties   # Development profile
│   └── application-prod.properties  # Production profile
├── src/test/java/
│   └── ShoppingBackendApplicationTests.java
└── pom.xml                  # Maven dependencies
```

## 🛠️ Prerequisites

- Java 17+
- Maven 3.6+
- IDE (IntelliJ IDEA, VS Code, Eclipse)

## 📦 Installation & Setup

### Clone Repository
```bash
git clone <repository-url>
cd shopping-backend
```

### Build Project
```bash
mvn clean install
```

### Run Application

#### Development Mode
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

#### Production Mode
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

#### Default (No Profile)
```bash
mvn spring-boot:run
```

### Access Application

- **API Base URL**: http://localhost:8080/api/v1
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## 📚 API Documentation

### Products API

#### Get All Products
```http
GET /api/v1/products
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Products retrieved successfully",
  "data": [
    {
      "id": 1,
      "name": "iPhone 15",
      "price": 80000,
      "imageUrl": "https://...",
      "active": true
    }
  ],
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Get Product by ID
```http
GET /api/v1/products/{id}
```

**Response (200 OK):** Same as single product object

**Response (404 Not Found):**
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Product not found with id: 999",
  "errors": null,
  "path": "/api/v1/products/999",
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Create Product
```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "iPad Pro",
  "price": 120000,
  "imageUrl": "https://example.com/ipad.jpg"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Product created successfully",
  "data": {
    "id": 4,
    "name": "iPad Pro",
    "price": 120000,
    "imageUrl": "https://example.com/ipad.jpg",
    "active": true
  },
  "timestamp": "2026-05-18T10:30:00"
}
```

**Response (400 Bad Request):**
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Input validation failed",
  "errors": {
    "name": "Product name is required",
    "price": "Price must be greater than 0",
    "imageUrl": "Image URL must be valid"
  },
  "path": "/api/v1/products",
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Update Product
```http
PUT /api/v1/products/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "price": 125000,
  "imageUrl": "https://example.com/updated.jpg"
}
```

**Response (200 OK):** Updated product object

#### Delete Product
```http
DELETE /api/v1/products/{id}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Product deleted successfully",
  "data": null,
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Deactivate Product
```http
PATCH /api/v1/products/{id}/deactivate
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Product deactivated successfully",
  "data": null,
  "timestamp": "2026-05-18T10:30:00"
}
```

### Cart API

#### Add to Cart
```http
POST /api/v1/cart
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Item added to cart successfully",
  "data": {
    "id": 1,
    "productId": 1,
    "productName": "iPhone 15",
    "price": 80000,
    "quantity": 2,
    "lineTotal": 160000
  },
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Get Cart Items
```http
GET /api/v1/cart
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Cart items retrieved successfully",
  "data": [
    {
      "id": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "price": 80000,
      "quantity": 2,
      "lineTotal": 160000
    }
  ],
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Update Cart Item
```http
PUT /api/v1/cart/{id}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 3
}
```

**Response (200 OK):** Updated cart item

#### Delete Cart Item
```http
DELETE /api/v1/cart/{id}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Item deleted from cart successfully",
  "data": null,
  "timestamp": "2026-05-18T10:30:00"
}
```

#### Clear Cart
```http
DELETE /api/v1/cart
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Cart cleared successfully",
  "data": null,
  "timestamp": "2026-05-18T10:30:00"
}
```

## 🔧 Configuration

### Development Profile (`application-dev.properties`)
- H2 in-memory database
- SQL logging enabled
- Debug level logging
- H2 Console enabled
- CORS: localhost:4200, localhost:3000

### Production Profile (`application-prod.properties`)
- External database (set via environment variables)
- SQL logging disabled
- Warning level logging
- H2 Console disabled
- CORS: configurable via environment

### Environment Variables for Production

```bash
export DB_URL=jdbc:mysql://localhost:3306/shopping
export DB_DRIVER=com.mysql.cj.jdbc.Driver
export DB_USERNAME=shopping_user
export DB_PASSWORD=secure_password
export CORS_ORIGINS=https://yourdomain.com
export SPRING_PROFILES_ACTIVE=prod
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=ShoppingBackendApplicationTests
```

### Test Coverage
```bash
mvn test jacoco:report
```

## 📝 Key Implementation Details

### DTOs (Data Transfer Objects)
- **ProductRequest**: Request payload validation
- **ProductResponse**: Response data transformation
- **CartItemRequest**: Cart item validation
- **CartItemResponse**: Cart item response with line total calculation
- **ApiResponse**: Standard API response wrapper
- **ErrorResponse**: Standard error response format

### Exception Handling
- **ResourceNotFoundException**: Thrown when entity not found (404)
- **BusinessException**: Thrown for business logic violations (400)
- **GlobalExceptionHandler**: Catches all exceptions and returns standard error responses

### Validation
- Bean Validation annotations (@NotBlank, @Positive, @URL, etc.)
- MethodArgumentNotValidException handled globally
- Custom validation messages for better UX

### Logging
- SLF4J with Logback configuration
- Debug logging in services
- Info logging for successful operations
- Warn logging for unexpected cases
- Error logging for exceptions

### Transaction Management
- @Transactional on service methods
- Read-only transactions for queries
- Proper rollback handling for exceptions

### Entity Relationships
- One-to-Many relationship between Product and CartItem
- Lazy loading for performance optimization
- Cascade delete for data consistency

## 🔐 Security Considerations

Current implementation includes:
- Input validation
- Global exception handling
- CORS configuration
- Structured error responses

Future enhancements:
- Spring Security integration
- JWT authentication
- Role-based authorization
- Password hashing with BCrypt
- Rate limiting

## 📈 Performance Optimization

Current optimizations:
- Lazy loading strategy
- JOIN FETCH queries to prevent N+1
- Indexed database columns
- Connection pooling configuration

Future enhancements:
- Pagination for list endpoints
- Caching with Caffeine/Redis
- Advanced query optimization
- Batch processing

## 🚀 Deployment

### Docker Deployment
```dockerfile
FROM openjdk:17-slim
COPY target/shopping-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Running with Docker Compose
```bash
docker-compose up
```

### Cloud Deployment (AWS/Azure/GCP)
- Containerize with Docker
- Use managed database services
- Configure environment variables
- Set up CI/CD pipeline

## 📖 Best Practices Implemented

1. **Layered Architecture**: Clear separation of concerns
2. **DTOs**: Protection against entity exposure
3. **Service Interfaces**: Loose coupling for testability
4. **Exception Handling**: Centralized error management
5. **Validation**: Input validation at boundaries
6. **Logging**: Comprehensive logging for debugging
7. **Configuration Management**: Environment-specific configurations
8. **Transaction Management**: Proper transaction boundaries
9. **Entity Relationships**: Proper JPA relationships
10. **API Versioning**: Version-based API endpoints

## 🐛 Troubleshooting

### Issue: H2 Console Not Accessible
- Ensure `spring.h2.console.enabled=true` in dev profile
- Check URL: http://localhost:8080/h2-console

### Issue: Validation Errors
- Ensure all required fields are provided
- Check @NotNull, @NotBlank annotations
- Review error response for field-level errors

### Issue: CORS Errors
- Verify CORS configuration in CorsConfig
- Check allowed-origins in properties
- Ensure Content-Type is set to application/json

### Issue: Database Connection Errors
- Verify database URL in properties
- Check database credentials
- Ensure database is running

## 📞 Support

For issues or questions:
1. Check logs for detailed error messages
2. Verify configuration settings
3. Review API documentation above
4. Check entity relationships and constraints

## 📄 License

This project is licensed under the MIT License.

## 🙏 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to branch
5. Create a Pull Request

---

**Last Updated**: May 18, 2026  
**Version**: 0.0.1-SNAPSHOT
