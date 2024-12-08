#!/bin/bash

# Define the container name
CONTAINER_NAME="proxy"
OUTPUT_DIR="output"

# Start server if not running
start_server() {
  # Check if container is running
  if ! docker ps --filter "name=$CONTAINER_NAME" --format '{{.Names}}' | grep -q "$CONTAINER_NAME"; then
    echo "Starting server..."
    docker run -d --name "$CONTAINER_NAME" \
      -v "$(pwd)/$OUTPUT_DIR:/app/$OUTPUT_DIR" \
      proxy
  else
    echo "Server is already running."
  fi
}

# Stop the server
stop_server() {
  # Stop and remove the container
  echo "Stopping server..."
  docker stop "$CONTAINER_NAME" && docker rm "$CONTAINER_NAME"
}

# Start client and fetch URL
start_client() {
  if [ -z "$1" ]; then
    echo "Error: URL argument is required."
    exit 1
  fi
  URL=$1
  echo "Starting client to fetch: $URL"
  docker exec -d "$CONTAINER_NAME" java -cp /app/src ProxyClient "$URL"
}

# Command handling
case "$1" in
  build)
    echo "Building the Docker image..."
    docker build -t proxy .
    ;;
  start-server)
    start_server
    ;;
  stop-server)
    stop_server
    ;;
  start-client)
    start_client "$2"
    ;;
  *)
    echo "Usage: $0 {build|start-server|stop-server|start-client <url>}"
    exit 1
    ;;
esac

