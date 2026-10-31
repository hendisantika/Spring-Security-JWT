# JWT Security Project

[![Java CI with Gradle](https://github.com/hendisantika/Spring-Security-JWT/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/Spring-Security-JWT/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot)
![Gradle](https://img.shields.io/badge/Gradle-9.3-02303A?logo=gradle)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

This project demonstrates a secure REST API built with Spring Boot, utilizing JSON Web Tokens (JWT) for authentication
and authorization. It features user registration, login, token refresh, and a product management system protected by
role-based access control.

## Features

* **User Registration:** Allows new users to register with a full name, username, password, and role.
* **User Login:** Authenticates users and returns an access token and a refresh token.
* **Token Refresh:** Provides a mechanism to refresh access tokens using refresh tokens.
* **JWT-Based Authentication:** Secures API endpoints using JWTs (stateless sessions).
* **Role-Based Authorization:** `ROLE_USER` can read products; only `ROLE_ADMIN` can create, update, or delete them
  (via `@PreAuthorize`).
* **Product Management:** CRUD operations for products.
* **PostgreSQL Database:** Stores user and product data in a PostgreSQL database.
* **Validation:** Implements input validation for request payloads.

## Technologies

| Component           | Version / Notes                                             |
|---------------------|-------------------------------------------------------------|
| Java                | **25** (Gradle toolchain)                                   |
| Spring Boot         | 4.1.x (Spring Web, Security, Data JPA, Validation)          |
| Build tool          | Gradle 9.3 (wrapper included — no local install needed)     |
| Database            | PostgreSQL                                                  |
| JWT                 | io.jsonwebtoken (JJWT) 0.13                                 |
| Testing             | JUnit 5, Spring Security Test, Testcontainers (PostgreSQL)  |
| Others              | Lombok, Spring Boot DevTools, Spring Boot Docker Compose    |

## Setup and Installation

1. **Prerequisites:**
    * Java Development Kit (JDK) **25**
    * PostgreSQL database installed and running
    * Docker (only required to run the tests — Testcontainers starts a throwaway PostgreSQL)

2. **Clone the repository:**
   ```bash
   git clone https://github.com/hendisantika/Spring-Security-JWT.git
   cd Spring-Security-JWT
   ```

3. **Database Configuration:**
    * Open `src/main/resources/application.properties`.
    * Update the PostgreSQL connection details (`spring.datasource.url`, `spring.datasource.username`,
      `spring.datasource.password`) to match your database setup.
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/<DATABASE-NAME>
   spring.datasource.username=<USERNAME>
   spring.datasource.password=<PASSWORD>
   ```
    * The application uses `spring.jpa.hibernate.ddl-auto=update`, so the schema is created/updated on startup and
      data is persisted between restarts.

4. **JWT Configuration:**
    * `app.jwt.secret` is a Base64-encoded key used to sign tokens. **Replace it and keep it secret in production**
      (e.g. inject it via an environment variable).
    * `app.jwt.expiration` (access token, default 1 hour) and `app.jwt.refresh-expiration` (refresh token, default
      24 hours) are in milliseconds.
    ```properties
    app.jwt.secret=<BASE64-ENCODED-SECRET-AT-LEAST-256-BITS>
    app.jwt.expiration=3600000
    app.jwt.refresh-expiration=86400000
    ```

5. **Build, Test and Run:**
    ```bash
    ./gradlew build      # compile + run tests (requires Docker for Testcontainers)
    ./gradlew bootRun    # start the application
    ```

   The application will start on `http://localhost:8080` (default Spring Boot port).

## API Endpoints

All `/api/products/**` endpoints require an `Authorization: Bearer <accessToken>` header.

| Method | Endpoint                   | Access              | Description                       |
|--------|----------------------------|---------------------|-----------------------------------|
| POST   | `/api/auth/register`       | Public              | Register a new user               |
| POST   | `/api/auth/login`          | Public              | Login and receive JWT tokens      |
| POST   | `/api/auth/refresh-token`  | Public              | Get a new access token            |
| GET    | `/api/products`            | Authenticated       | List all products                 |
| GET    | `/api/products/{id}`       | Authenticated       | Get a product by id               |
| POST   | `/api/products`            | `ROLE_ADMIN`        | Create a product                  |
| PUT    | `/api/products/{id}`       | `ROLE_ADMIN`        | Update a product                  |
| DELETE | `/api/products/{id}`       | `ROLE_ADMIN`        | Delete a product                  |

### Examples

* **Register** — `POST /api/auth/register` (`role` must be `ROLE_USER` or `ROLE_ADMIN`)
  ```json
  {
    "fullName": "New User",
    "username": "newuser",
    "password": "newpassword",
    "role": "ROLE_ADMIN"
  }
  ```

* **Login** — `POST /api/auth/login`
  ```json
  {
    "username": "newuser",
    "password": "newpassword"
  }
  ```
  Response:
  ```json
  {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
  }
  ```

* **Refresh token** — `POST /api/auth/refresh-token`
  ```json
  {
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
  }
  ```

* **Create / update product** — `POST /api/products` or `PUT /api/products/{id}`
  ```json
  {
    "name": "product name",
    "price": 10
  }
  ```

* **cURL**
  ```bash
  TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
    -H 'Content-Type: application/json' \
    -d '{"username":"newuser","password":"newpassword"}' | jq -r .accessToken)

  curl http://localhost:8080/api/products -H "Authorization: Bearer $TOKEN"
  ```

## CI/CD & Dependency Management

* **GitHub Actions** (`.github/workflows/gradle.yml`): builds and tests the project on **JDK 25 (Temurin)** for every
  push and pull request to `main`, uploads test reports on failure, and submits the Gradle dependency graph on pushes
  so GitHub can raise Dependabot security alerts.
* **Dependabot** (`.github/dependabot.yml`):
    * **Gradle** — checked daily; related libraries are grouped (Spring Boot, JJWT, Testcontainers) and remaining
      minor/patch bumps are batched into a single PR. Major Spring Boot upgrades are ignored (they need manual
      migration).
    * **GitHub Actions** — checked weekly and grouped into a single PR.
    * PRs are labelled (`dependencies`, `java`, `github-actions`) and use conventional commit prefixes
      (`build(deps)`, `ci(deps)`).
    * Dependabot alerts and security updates are enabled for the repository.

## Further Development

* **More detailed error handling:** Improve error handling and provide more informative error messages.
* **Comprehensive testing:** Add more unit and integration tests for auth and product flows.
* **Enhanced security:** Consider additional security measures, such as token revocation and rate limiting.
* **API documentation:** Add OpenAPI/Swagger documentation.
