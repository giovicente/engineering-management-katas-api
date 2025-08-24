# Engineering Management Katas API

A Spring Boot API designed to serve randomized behavioral questions, strategic cases, and reflection prompts for Engineering Managers.  

Useful for self-assessment, mentoring, team alignment, and leadership practice sessions.

---

## ✨ Purpose

This API returns a random case, question, or leadership kata from a growing database.  

It supports Engineering Managers and aspiring leaders in preparing for real-world scenarios, interviews, and day-to-day leadership decisions.  

The goal is to foster reflection, critical thinking, and discussion around people management and strategic challenges in tech teams.

---

## 📁 Project Structure (Clean Architecture + Multi-Module)

```plaintext
engineering-management-katas-api/
│
├── pom.xml                        # Parent pom
├── README.md                      # Project description
├── LICENSE                        # Project license
├── .env                           # Environment Variables
├── Dockerfile                     # App image
├── docker-compose.yml             # For local DB and services
├── docker/db/                     # For initial database setup file
│   └── seed_katas.sql
│
├── engineering-management-katas-api-adapter/                       # Infrastructure: database, web, external APIs
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/org/giovicente/engineering/management/katas/api/adapter/
│       │       ├── mapper/
│       │       └── persistence/
│       │           ├── entity/
│       │           └── repository/
│       └── test/
│           └── java/org/giovicente/engineering/management/katas/api/adapter/tests
│               ├── mapper/
│               └── persistence/
│
├── engineering-management-katas-api-application/                   # Main entrypoint module (Spring Boot App)
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/org/giovicente/engineering/management/katas/api/
│           └── resources/
│
├── engineering-management-katas-api-controller/                    # Http Request and Response, orchestration
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/org/giovicente/engineering/management/katas/api/controller 
│       │       ├── dto/
│       │       ├── error/
│       │       └── mapper/
│       └── test/
│           └── java/org/giovicente/engineering/management/katas/api/controller/tests/
│
├── engineering-management-katas-api-core/                          # Interfaces for business logic
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/org/giovicente/engineering/management/katas/api/core/
│               ├── processor/
│               └── strategy/
│
├── engineering-management-katas-api-domain/                        # Business models
│   ├── pom.xml
│   └── src/
│       └── main/
│           └── java/org/giovicente/engineering/management/katas/api/domain/
│               ├── enums/
│               └── model/
│
└── engineering-management-katas-api-processor/                     # Use cases and application logic
    ├── pom.xml
    └── src/
        ├── main/
        │   └── java/org/giovicente/engineering/management/katas/api/processor
        │       └── strategy
        │           ├── handler/
        │           └── impl/
        └── test/
            └── java/org/giovicente/engineering/management/katas/api/processor/tests/
                └── strategy
                    ├── handler/
                    └── impl/
```

## ✅ Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17** or later  
  *(Check with: `java -version`)*
- **Maven**  
  *(Check with: `mvn -v`)*
- **PostgreSQL 12+**

## 🧪 Run locally

### Using Maven Command (without Docker)

1️⃣ Start your local PostgreSQL instance.  
2️⃣ Make sure your `application.yaml` is configured with the correct database URL, username, and password.  
3️⃣ Build and run the Spring Boot application:
```bash
mvn clean install
```

```bash
mvn spring-boot:run -pl engineering-management-katas-api-application
```

### 🐳 Running with Docker Compose

You can also run the entire stack (PostgreSQL + Spring Boot app) using Docker Compose:

#### First time (or after changing the Dockerfile)
```bash
docker-compose up --build
```

This command will:
- Build the Spring Boot application inside a container.
- Spin up a PostgreSQL container with the provided seed data.
- Start both services in a shared Docker network.

#### ⚠️ Use `--build` only when:

- It's the first run.
- You made changes to the Dockerfile.
- You updated dependencies in pom.xml (or Gradle).
- You modified resources that affect the image build (e.g., application.yaml copied inside the image).

#### Next times (or after changing the Dockerfile)
```bash
docker compose up
```

This will simply start the containers using the already built images.

#### ⚙️ Configuration
- The app uses environment variables defined in the .env file.
- Make sure your .env contains the correct database credentials and connection URL for Docker:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/engineering_management_katas
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=your_password_here
```

#### ✅ Notes
- The Docker build uses Maven to generate a Spring Boot fat jar with the correct Main-Class.
- The PostgreSQL service runs with an initialization SQL script located at docker/db/seed_katas.sql.
- Volumes are used to persist PostgreSQL data between container restarts.

## 📄 License

This project is open source and licensed under the MIT License.

You are free to use, modify, and distribute it, including for commercial purposes.

All profits from the frontend's advertising will go solely to the author.

## 🚧 Next Steps

- [X] Implement null handling with Optional
- [ ] Implement basic GUI in React
- [ ] Deploy this entire project as a website
