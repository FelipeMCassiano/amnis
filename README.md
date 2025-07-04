# Amnis API
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)


Amnis is a scalable stream chat service developed in Java, utilizing Kafka for messaging and WebSockets for real-time communication. The system is designed to efficiently support live chat interactions on streaming platforms, offering robust analytics and moderation features.

---

## Endpoints by Service

### AnalyticsService

#### REST Endpoints

- **GET /analytics**
  - **Description:** Retrieves all analytics records.
  - **Parameters:** None
  - **Example:**
    ```bash
    curl https://api.example.com/analytics
    ```
  - **Response:**
    ```json
    [
      {
        "id": "abc123",
        "streamId": 321,
        "...": "..."
      }
    ]
    ```

- **GET /analytics/{streamId}/dashboard**
  - **Description:** Returns analytics dashboard for a specific stream.
  - **Parameters:**
    - `streamId` (path, required): Stream identifier.
  - **Example:**
    ```bash
    curl https://api.example.com/analytics/321/dashboard
    ```
  - **Response:**
    ```json
    {
      "chatMessageAnalytics": { "...": "..." },
      "giftAnalytics": { "...": "..." },
      "userAnalytics": { "...": "..." }
    }
    ```
  - **Errors:** `404` if analytics not found for the given stream.

---

### ModerationService

#### REST Endpoints

- **POST /mod/{streamId}**
  - **Description:** Adds keywords to the blacklist for a stream.
  - **Parameters:**
    - `streamId` (path, required)
    - **Body:** `BlackListMessagesDTO`
  - **Example:**
    ```bash
    curl -X POST https://api.example.com/mod/321 \
      -H "Content-Type: application/json" \
      -d '{"blacklist":["spamword1","spamword2"]}'
    ```
  - **Response:** `201 Created` (No body)
  - **Errors:** `400` for invalid data

- **GET /mod/{streamId}**
  - **Description:** Gets the current blacklist for a stream.
  - **Example:**
    ```bash
    curl https://api.example.com/mod/321
    ```
  - **Response:**
    ```json
    {
      "blacklist": ["spamword1", "spamword2"]
    }
    ```

- **GET /mod/{streamId}/blocked**
  - **Description:** Retrieves blocked messages for a stream.
  - **Example:**
    ```bash
    curl https://api.example.com/mod/321/blocked
    ```
  - **Response:**
    ```json
    {
      "blockedMessages": ["blocked message 1", "blocked message 2"]
    }
    ```

---

### StreamGateway

#### REST Endpoints

- **POST /streams/{streamId}/join**
  - **Description:** Registers a user joining a stream.
  - **Parameters:**
    - `streamId` (path, required)
    - **Body:** `UserJoinedDTO`
  - **Example:**
    ```bash
    curl -X POST https://api.example.com/streams/321/join \
      -H "Content-Type: application/json" \
      -d '{"username":"alice", "timestamp":"2025-07-04T19:31:10Z"}'
    ```
  - **Response:** `200 OK` (No body)
  - **Errors:** `400` for invalid data

- **POST /streams/{streamId}/gift**
  - **Description:** Sends a gift in a stream.
  - **Parameters:**
    - `streamId` (path, required)
    - **Body:** `GiftDTO`
  - **Example:**
    ```bash
    curl -X POST https://api.example.com/streams/321/gift \
      -H "Content-Type: application/json" \
      -d '{"username":"alice", "giftType":"SUPER_CHAT", "value":100, "timestamp":"2025-07-04T19:31:10Z"}'
    ```
  - **Response:** `200 OK` (No body)
  - **Errors:** `400` for invalid data

---

## How to Run Locally

Follow these steps to run the project locally:

1. **Clone the repository:**
    ```bash
    git clone https://github.com/FelipeMCassiano/amnis.git
    cd amnis
    ```

2. **Start all services using Docker Compose:**
    ```bash
    docker compose up -d
    ```

3. All services will be available at the ports configured in the `docker-compose.yml` file.

> Ensure you have [Docker](https://www.docker.com/products/docker-desktop/) and [Docker Compose](https://docs.docker.com/compose/) installed on your machine.

---
