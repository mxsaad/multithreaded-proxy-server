# Multi-threaded Proxy Server and Client

This project implements a multi-threaded proxy server and client in Java. The server accepts any URL from the client, fetches the content, and returns it to the client. The client is capable of saving the content in an HTML file. The project includes Docker support, allowing you to run both the server and clients inside Docker containers.

## Project Structure

- **`src/`**: Contains the source code for both the `ProxyServer` and `ProxyClient` classes.
- **`Dockerfile`**: A Dockerfile that builds a Docker image for the proxy server and client.
- **`proxyman.sh`**: A bash script to manage the proxy server and client inside the Docker container.

## Features

- Multi-threaded server using Java's `ExecutorService`.
- Clients can request content from any URL, and the server will fetch it and send it back.
- The content fetched is saved as HTML files in the `output/` directory.
- Dockerized setup for easy deployment and testing.
- The proxy server runs in detached mode and stays alive until stopped.
- The client can be dynamically started with a URL to fetch the content.

## Requirements

- Docker
- Java 22 JDK

## Commands

- `build`: Builds the Docker image.
- `start`: Starts the proxy server (if not already running).
- `stop`: Stops and removes the proxy server container.
- `fetch <url>`: Starts a client to fetch content from the specified URL.

### Example Usage

<details>

<summary>Using Container (Recommended)</summary>

To build the Docker image:

```bash
./proxyman.sh build
```

To start the server:

```bash
./proxyman.sh start
```

To start the client and fetch content from a URL:

```bash
./proxyman.sh fetch "https://example.com"
```

To stop the server:

```bash
./proxyman.sh stop
```

</details>

<details>

<summary>Locally</summary>

To compile the source code:

```bash
javac src/ProxyServer.java src/ProxyClient.java
```

To start the server:

```bash
java -cp src ProxyServer
```

To start the client and fetch content from a URL:

```bash
java -cp src ProxyClient "https://example.com"
```

Note: The server and client must be run in separate terminals.

</details>

The HTML files fetched by the client will be saved in the `output/` directory in the root of the project.

## Notes

- The server listens on port 8080 by default. If the port is already in use, you may need to stop the existing process or change the port in the `ProxyServer.java` class.
- The `proxyman.sh` script manages starting and stopping the server and clients within the Docker container.

