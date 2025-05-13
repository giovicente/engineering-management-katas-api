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
├── engineering-management-katas-api-application/                   # Main entrypoint module (Spring Boot App)
│   └── pom.xml
│   └── src/
│       └── main/java/
│           └── org.giovicente.engineering.management.katas.api/
│
├── engineering-management-katas-api-domain/                        # Business models and core logic
│   └── pom.xml
│   └── src/
│       └── main/java/
│           └── org.giovicente.engineering.management.katas.api.domain/
│               └── enums
│               └── model
├── engineering-management-katas-api-adapter/                       # Infrastructure: database, web, external APIs
│   └── pom.xml
│   └── src/
│       └── main/java/
│           └── org.giovicente.engineering.management.katas.api.adapter/
│
└── engineering-management-katas-api-processor/                     # Use cases and application logic
    └── pom.xml
    └── src/
        └── main/java/
            └── org.giovicente.engineering.management.katas.api.processor/
```

## ✅ Prerequisites

Before running the application, ensure you have the following installed:

- **Java 17** or later (You can check your version with: `java -version`)
- **Maven** (You can check your version with: `mvn -v`)

## 🧪 Run locally

### Using Maven Command

To run the application locally, navigate to the root directory of the project and use the following Maven command:

```bash
mvn spring-boot:run -pl engineering-management-katas-api-application
```

## 📄 License

This project is open source and licensed under the MIT License.

You are free to use, modify, and distribute it, including for commercial purposes.

All profits from the frontend's advertising will go solely to the author.

## 🚧 Next Steps

- Add PostgreSQL connection with Docker
- Create first use case: GetRandomKata
- Expose HTTP endpoint via RESTController
- Implement basic GUI in React (future)
