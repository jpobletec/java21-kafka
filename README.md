# Microservices Kafka Project

This project demonstrates a microservices-based architecture using **Spring Boot**, **Kafka**, and **Docker**. It implements a producer-consumer pattern where:
- A producer sends messages to a Kafka topic.
- A consumer processes these messages and allows querying or clearing them.

---

## Features

- **Spring Boot** application with REST endpoints.
- **Kafka Producer and Consumer**.
- **Manual Offset Acknowledgment** for Kafka consumer.
- Dockerized environment using `docker-compose` for Kafka setup.
- **Endpoints**:
  - `POST /api/messages`: Send a message to Kafka.
  - `GET /api/messages/consume-all`: Retrieve all consumed messages and clear the buffer.

---

## Prerequisites

1. **Docker** and **Docker Compose** installed.
2. **Java 21**.
3. **Maven** for dependency management.

---

## Installation and Setup

### Clone the Repository
```bash
git clone https://github.com/jpobletec/java21-kafka.git
cd java21-kafka
```

### Build the Application
```bash
mvn clean install
```

### Run with Docker Compose
Start the entire environment, including Kafka and the application:
```bash
docker-compose up --build
```

---

## Configuration

### Application Configuration (`application.yml`)

- Kafka consumer and producer settings:

```yaml
spring:
  kafka:
    bootstrap-servers: kafka:9092
    consumer:
      group-id: group_id
      enable-auto-commit: false
      auto-offset-reset: earliest
```

### Kafka Topic
The application interacts with a Kafka topic named `messages`. If the topic does not exist, Kafka will create it automatically.

---

## Usage

### Monitor Kafka in Real-Time
You can monitor the `messages` topic in real-time using the Kafka UI included in the Docker setup. The Kafka UI is available at:

**URL**: [http://localhost:8082](http://localhost:8082)

Kafka UI allows you to:
- View topics and partitions.
- Monitor consumer groups.
- Send test messages.
- Explore message history.

### Send a Message to Kafka
Use the `POST /api/messages` endpoint to send a message to Kafka.

**Example Request**:
```json
{
  "id": "12345",
  "timestamp": "2024-12-30T12:34:56Z",
  "message": "Test Message",
  "metadata": {
    "source": "app1",
    "type": "notification"
  }
}
```

**Curl Command**:
```bash
curl -X POST http://localhost:8081/api/messages \
-H "Content-Type: application/json" \
-d '{
  "id": "12345",
  "timestamp": "2024-12-30T12:34:56Z",
  "message": "Test Message",
  "metadata": {
    "source": "app1",
    "type": "notification"
  }
}'
```

### Consume All Messages
Use the `GET /api/messages/consume-all` endpoint to retrieve all consumed messages and clear the buffer.

**Curl Command**:
```bash
curl -X GET http://localhost:8081/api/messages/consume-all
```

---

## Testing

Run unit tests with:
```bash
mvn test
```

Unit tests cover the following functionalities:
- Sending messages to Kafka.
- Consuming messages from Kafka.
- Clearing the buffer of consumed messages.

---

## Logs and Debugging

### View Logs
To view logs for the application:
```bash
docker-compose logs app
```

---

## Troubleshooting

### Messages Still Appear in Kafka
Kafka retains messages for a configured retention period. To avoid re-processing messages:

