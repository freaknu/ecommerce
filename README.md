# 🛒 Enterprise E-Commerce Microservices Platform

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4%20%7C%204.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Flutter](https://img.shields.io/badge/Flutter-Mobile%20App-02569B.svg)](https://flutter.dev/)
[![React](https://img.shields.io/badge/React.js-Admin%20Panel-61DAFB.svg)](https://reactjs.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)](https://www.docker.com/)
[![Microservices](https://img.shields.io/badge/Architecture-Microservices-purple.svg)](https://microservices.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Live Demo](https://img.shields.io/badge/Live-Demo-success.svg)](https://shopendingecommerce.duckdns.org)

> **A production-ready, enterprise-grade e-commerce ecosystem** featuring Flutter mobile app (iOS/Android), React.js admin panel, and Java Spring Boot microservices backend. Deployed on GCP with Nginx, demonstrating real-world distributed systems architecture.

---

## 🌟 Live Demo

<div align="center">

| Resource | Link |
|----------|------|
| **🌐 Live Application** | [https://shopendingecommerce.duckdns.org](https://shopendingecommerce.duckdns.org) |
| **📚 API Documentation** | [Swagger UI](https://shopendingecommerce.duckdns.org/swagger-ui/index.html) |
| **📊 Service Discovery** | [Eureka Dashboard](http://34.58.229.119:8761) |

**Infrastructure:** Google Cloud Platform • Nginx • Docker • DuckDNS • SSL/TLS

</div>

---

## 📋 Table of Contents

- [🎯 Project Overview](#-project-overview)
- [✨ Key Features](#-key-features)
- [🏗️ System Architecture](#️-system-architecture)
- [🔧 Technology Stack](#-technology-stack)
- [📦 Microservices](#-microservices)
- [🚀 Getting Started](#-getting-started)
- [📡 API Documentation](#-api-documentation)
- [🔐 Security](#-security)
- [📊 Monitoring](#-monitoring)
- [🎓 Learning Outcomes](#-learning-outcomes)
- [📞 Contact](#-contact)

---

## 🎯 Project Overview

A **complete, production-deployed e-commerce platform** showcasing enterprise-level expertise:

### **Multi-Platform Ecosystem**
- 📱 **Flutter Mobile App** - Native iOS & Android customer application
- 💻 **React.js Admin Panel** - Web-based business management dashboard  
- ☁️ **Java Microservices** - 8 independent, scalable backend services

### **Enterprise Architecture**
- **Microservices** - Service-oriented architecture with bounded contexts
- **Service Discovery** - Dynamic registration with Netflix Eureka
- **API Gateway** - Centralized routing, security, and load balancing
- **Event-Driven** - Asynchronous communication using Apache Kafka
- **Full-Text Search** - Product search powered by Elasticsearch
- **Cloud Deployment** - Production-ready on GCP with Nginx

### **Real-World Implementation**
✅ Live Production Deployment on Google Cloud Platform  
✅ SSL/TLS Secured with proper domain configuration  
✅ Horizontally Scalable architecture  
✅ High Availability with health checks and auto-restart  
✅ Distributed Tracing ready  
✅ CI/CD ready structure  

---

## ✨ Key Features

<table>
<tr>
<td width="50%">

### 🎫 Authentication
- JWT-based stateless auth
- OAuth2 Google login
- Role-based access (RBAC)
- Refresh token rotation
- OTP password reset
- BCrypt encryption

### 🛍️ Products
- Full-text search (Elasticsearch)
- Advanced filtering
- Reviews & ratings
- Discount management
- Multi-image upload
- AI recommendations (OpenAI)

### 📦 Orders
- Shopping cart
- Multi-step checkout
- Order tracking
- Payment processing
- Status notifications
- Address management

</td>
<td width="50%">

### 📊 Inventory
- Real-time stock tracking
- Low stock alerts
- Auto-updates on orders
- Stock reservation
- Multi-warehouse ready

### 📧 Notifications
- Email (SMTP)
- Push (Firebase FCM)
- SMS ready
- Event-driven via Kafka
- Notification history

### 📸 Media
- Cloud storage (Cloudinary)
- Auto optimization
- CDN delivery
- Multi-format support

</td>
</tr>
</table>

---

## 🏗️ System Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                    CLIENT APPLICATIONS                        │
├─────────────────┬──────────────────┬─────────────────────────┤
│  📱 Flutter App │ 💻 React Admin   │  🌐 External APIs       │
│  (iOS/Android)  │    Dashboard     │                         │
└─────────────────┴──────────────────┴─────────────────────────┘
                          │
                   HTTPS (SSL/TLS)
                          │
┌─────────────────────────▼─────────────────────────────────────┐
│          🌐 NGINX REVERSE PROXY (GCP)                         │
│     https://shopendingecommerce.duckdns.org                   │
│  • SSL Termination  • Load Balancing  • Rate Limiting        │
└───────────────────────┬───────────────────────────────────────┘
                        │
┌───────────────────────▼───────────────────────────────────────┐
│         🚪 API GATEWAY (Spring Cloud Gateway)                 │
│  • JWT Validation  • Circuit Breaker  • Request Routing      │
└───────┬───────────────────────────────────────────────────────┘
        │
        ├─────► Eureka (Service Discovery) ─────► Kafka (Events)
        │
┌───────▼─────────────────────────────────────────────────────┐
│              MICROSERVICES LAYER (Java 21)                   │
│                                                              │
│  Auth (8081)  │  Product (8082)  │  Inventory (8083)        │
│  Order (8084) │  Notification (9000)  │  Upload (8087)      │
│                                                              │
│  • REST APIs  • Business Logic  • Event Publishing          │
└───────┬──────────────────────────────────────────────────────┘
        │
┌───────▼──────────────────────────────────────────────────────┐
│                   DATA & INFRASTRUCTURE                       │
│                                                              │
│  MySQL (3307)  │  Elasticsearch (9200)  │  Kafka (9092)     │
│  Cloudinary    │  Kibana (5601)         │  Kafka UI (8085)  │
└──────────────────────────────────────────────────────────────┘
```

### Communication Patterns

| Pattern | Technology | Use Case |
|---------|-----------|----------|
| **Sync** | REST APIs | Client requests, queries |
| **Async** | Kafka | Order events, notifications |
| **Discovery** | Eureka | Service registration |
| **Resilience** | Resilience4j | Circuit breaking, retries |

---

## 🔧 Technology Stack

### Backend & Core
| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 21 (LTS) | Programming language |
| Spring Boot | 3.4 / 4.0 | Application framework |
| Spring Cloud | 2024.0 / 2025.1 | Microservices tools |
| Spring Security | Latest | Auth & authorization |
| Spring Data JPA | Latest | Data persistence |
| Hibernate | Latest | ORM |

### Mobile & Frontend
| Technology | Purpose |
|------------|---------|
| Flutter 3.x | iOS/Android mobile app |
| React.js 18+ | Admin dashboard |
| Redux/Context API | State management |
| Material-UI | UI components |
| Firebase FCM | Push notifications |

### Infrastructure & Data
| Technology | Version | Purpose |
|------------|---------|---------|
| MySQL | 8.0 | Primary database |
| Elasticsearch | 8.15 | Search engine |
| Apache Kafka | 7.5 | Event streaming |
| Zookeeper | 7.5 | Kafka coordination |
| Redis | Ready | Caching |

### DevOps & Cloud
| Technology | Purpose |
|------------|---------|
| **GCP** | Production hosting |
| **Nginx** | Reverse proxy |
| **Docker** | Containerization |
| **Docker Compose** | Orchestration |
| **Maven** | Build tool |
| **Cloudinary** | Media CDN |

### Security & Resilience
| Technology | Purpose |
|------------|---------|
| JWT | Token auth |
| OAuth2 | Social login |
| BCrypt | Password hashing |
| Resilience4j | Circuit breakers |
| Spring Security | Framework |

---

## 📦 Microservices

### 1️⃣ Eureka Server (8761)
**Service Discovery & Registry**

- Service registration/deregistration
- Health monitoring
- Dashboard visualization

### 2️⃣ API Gateway (8080)
**Entry Point & Security**

- JWT validation
- Dynamic routing
- Circuit breaker
- Rate limiting
- CORS handling
- Load balancing

### 3️⃣ Auth Service (8081)
**Authentication & Users**

- User registration/login
- JWT tokens (30min access, 50day refresh)
- OAuth2 Google login
- OTP password reset
- RBAC (USER, ADMIN, SELLER)

**Endpoints:**
```
POST /api/auth/register
POST /api/auth/login
POST /api/auth/refresh
GET  /api/auth/oauth2/google
```

### 4️⃣ Product Service (8082)
**Catalog & Search**

- Product CRUD
- Category management
- Elasticsearch search
- Reviews & ratings
- Discount system
- AI recommendations

**Endpoints:**
```
GET  /api/products
GET  /api/products/search?q={query}
POST /api/products (ADMIN)
GET  /api/categories
POST /api/reviews
```

### 5️⃣ Inventory Service (8083)
**Stock Management**

- Real-time tracking
- Stock reservation
- Low stock alerts
- Reactive WebFlux calls

**Endpoints:**
```
GET  /api/inventory/{productId}
POST /api/inventory/reserve
POST /api/inventory/release
```

### 6️⃣ Order Service (8084)
**Orders & Cart**

- Shopping cart
- Order placement
- Payment tracking
- Status management
- Address handling

**Endpoints:**
```
POST /api/cart/add
GET  /api/cart
POST /api/orders
GET  /api/orders
PUT  /api/orders/{id}/cancel
```

### 7️⃣ Notification Service (9000)
**Multi-Channel Alerts**

- Email (SMTP)
- Push (FCM)
- SMS ready
- Kafka consumer
- Template engine

**Topics:**
```
order_topic - Order events
otp_event   - OTP codes
```

### 8️⃣ Upload Service (8087)
**Media Management**

- Cloudinary storage
- Image optimization
- CDN delivery
- Multi-file uploads

---

## 🚀 Getting Started

### Prerequisites
- Docker 20+ & Docker Compose 2+
- Java 21 (optional, for local dev)
- 8GB RAM minimum

### Quick Start

```bash
# 1. Clone repository
git clone https://github.com/freaknu/ecommerce.git
cd ecommerce

# 2. Setup environment
cp .env.example .env
# Edit .env with your credentials

# 3. Deploy all services
./deploy.sh

# 4. Verify deployment
docker-compose ps
```

### Environment Configuration

Create `.env` file with:

```bash
# Database
MYSQL_ROOT_PASSWORD=your_secure_password
MYSQL_DATABASE=microservices_db

# JWT
JWT_SECRET=your_256_bit_secret_key

# OAuth2
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret

# Email
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_app_password

# Cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

### Access Services

| Service | URL |
|---------|-----|
| API Gateway | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| Eureka | http://localhost:8761 |
| Kafka UI | http://localhost:8085 |
| Kibana | http://localhost:5601 |
| MySQL | localhost:3307 |

---

## 📡 API Documentation

### Interactive Swagger UI
**Live:** [https://shopendingecommerce.duckdns.org/swagger-ui/index.html](https://shopendingecommerce.duckdns.org/swagger-ui/index.html)

### Authentication Flow

```bash
# 1. Register
POST https://shopendingecommerce.duckdns.org/api/auth/register
{
  "email": "user@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe"
}

# 2. Login
POST https://shopendingecommerce.duckdns.org/api/auth/login
{
  "email": "user@example.com",
  "password": "SecurePass123!"
}

# Response
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresIn": 1800000
}

# 3. Use token
GET https://shopendingecommerce.duckdns.org/api/products
Authorization: Bearer {accessToken}
```

### Common Endpoints

```bash
# Search products
GET /api/products/search?q=laptop

# Filter products
GET /api/products?category=electronics&minPrice=100&maxPrice=1000

# Add to cart
POST /api/cart/add
Authorization: Bearer {token}

# Create order
POST /api/orders
Authorization: Bearer {token}
```

### Error Responses

```json
{
  "timestamp": "2025-01-24T10:15:30.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/products"
}
```

**Status Codes:**
- `200` - Success
- `201` - Created
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Server Error

---

## 🔐 Security

### Authentication
- **JWT Tokens**: RS256 algorithm
- **Access Token**: 30-minute expiry
- **Refresh Token**: 50-day expiry
- **OAuth2**: Google integration
- **OTP**: 10-minute expiry

### Authorization
```
ROLE_USER   - Customer access
ROLE_ADMIN  - Full system access
ROLE_SELLER - Product management
```

### Security Features
✅ Gateway-level JWT validation  
✅ Role-based access control  
✅ BCrypt password hashing  
✅ Input validation  
✅ SQL injection prevention  
✅ CORS configuration  
✅ Rate limiting  
✅ HTTPS/TLS (production)  

### Production Setup
- Nginx reverse proxy
- SSL/TLS encryption
- Firewall rules (GCP)
- Private network
- Environment-based secrets

---

## 📊 Monitoring

### Health Checks
```bash
# Gateway health
curl https://shopendingecommerce.duckdns.org/actuator/health

# Eureka dashboard
open http://34.58.229.119:8761
```

### Log Management
- **Kibana**: [http://34.58.229.119:5601](http://34.58.229.119:5601)
- Centralized logging
- Real-time log streaming
- Custom dashboards

### Message Queue
- **Kafka UI**: [http://34.58.229.119:8085](http://34.58.229.119:8085)
- Topic monitoring
- Consumer lag tracking
- Message inspection

### Metrics
- Request throughput
- Response times
- Error rates
- JVM metrics
- Database connections

---

## 🎓 Learning Outcomes

### Architecture Skills
✅ Microservices design  
✅ Service decomposition  
✅ Event-driven architecture  
✅ API Gateway pattern  
✅ Circuit breaker pattern  
✅ CQRS principles  

### Technology Expertise
✅ Spring Boot 3.x/4.x  
✅ Spring Cloud (Gateway, Eureka)  
✅ Spring Security (JWT, OAuth2)  
✅ Spring Data JPA  
✅ Apache Kafka  
✅ Elasticsearch  
✅ Docker & Docker Compose  

### Multi-Platform Development
✅ Flutter (iOS/Android)  
✅ React.js  
✅ RESTful APIs  
✅ State management  
✅ Push notifications  

### DevOps & Cloud
✅ GCP deployment  
✅ Nginx configuration  
✅ SSL/TLS setup  
✅ Container orchestration  
✅ Health monitoring  

### Best Practices
✅ Security (auth, encryption)  
✅ Error handling  
✅ Logging & monitoring  
✅ Code organization  
✅ API documentation  

---

## 🎯 For Employers

**This project demonstrates:**

| Skill | Level | Evidence |
|-------|-------|----------|
| Architecture | **Senior** | 8 microservices, proper patterns |
| Java/Spring | **Advanced** | Boot 3/4, Cloud, Security |
| Multi-Platform | **Advanced** | Flutter + React + Backend |
| DevOps | **Intermediate** | GCP, Docker, Nginx |
| Security | **Advanced** | JWT, OAuth2, RBAC |
| Real-World | **Production** | Live deployment on GCP |

**Equivalent to 4-5 years of experience** in terms of technical breadth and depth.

---

## 📞 Contact

**Developer:** Prabhat Kumar  
**Repository:** [github.com/freaknu/ecommerce](https://github.com/freaknu/ecommerce)

### Links
- 🌐 **Live Demo**: [https://shopendingecommerce.duckdns.org](https://shopendingecommerce.duckdns.org)
- 📚 **API Docs**: [Swagger UI](https://shopendingecommerce.duckdns.org/swagger-ui/index.html)
- 📊 **Eureka**: [Dashboard](http://34.58.229.119:8761)
- 💻 **GitHub**: [Repository](https://github.com/freaknu/ecommerce)

### Project Structure
```
ecommerce/
├── AuthService/           # Auth & users
├── product_service/       # Products & search
├── inventory_service/     # Stock management
├── order_service/         # Orders & cart
├── notification_service/  # Notifications
├── uploadservice/         # Media upload
├── maingateway/          # API Gateway
├── eurekaserver/         # Service discovery
├── docker-compose.yml    # Container config
├── deploy.sh             # Deploy script
└── .env.example          # Environment template
```

---

## 🤝 Contributing

Contributions welcome!

1. Fork the repository
2. Create feature branch (`git checkout -b feature/Amazing`)
3. Commit changes (`git commit -m 'Add Amazing'`)
4. Push to branch (`git push origin feature/Amazing`)
5. Open Pull Request

---

## 📄 License

MIT License - see [LICENSE](LICENSE) file

---

## 🙏 Acknowledgments

Spring Framework • Netflix OSS • Elasticsearch • Apache Kafka • Docker • Google Cloud Platform • Cloudinary

---

<div align="center">

### ⭐ Star this repo if you find it helpful!

**Built with ❤️ using Java, Spring Boot, Flutter, React & Microservices**

[![GitHub stars](https://img.shields.io/github/stars/freaknu/ecommerce?style=social)](https://github.com/freaknu/ecommerce)

---

**🚀 Deployed on GCP | 🔒 SSL/TLS Secured | ⚡ Microservices Architecture**

</div>
