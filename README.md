# Mini Rewards Service

A Spring Boot-based rewards service that allows users to earn and redeem points based on purchases. This service implements Clean Architecture principles with comprehensive testing, security, and monitoring features.

## Architecture

This project follows **Clean Architecture** principles:

- **Domain Layer** (`domain/`): Contains business entities, value objects, and core business rules
- **Application Layer** (`application/service/`): Contains use cases and application logic
- **Infrastructure Layer** (`infrastructure/`): Contains external concerns like controllers, repositories, and configurations
- **Frameworks Layer**: External frameworks and drivers (Spring Boot, JPA, etc.)

### Key Components

- **Entities**: UserAccount, Wallet, RewardTransaction, WalletEntry, UserTier
- **Services**: RewardService (business logic), UserService (user management), RewardEventPublisher (messaging)
- **Repositories**: JPA repositories for data access
- **Controllers**: REST API endpoints
- **Security**: API key-based authentication
- **Caching**: Redis-based caching for performance
- **Messaging**: RabbitMQ for event-driven architecture
- **Rate Limiting**: Bucket4j-based rate limiting
- **Monitoring**: Spring Boot Actuator for metrics and health checks

## Features

- Award points based on purchase amounts with tier multipliers
- Redeem points for virtual wallet credits
- API key authentication
- Redis caching for improved performance
- RabbitMQ messaging for event-driven architecture
- Bucket4j rate limiting
- Comprehensive logging and metrics
- Swagger/OpenAPI documentation
- Database abstraction (H2 for dev, PostgreSQL for prod)

## API Endpoints

- `POST /api/rewards/award` - Award points to user
- `POST /api/rewards/redeem` - Redeem points for money
- `GET /api/rewards/balance/{userId}` - Get user balance

All endpoints require `X-API-Key` header for authentication.

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
3. Access H2 console at `http://localhost:8080/h2-console`
4. Swagger UI at `http://localhost:8080/swagger-ui.html`

### Database Configuration

- **Development**: H2 in-memory database
- **Test**: PostgreSQL via Testcontainers
- **Production**: Configure PostgreSQL in `application-prod.properties`

## Testing

Run tests with:
```bash
./mvnw test
```

Integration tests use Testcontainers with PostgreSQL.

### Test Instructions

1. Ensure Docker is running for Testcontainers
2. Run unit tests: `./mvnw test -Dtest=*Test`
3. Run integration tests: `./mvnw test -Dtest=*IntegrationTest`
4. View test coverage (if configured): `./mvnw test jacoco:report`

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

## Design Patterns Used

- **Repository Pattern**: Data access abstraction
- **Service Layer**: Business logic encapsulation
- **DTO Pattern**: Data transfer objects
- **Factory Pattern**: Entity creation
- **Strategy Pattern**: Tier-based multipliers

## Clean Code Principles

- Single Responsibility Principle
- Open/Closed Principle
- Dependency Inversion
- DRY (Don't Repeat Yourself)
- Meaningful names and comments
- Comprehensive test coverage
