# Use OpenJDK 22 base image
FROM openjdk:22-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the application source code into the container
COPY ./src /app/src

# Compile Java files
RUN javac /app/src/ProxyServer.java /app/src/ProxyClient.java

# Expose the port that the server will run on
EXPOSE 8080

# Default command: run the ProxyServer class
CMD ["java", "-cp", "/app/src", "ProxyServer"]

