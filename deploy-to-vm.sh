#!/bin/bash
set -e

# --- CONFIGURATION ---
SSH_HOST="myvm"
VM_PATH="~/ecommerce"

echo "[1/4] Ensuring VM project directory exists..."
ssh $SSH_HOST "mkdir -p $VM_PATH"

# --- SYNC CODE TO VM ---
echo "[2/4] Syncing project files to VM..."
rsync -avz --exclude 'target' --exclude '.git' -e "ssh" ./ $SSH_HOST:$VM_PATH/

# --- CLEAN OLD CONTAINERS ON VM ---
echo "[3/4] Cleaning old containers on VM..."
ssh $SSH_HOST "cd $VM_PATH && docker compose down -v || true && docker system prune -af --volumes || true"

# --- RUN DEPLOYMENT ON VM ---
echo "[4/4] Starting new deployment..."
ssh $SSH_HOST "cd $VM_PATH && bash deploy.sh"

echo "🚀 Deployment completed successfully!"
