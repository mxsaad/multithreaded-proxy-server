#!/bin/bash

# proxyman.sh: Manages proxy server and clients

# Server details
SERVER_IMAGE="proxy_server_image"
SERVER_CONTAINER="proxy_server"
SERVER_PORT=8080

# Client details
CLIENT_IMAGE="proxy_client_image"

start_server() {
  echo "Starting proxy server..."
  docker build -t $SERVER_IMAGE .
  docker run -d --rm --name $SERVER_CONTAINER -p $SERVER_PORT:8080 $SERVER_IMAGE
}

stop_server() {
  echo "Stopping proxy server..."
  docker stop $SERVER_CONTAINER
}

start_client() {
  local url=$1
  echo "Starting proxy client for URL: $url"
  docker run --rm $CLIENT_IMAGE java -cp /app/build client.ProxyClient $SERVER_CONTAINER $SERVER_PORT $url
}

case $1 in
  start-server)
    start_server
    ;;
  stop-server)
    stop_server
    ;;
  start-client)
    if [ -z "$2" ]; then
      echo "Usage: $0 start-client <url>"
      exit 1
    fi
    start_client "$2"
    ;;
  *)
    echo "Usage: $0 {start-server|stop-server|start-client <url>}"
    exit 1
    ;;
esac

