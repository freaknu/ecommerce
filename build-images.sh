#!/bin/bash
set -e

services=("AuthService" "inventory_service" "product_service" "notification_service" "order_service" "maingateway" "uploadservice" "eurekaserver")

for service in "${services[@]}"; do
  echo "🔹 Building $service..."
  cd $service
  ./mvnw clean package -DskipTests
  IMAGE_NAME=$(echo "$service" | tr '[:upper:]' '[:lower:]')
  docker build -t ${IMAGE_NAME}:latest .
  cd ..
done

echo "🎯 All images built successfully!"
