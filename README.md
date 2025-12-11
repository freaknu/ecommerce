# Ecommerce Microservices Platform

A production-ready, scalable ecommerce platform built with Java Spring Boot microservices, Docker, and React.js for the admin/user panel. This project demonstrates a real-world microservices architecture with service discovery, API gateway, messaging, search, and persistent storage.

---

## Table of Contents
- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Microservices](#microservices)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Deployment](#deployment)
- [Environment Variables](#environment-variables)
- [Useful Endpoints](#useful-endpoints)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview
This platform is designed for modularity, scalability, and ease of deployment. Each microservice is independently deployable and communicates via REST APIs and Kafka. The system supports user authentication, product management, order processing, inventory, notifications, and file uploads.

## Architecture
- **Backend:** Java 21, Spring Boot, Maven
- **Service Discovery:** Eureka Server
- **API Gateway:** Spring Cloud Gateway
- **Database:** MySQL
- **Search:** Elasticsearch, Kibana
- **Messaging:** Kafka, Zookeeper
- **Containerization:** Docker, Docker Compose
- **Frontend:** React.js (admin/user panel, in separate repo/folder)

```
[ React.js ]
     |
[ Main Gateway ]
     |
-------------------------------------------------------------
|   Auth   |  Product  |  Inventory  |  Order  |  Notification  |  Upload  |
| Service  |  Service  |   Service   | Service |    Service     | Service  |
-------------------------------------------------------------
     |
[ Eureka Server ]
     |
[ MySQL | Elasticsearch | Kafka | Zookeeper | Kibana ]
```

## Microservices
| Service              | Port  | Description                        |
|----------------------|-------|------------------------------------|
| Eureka Server        | 8761  | Service registry/discovery         |
| Main Gateway         | 8080  | API Gateway                        |
| Auth Service         | 8081  | Authentication & user management   |
| Product Service      | 8082  | Product catalog & search           |
| Inventory Service    | 8083  | Inventory management               |
| Order Service        | 8084  | Order processing                   |
| Notification Service | 9000  | Email/SMS notifications            |
| Upload Service       | 8087  | File/image uploads (Cloudinary)    |
| Kafka UI             | 8085  | Kafka management UI                |
| Kibana              | 5601  | Elasticsearch dashboard            |
| MySQL               | 3307  | Database                           |
| Elasticsearch       | 9200  | Search engine                      |
| Zookeeper           | 2181  | Kafka coordination                 |
| Kafka               | 9092  | Messaging broker                   |

## Tech Stack
- Java 21, Spring Boot, Maven
- Docker, Docker Compose
- MySQL, Elasticsearch, Kibana
- Kafka, Zookeeper
- Eureka, Spring Cloud Gateway
- React.js

## Getting Started

### Prerequisites
- Docker & Docker Compose installed
- Git for code updates

### Access services
- API Gateway: http://34.58.229.119:8080
- Eureka Dashboard: http://34.58.229.119:8761

## Deployment

All services are orchestrated using Docker Compose. The `deploy.sh` script builds and starts all containers. For remote deployment, use `deploy-to-vm.sh` to sync code and run deployment on your VM.

## Environment Variables
Sensitive keys (DB, Cloudinary, Google, etc.) are set in `docker-compose.yml` for local development. For production, use secrets management.

## Useful Endpoints
- **Eureka Dashboard:** `/` on port 8761 (http://34.58.229.119:8761)
- **API Gateway:** `/` on port 8080 (http://34.58.229.119:8080)
- **Kafka UI:** `/` on port 8085 (http://34.58.229.119:8085)
- **Kibana:** `/` on port 5601 (http://34.58.229.119:5601)
- **Service APIs:** Each service exposes REST endpoints on its port

## Contributing
Contributions are welcome! Please open issues or submit PRs.

## License
MIT License

## Features

- **User Authentication & Authorization**: Secure login, JWT-based authentication, Google OAuth integration, and role-based access control.

- **Product Management**: CRUD operations for products, categories, and search powered by Elasticsearch.

- **Order Management**: Place, update, and track orders with real-time status updates.

- **Inventory Management**: Stock tracking, automatic updates on order placement/cancellation, and low-stock alerts.

- **Notification Service**: Email notifications for order status, password resets, and other events (SMTP integration).

- **File & Image Uploads**: Upload and manage product images and files using Cloudinary.

- **API Gateway**: Centralized routing, load balancing, and security for all microservices.

- **Service Discovery**: Eureka server for dynamic service registration and discovery.

- **Messaging & Event-Driven Architecture**: Kafka for asynchronous communication between services (e.g., order events, notifications).

- **Admin & User Panels**: (React.js, separate repo) for managing products, orders, users, and analytics.

- **Search & Analytics**: Full-text product search and analytics dashboards via Kibana.

- **Health Checks & Monitoring**: Docker Compose healthchecks, service status endpoints, and logs for monitoring.

- **Error Handling & Logging**: Centralized error handling, custom exceptions, and structured logging.

- **Dockerized Development & Deployment**: All services containerized for easy local and cloud deployment.

- **Environment Configuration**: All sensitive configs managed via environment variables and Docker Compose.

- **Unit & Integration Testing**: JUnit-based tests for core business logic and API endpoints.
