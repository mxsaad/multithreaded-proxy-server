# Universal Dockerfile
FROM openjdk:22-jdk

# Set working directory
WORKDIR /app

# Copy source code
COPY ./src /app/src

# Compile Java source code
RUN javac -d /app/build /app/src/**/*.java

# Default command can be overridden at runtime
CMD ["java", "-cp", "/app/build", "server.ProxyServer"]

