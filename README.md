# Mini Rewards Service

A Spring Boot-based rewards service that allows users to earn and redeem points based on purchases. This service implements Clean Architecture principles with comprehensive testing, security, and monitoring features.

## Requirements Implementation

### Clean Architecture
This project follows **Clean Architecture** principles to ensure separation of concerns and maintainability:

- **Domain Layer** (`domain/`): Contains business entities (UserAccount, Wallet, RewardTransaction), value objects (UserTier), and core business rules. This layer is independent of external frameworks.
- **Application Layer** (`application/service/`): Contains use cases and application logic (RewardService, UserService). Orchestrates domain entities and defines application workflows.
- **Infrastructure Layer** (`infrastructure/`): Contains external concerns like web controllers, repositories, configurations, and external integrations. Implements interfaces defined in inner layers.
- **Frameworks Layer**: External frameworks and drivers (Spring Boot, JPA, Redis, RabbitMQ).

This structure ensures:
- Independence from external frameworks
- Testability of business logic
- Easy replacement of external dependencies
- Clear separation of responsibilities

### Clean Code
The codebase adheres to Clean Code principles:

- **Single Responsibility Principle**: Each class has one reason to change (e.g., RewardService handles rewards logic, UserService handles user management).
- **Open/Closed Principle**: Code is open for extension but closed for modification (e.g., UserTier enum can be extended with new tiers).
- **Dependency Inversion**: High-level modules don't depend on low-level modules; both depend on abstractions (e.g., services depend on repository interfaces).
- **DRY (Don't Repeat Yourself)**: Common logic is extracted (e.g., wallet creation in a private method).
- **Meaningful Names**: Classes, methods, and variables have descriptive names (e.g., `awardPoints`, `redeemPoints`).
- **Small Functions**: Methods are focused and short, doing one thing well.
- **Comprehensive Comments**: Code is self-documenting with necessary comments for complex logic.

### Design Patterns
Several design patterns are implemented:

- **Repository Pattern**: Abstracts data access (e.g., UserAccountRepository, WalletRepository) for easy testing and switching data sources.
- **Service Layer Pattern**: Encapsulates business logic in services (RewardService, UserService).
- **DTO Pattern**: Data Transfer Objects (AwardRequest, BalanceResponse) for API communication.
- **Factory Pattern**: Entity creation methods (e.g., RewardTransaction.earn(), RewardTransaction.redeem()).
- **Strategy Pattern**: Tier-based point multipliers implemented via UserTier enum.
- **Observer Pattern**: Event publishing for decoupled messaging (RewardEventPublisher).

### Tests
Comprehensive testing ensures code quality and reliability:

- **Unit Tests**: Test individual components in isolation using mocks.
- **Integration Tests**: Test end-to-end functionality with real dependencies (Testcontainers for PostgreSQL).
- **Test Coverage**: Aims for high coverage of business logic.
- **Testcontainers**: Uses Docker containers for realistic testing environments.

#### Run All Tests
```bash
./mvnw test
```

#### Run Specific Test Classes
- Basic context load test: `./mvnw test -Dtest=RewardsServiceApplicationTests`
- Integration test: `./mvnw test -Dtest=RewardServiceIntegrationTest`
- Multiple classes: `./mvnw test -Dtest=RewardsServiceApplicationTests,RewardServiceIntegrationTest`
- Pattern matching: `./mvnw test -Dtest=*IntegrationTest`

#### Test Instructions
1. Ensure Docker is running for Testcontainers (required for `RewardServiceIntegrationTest`)
2. Run unit tests: `./mvnw test -Dtest=*Test -Dtest=!*IntegrationTest`
3. Run integration tests: `./mvnw test -Dtest=*IntegrationTest`
4. View test coverage: `./mvnw test jacoco:report` (report in `target/site/jacoco/`)
5. Run tests quietly: `./mvnw test -q`
6. Debug output: `./mvnw test -X`
7. Skip tests during build: `./mvnw clean install -DskipTests`

### Documentation
API documentation is provided via:

- **Swagger/OpenAPI**: Interactive API docs at `/swagger-ui.html`
- **README**: This comprehensive guide
- **Code Comments**: Inline documentation for complex logic
- **Architecture Diagrams**: Text-based architecture explanations

### Deployment Plan
The service supports multiple deployment strategies:

- **Local Development**: Run with `./mvnw spring-boot:run`
- **Docker**: Single container with `docker build -t rewards-service . && docker run -p 8080:8080 rewards-service`
- **Docker Compose**: Full stack with database, cache, and message broker: `docker-compose up --build`
- **Production**: Use `SPRING_PROFILES_ACTIVE=prod` with external PostgreSQL, Redis, RabbitMQ
- **Cloud Deployment**: Compatible with Kubernetes, AWS ECS, etc.

Environment variables for production:
- Database: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- Redis: `SPRING_DATA_REDIS_HOST`, `SPRING_DATA_REDIS_PORT`
- RabbitMQ: `SPRING_RABBITMQ_HOST`, `SPRING_RABBITMQ_PORT`

### Caching
Redis-based caching improves performance:

- **Wallet Balance Caching**: `@Cacheable` on `getBalance()` to avoid database queries for frequent balance checks.
- **Cache Eviction**: `@CacheEvict` on point-changing operations (`awardPoints`, `redeemPoints`).
- **Configuration**: `spring.cache.type=redis` with connection settings.

### Logging
Structured logging provides observability:

- **SLF4J**: Logging facade with Logback implementation.
- **Log Levels**: INFO for business operations, DEBUG for detailed tracing.
- **Context**: Logs include user IDs, transaction IDs for traceability.
- **Example**: `log.info("Awarded {} points to user {}", points, userId);`

### Messaging
RabbitMQ enables event-driven architecture:

- **Event Publishing**: `RewardEventPublisher` sends events for earned/redeemed points.
- **Decoupling**: Services communicate asynchronously.
- **Configuration**: `spring.rabbitmq.*` properties for connection.

### Metrics
Spring Boot Actuator provides monitoring:

- **Health Checks**: `/actuator/health` for service status.
- **Metrics**: `/actuator/metrics` for performance data (HTTP requests, database connections).
- **Custom Metrics**: Can be added for business-specific KPIs.

### Rate Limiting 
Bucket4j-based rate limiting protects against abuse:

- **Configuration**: `RateLimitConfig` and `RateLimitInterceptor`.
- **Limits**: Configurable per endpoint or user.
- **Implementation**: Token bucket algorithm for smooth rate limiting.

### Storage
Database abstraction supports multiple storage backends:

- **PostgreSQL**: Primary production database with JPA/Hibernate.
- **H2**: In-memory database for development.
- **Testcontainers PostgreSQL**: For integration testing.
- **Schema**: Auto-generated with `spring.jpa.hibernate.ddl-auto=update` (validate in prod).

## Features

- Award points based on purchase amounts with tier multipliers
- Redeem points for virtual wallet credits
- API key authentication
- Redis caching for improved performance
- RabbitMQ messaging for event-driven architecture
- Bucket4j rate limiting
- Comprehensive logging and metrics
- Swagger/OpenAPI documentation
- Database abstraction ( PostgreSQL  for dev, PostgreSQL for prod)

## API Endpoints

- `POST /api/rewards/award` - Award points to user
- `POST /api/rewards/redeem` - Redeem points for money
- `GET /api/rewards/balance/{userId}` - Get user balance

All endpoints require `X-API-Key` header for authentication.(Except for [http://localhost:8080/api/users] for User Creation then we obtain api-key for the user)

## Setup Instructions

### Prerequisites

- Java 21
- Maven 3.6+
- Docker (for testcontainers)

### Running Locally

1. Clone the repository
2. Run with Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Swagger UI at `http://localhost:8080/swagger-ui.html`

### Database Configuration

- **Development**:PostgreSQL
- **Test**: PostgreSQL via Testcontainers
- **Production**: Configure PostgreSQL in `application-prod.properties`


## Deployment

### Local Development

1. Run the application: `./mvnw spring-boot:run`
2. Access the service at `http://localhost:8080`

### Docker

Build and run with Docker:
```bash
docker build -t rewards-service .
docker run -p 8080:8080 rewards-service
```

### Docker Compose

For full stack deployment with PostgreSQL:
```bash
docker-compose up --build
```

This will start:
- Rewards service on port 8080
- PostgreSQL database on port 5432
- Redis cache on port 6379
- RabbitMQ message broker on ports 5672 (AMQP) and 15672 (Management UI)

### Production Deployment

1. Configure environment variables for database connection
2. Use `SPRING_PROFILES_ACTIVE=prod` for production profile
3. Ensure proper secrets management for API keys and database credentials
4. Consider using Kubernetes or cloud services for scaling

## Configuration

Key configuration properties:

- `spring.datasource.*` - Database settings
- `spring.data.redis.*` - Redis cache settings
- `spring.rabbitmq.*` - RabbitMQ messaging settings
- `spring.cache.type` - Caching provider (set to 'redis')
- `management.endpoints.*` - Actuator endpoints

## Security

- API key authentication via `X-API-Key` header
- User tiers with different multipliers (STANDARD, SILVER, GOLD, PLATINUM)
- Secure password handling (API keys are hashed)

## Monitoring

- Health checks: `/actuator/health`
- Metrics: `/actuator/metrics`
- Logs: Structured logging with SLF4J

## Summary

### How Points Are Earned
Points are earned through purchases via `POST /api/rewards/award`. The request includes `userId` and `amountCents` (e.g., 10000 for $100). Calculation: `basePoints = (amountCents / 100) * 100` (1 point per $1), then `points = basePoints * tier.multiplier` (e.g., PLATINUM = 2x). A `RewardTransaction` (earn type) records the transaction, the user's `Wallet` points are incremented, and a reward-earned event is published via RabbitMQ. Cache eviction ensures balance accuracy.

### Broader Architecture Integration
This fits into Clean Architecture: Domain layer defines `UserTier` multipliers and `RewardTransaction` creation rules; Application layer (`RewardService`) orchestrates earning logic, validating users and calculating points; Infrastructure layer handles API exposure (`RewardController`), data persistence (`RewardTransactionRepository`, `WalletRepository`), and external messaging (`RewardEventPublisher`); Frameworks layer provides Spring Boot for web, JPA for ORM, Redis for caching balances, and PostgreSQL for storage. The design enables scalable, testable rewards management with security (API key auth), monitoring (Actuator metrics), and rate limiting.



