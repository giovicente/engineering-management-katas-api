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
├── docker-compose.yml             # (Planned) For local DB and services
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

- **Java 17** or later (You can check your version with: `java -version`)
- **Maven** (You can check your version with: `mvn -v`)
- **PostgreSQL 12+**

## 🧪 Run locally

### Using Maven Command

To run the application locally, navigate to the root directory of the project and use the following Maven commands:

```bash
mvn clean install
```

```bash
mvn spring-boot:run -pl engineering-management-katas-api-application
```

## 📄 License

This project is open source and licensed under the MIT License.

You are free to use, modify, and distribute it, including for commercial purposes.

All profits from the frontend's advertising will go solely to the author.

## 🚧 Next Steps

- Add PostgreSQL connection with Docker
- Get random kata by language use case
- Get random kata by category and language use case
- Implement basic GUI in React (future)
