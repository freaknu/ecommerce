# 🚀 Setup Guide

Complete guide to set up and run the E-Commerce Microservices Platform locally.

---

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Environment Setup](#environment-setup)
3. [Getting Credentials](#getting-credentials)
4. [Running the Application](#running-the-application)
5. [Verification](#verification)
6. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### Required Software

| Software | Version | Download Link |
|----------|---------|---------------|
| **Docker** | 20+ | [docker.com](https://www.docker.com/get-started) |
| **Docker Compose** | 2+ | Included with Docker Desktop |
| **Git** | Latest | [git-scm.com](https://git-scm.com/downloads) |

### Optional (for local development)

| Software | Version | Download Link |
|----------|---------|---------------|
| Java JDK | 21 | [Adoptium](https://adoptium.net/) |
| Maven | 3.8+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| IntelliJ IDEA | Latest | [jetbrains.com](https://www.jetbrains.com/idea/) |

### System Requirements

- **OS**: Windows 10/11, macOS 10.15+, or Linux
- **RAM**: 8GB minimum (16GB recommended)
- **Disk**: 10GB free space
- **Network**: Internet connection for downloading dependencies

---

## Environment Setup

### 1. Clone the Repository

```bash
git clone https://github.com/freaknu/ecommerce.git
cd ecommerce
```

### 2. Create Environment File

```bash
# Copy the example file
cp .env.example .env
```

### 3. Edit Environment Variables

Open `.env` in your favorite editor and fill in the values:

```bash
nano .env
# or
code .env  # VS Code
# or
vim .env
```

---

## Getting Credentials

### 🔑 Database (Required)

**Action**: Create a strong password

```bash
# In .env file
MYSQL_ROOT_PASSWORD=YourStr0ngP@ssw0rd123!
MYSQL_DATABASE=microservices_db
MYSQL_USER=admin
MYSQL_PASSWORD=AnotherStr0ngP@ssw0rd!
```

**Tips:**
- Use a password manager to generate strong passwords
- Mix uppercase, lowercase, numbers, and special characters
- Minimum 16 characters recommended

---

### 🔐 JWT Secret (Required)

**Action**: Generate a secure random key

```bash
# Generate 256-bit secret (recommended)
openssl rand -base64 64
```

Copy the output and add to `.env`:

```bash
JWT_SECRET=<paste_the_generated_key_here>
JWT_EXPIRATION=1800000
REFRESH_TOKEN_EXPIRATION=4320000000
OTP_TOKEN_EXPIRATION=600000
```

---

### 🔵 Google OAuth2 (Optional, but recommended)

**Steps:**

1. **Go to Google Cloud Console**  
   → [console.cloud.google.com](https://console.cloud.google.com/)

2. **Create a new project** or select existing

3. **Enable Google+ API**
   - Navigate to "APIs & Services" → "Library"
   - Search for "Google+ API"
   - Click "Enable"

4. **Create OAuth2 credentials**
   - Go to "APIs & Services" → "Credentials"
   - Click "Create Credentials" → "OAuth client ID"
   - Application type: "Web application"
   - Authorized redirect URIs: 
     - `http://localhost:8081/login/oauth2/code/google`
     - `https://yourdomain.com/login/oauth2/code/google` (for production)

5. **Copy credentials to .env**

```bash
GOOGLE_CLIENT_ID=your-client-id.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=your-client-secret
```

**Skip OAuth2?** Leave empty, social login will be disabled.

---

### 📧 Email Service (Required for notifications)

**Using Gmail:**

1. **Enable 2-Factor Authentication** on your Google account  
   → [myaccount.google.com/security](https://myaccount.google.com/security)

2. **Generate App Password**
   - Go to Google Account → Security
   - Select "App passwords"
   - Generate password for "Mail" on "Other device"
   - Copy the 16-character password

3. **Add to .env:**

```bash
SPRING_MAIL_USERNAME=your.email@gmail.com
SPRING_MAIL_PASSWORD=xxxx xxxx xxxx xxxx  # App password
```

**Using Other SMTP:**

```bash
SPRING_MAIL_HOST=smtp.your-provider.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your-email
SPRING_MAIL_PASSWORD=your-password
```

---

### ☁️ Cloudinary (Required for image uploads)

1. **Create free account**  
   → [cloudinary.com/users/register/free](https://cloudinary.com/users/register/free)

2. **Get credentials from dashboard**
   - After login, you'll see your Cloud name, API Key, and API Secret

3. **Add to .env:**

```bash
CLOUDINARY_CLOUD_NAME=your-cloud-name
CLOUDINARY_API_KEY=123456789012345
CLOUDINARY_API_SECRET=your-api-secret
```

**Free tier includes:**
- 25 GB storage
- 25 GB bandwidth/month
- Perfect for development and small projects

---

## Running the Application

### Option 1: One-Command Deploy (Recommended)

```bash
# Make script executable
chmod +x deploy.sh

# Run deployment
./deploy.sh
```

This will:
1. Stop existing containers
2. Build all services
3. Create Docker images
4. Start all containers
5. Show deployment status

---

### Option 2: Manual Step-by-Step

#### Step 1: Build Services

```bash
# Make build script executable
chmod +x build-images.sh

# Build all Docker images
./build-images.sh
```

This compiles each microservice and creates Docker images.

**Expected output:**
```
🔹 Building AuthService...
🔹 Building inventory_service...
🔹 Building product_service...
...
🎯 All images built successfully!
```

#### Step 2: Start Services

```bash
# Start all containers
docker-compose up -d

# View logs (optional)
docker-compose logs -f

# Stop viewing logs: Ctrl+C
```

#### Step 3: Check Status

```bash
# Check all services
docker-compose ps

# Should see all services "Up" and healthy
```

---

### Option 3: Local Development (Single Service)

Run individual service without Docker:

```bash
# Navigate to service directory
cd AuthService

# Run with Spring Boot
./mvnw spring-boot:run

# Or with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## Verification

### 1. Check Docker Containers

```bash
docker-compose ps
```

**Expected output:**
All services should show "Up" status:
```
NAME                STATUS
eureka-server       Up (healthy)
main-gateway        Up
auth-service        Up
product-service     Up
inventory-service   Up
order-service       Up
notification-service Up
upload-service      Up
mysql               Up (healthy)
elasticsearch       Up (healthy)
kafka               Up
zookeeper           Up
kibana              Up
kafka-ui            Up
```

---

### 2. Access Dashboards

Open in your browser:

| Service | URL | Status Check |
|---------|-----|--------------|
| **Eureka Dashboard** | http://localhost:8761 | Should show all services registered |
| **API Gateway** | http://localhost:8080/actuator/health | Should return `{"status":"UP"}` |
| **Swagger UI** | http://localhost:8080/swagger-ui/index.html | Should show API docs |
| **Kafka UI** | http://localhost:8085 | Should show topics |
| **Kibana** | http://localhost:5601 | Should load dashboard |

---

### 3. Test API Endpoints

#### Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!",
    "firstName": "Test",
    "lastName": "User"
  }'
```

**Expected response:**
```json
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresIn": 1800000
}
```

#### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!"
  }'
```

#### Get Products

```bash
curl -X GET http://localhost:8080/api/products
```

---

### 4. View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth-service
docker-compose logs -f product-service

# Last 100 lines
docker-compose logs --tail=100 auth-service
```

---

## Troubleshooting

### Issue: Port Already in Use

**Error:** `Bind for 0.0.0.0:8080 failed: port is already allocated`

**Solution:**
```bash
# Find and kill process using the port
# On macOS/Linux:
lsof -ti:8080 | xargs kill -9

# On Windows:
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Or change port in docker-compose.yml
```

---

### Issue: MySQL Won't Start

**Error:** `ERROR: Connection refused`

**Solution:**
```bash
# Remove old volumes and restart
docker-compose down -v
docker-compose up -d mysql

# Wait for healthy status
docker-compose ps mysql
```

---

### Issue: Service Not Registering with Eureka

**Symptom:** Service not appearing in Eureka dashboard

**Solution:**
```bash
# Check service logs
docker-compose logs service-name

# Restart Eureka and the service
docker-compose restart eureka-server
docker-compose restart service-name

# Wait 30 seconds and check again
```

---

### Issue: Out of Memory

**Error:** `java.lang.OutOfMemoryError: Java heap space`

**Solution:**
```bash
# Increase Docker memory
# Docker Desktop → Settings → Resources → Memory
# Set to at least 8GB

# Or reduce number of running services
docker-compose up -d eureka-server main-gateway auth-service product-service
```

---

### Issue: Build Fails

**Error:** `mvnw: Permission denied`

**Solution:**
```bash
# Make mvnw executable
chmod +x AuthService/mvnw
chmod +x product_service/mvnw
# ... repeat for all services

# Or use build script
chmod +x build-images.sh
./build-images.sh
```

---

### Issue: Can't Access Services

**Symptom:** `Connection refused` when accessing http://localhost:8080

**Solution:**
```bash
# Check if containers are running
docker-compose ps

# Check gateway logs
docker-compose logs main-gateway

# Restart gateway
docker-compose restart main-gateway

# If still not working, rebuild
docker-compose down
docker-compose up -d --build
```

---

### Issue: Elasticsearch Won't Start

**Error:** `max virtual memory areas vm.max_map_count [65530] is too low`

**Solution:**

**On Linux:**
```bash
sudo sysctl -w vm.max_map_count=262144
```

**On macOS/Windows Docker Desktop:**
```bash
# Already configured, restart Docker Desktop
```

---

### Getting Help

If you encounter issues not listed here:

1. **Check logs:**
   ```bash
   docker-compose logs service-name
   ```

2. **Search existing issues:**  
   [github.com/freaknu/ecommerce/issues](https://github.com/freaknu/ecommerce/issues)

3. **Create new issue:**  
   Provide:
   - Error message
   - Logs output
   - OS and Docker version
   - Steps to reproduce

---

## Next Steps

Once everything is running:

1. ✅ Explore the API using Swagger UI
2. ✅ Test authentication flow
3. ✅ Create products and categories
4. ✅ Place test orders
5. ✅ Monitor services in Eureka
6. ✅ Check Kafka messages in Kafka UI
7. ✅ View logs in Kibana

---

## Development Tips

### Hot Reload for Development

```bash
# Run service with Spring DevTools
cd AuthService
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Changes will auto-reload
```

### Database Access

```bash
# Connect to MySQL
docker exec -it mysql mysql -uroot -p

# Enter password from .env
# Then:
SHOW DATABASES;
USE product_service;
SHOW TABLES;
```

### Reset Everything

```bash
# Stop and remove all containers, networks, volumes
docker-compose down -v

# Remove all images
docker-compose down --rmi all

# Start fresh
./deploy.sh
```

---

**Happy Coding! 🚀**

For more information, see the main [README.md](README.md)
