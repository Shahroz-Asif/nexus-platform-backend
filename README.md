# 🚀 Copilot Instructions for Spring Boot Backend (Nexus Platform)

### 1. Project Setup

```plaintext
// Create a new Spring Boot project using Spring Initializr with:
// Dependencies: Spring Web, Spring Security, Spring Data JPA, PostgreSQL Driver, Lombok, Validation, Spring Boot Starter Mail, springdoc-openapi, WebSocket
// Build tool: Maven
// Java version: 17+
```

### 2. Project Structure

```plaintext
// Organize project with these packages:
// - config/          (security, CORS, app configs)
// - controller/      (REST endpoints)
// - dto/             (request/response DTOs)
// - entity/          (JPA entities)
// - repository/      (JpaRepository interfaces)
// - service/         (business logic)
// - security/        (JWT filters, user details, auth utils)
```

### 3. Database Config

```yaml
// Add to application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nexus
    username: nexus_user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  mail:
    host: smtp.example.com
    port: 587
    username: example@example.com
    password: secret
```

### 4. Authentication & User Management

```plaintext
// Implement User entity with fields: id, email, password, role, bio, portfolio, preferences
// Use BCryptPasswordEncoder for hashing
// Create JWT util class for token generation & validation
// Secure endpoints with Spring Security
// Implement controllers for:
// - POST /auth/register
// - POST /auth/login
// - GET /users/profile
// - PUT /users/profile
```

### 5. Meeting Scheduling

```plaintext
// Create Meeting entity: id, title, participants, dateTime, status
// Prevent overlapping meetings in service layer
// Endpoints:
// - POST /meetings (create)
// - PUT /meetings/{id} (update)
// - DELETE /meetings/{id} (cancel)
```

### 6. Video Calling Backend

```plaintext
// Add WebSocket + STOMP support
// Use signaling endpoints:
// - POST /video/createRoom
// - POST /video/joinRoom
// Store meeting/video session history in DB
```

### 7. Document Handling

```plaintext
// Create Document entity: id, owner, version, status (Draft/Reviewed/Signed), metadata
// Upload endpoint: POST /documents/upload (MultipartFile → AWS S3/Cloudinary)
// Preview endpoint: GET /documents/{id}/preview
// E-sign endpoint: POST /documents/{id}/sign
```

### 8. Payments

```plaintext
// Integrate Stripe Java SDK or PayPal SDK (sandbox)
// Create Transaction entity: id, user, amount, type, status (Pending/Completed/Failed)
// Endpoints:
// - POST /payments/deposit
// - POST /payments/withdraw
// - POST /payments/transfer
// - GET /payments/history
```

### 9. Security Enhancements

```plaintext
// Add input validation with @Valid
// Use Bucket4j for rate limiting login/register
// Mock 2FA with Spring Mail (send OTP to email)
// Ensure all passwords are hashed
```

### 10. Documentation & Deployment

```plaintext
// Add Swagger UI with springdoc-openapi
// Write OpenAPI annotations on controllers
// Deploy backend to Render/Heroku/AWS
// Provide Postman collection or Swagger JSON
```