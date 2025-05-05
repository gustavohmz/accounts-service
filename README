# 💳 Bank Accounts Microservice

This is a microservice for managing bank accounts and movements, built with **Spring Boot**, **PostgreSQL**, and **RabbitMQ**. It supports full CRUD operations, transaction handling, balance validation, and report generation.

---

## 🧱 Architecture

This microservice follows a layered architecture with clean separation of concerns:

- **Controller Layer**: Exposes HTTP endpoints.
- **Service Layer**: Implements business logic.
- **Repository Layer**: Handles persistence using Spring Data JPA.
- **DTOs & Mappers**: DTOs decouple the API from internal models.
- **Exception Handling**: Centralized using `@RestControllerAdvice`.

---

## ✅ Features

- CRUD operations for `Account` entity.
- Record account `Movements` (deposits/withdrawals).
- Balance updates and overdraft validation.
- Statement generation with filters by date and client.
- Custom exception messages (e.g. duplicate account, insufficient balance).
- Dockerized with PostgreSQL and RabbitMQ.
- Uses Feign to call `usuarios-service` and validate client existence.

---

### ⚙️ Prerequisites

- ✅ Install **Docker Desktop**: [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
- ✅ Make sure Docker Daemon is running.
- ✅ Create a shared Docker network once:

```bash
docker network create bank-network
```

---

## ▶️ How to Run

### Clone the Repository

```bash
git clone https://github.com/gustavohmz/accounts-service.git
cd accounts-service
```

### Run with Docker

```bash
docker-compose up --build
```

Access the API: [http://localhost:8081](http://localhost:8081)

---

## 🔍 API Endpoints

| Method | Endpoint                             | Description                           |
|--------|--------------------------------------|---------------------------------------|
| POST   | `/api/accounts`                      | Create a new account                  |
| GET    | `/api/accounts`                      | Get all accounts                      |
| GET    | `/api/accounts/{id}`                 | Get account by ID                     |
| PUT    | `/api/accounts/{id}`                 | Update an account                     |
| DELETE | `/api/accounts/{id}`                 | Delete an account                     |
| POST   | `/api/movements`                     | Create a new movement                 |
| GET    | `/api/movements/account/{accountId}` | List movements (optionally filtered) |
| GET    | `/api/reports`                       | Generate account statement report     |

---

## 📋 Example Request

### ➕ Create Account

```json
{
  "accountNumber": "123456710",
  "type": "Savings",
  "initialBalance": 500.00,
  "status": true,
  "clientId": 1
}
```

### ➖ Create Movement

```json
{
  "accountId": 1,
  "amount": 100.00,
  "type": "Withdrawal"
}
```

### 📈 Get Statement

```
GET /api/reports?clientId=1&from=2024-01-01&to=2025-01-01
```

---

## 🧪 Testing

Unit tests are implemented for the service layer using JUnit and Mockito.

To run tests:

```bash
mvn test
```

---

## 🚨 Exception Handling

Custom error messages are returned for:

- `404` Not Found – Missing account or movement.
- `400` Bad Request – Insufficient balance or invalid input.
- `409` Conflict – Duplicate account numbers.
- `500` Internal Server Error – Unexpected issues.

---

## 🐳 Dockerized Setup

### 📦 docker-compose.yml

```yaml
version: '3.8'

services:
  accounts-db:
    image: postgres:16
    container_name: accounts-db
    environment:
      POSTGRES_DB: accounts_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 4535
    ports:
      - "5433:5432"
    volumes:
      - accounts_data:/var/lib/postgresql/data
    networks:
      - bank-network

  rabbitmq-accounts:
    image: rabbitmq:3-management
    container_name: rabbitmq-accounts
    ports:
      - "15673:15672"
    networks:
      - bank-network

  accounts-service:
    build: .
    container_name: accounts-service
    depends_on:
      - accounts-db
      - rabbitmq-accounts
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://accounts-db:5432/accounts_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: 4535
      SPRING_RABBITMQ_HOST: rabbitmq-accounts
      SPRING_RABBITMQ_PORT: 5672
      SPRING_RABBITMQ_USERNAME: guest
      SPRING_RABBITMQ_PASSWORD: guest
      USER_SERVICE_URL: http://usuarios-service:8080
    networks:
      - bank-network

volumes:
  accounts_data:

networks:
  bank-network:
    external: true
```

---

## 📂 Project Structure

```
src
├── main
│   ├── java/com.bank.accounts
│   │   ├── controller
│   │   ├── service / impl
│   │   ├── repository
│   │   ├── model
│   │   ├── dto
│   │   ├── mapper
│   │   ├── client
│   │   └── exception
│   └── resources
│       └── application.yml
└── test
    └── service.impl
```

---

## 👤 Author

- **Name**: Gustavo Hernandez
- **GitHub**: [https://github.com/gustavohmz](https://github.com/gustavohmz)