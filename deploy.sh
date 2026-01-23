#!/bin/bash
set -e

echo "Stopping existing containers..."
docker-compose down || true

echo "Building microservices..."
docker-compose build --no-cache

echo "Starting containers..."
docker-compose up -d

echo "Deployment Done! 🚀"
docker-compose ps
