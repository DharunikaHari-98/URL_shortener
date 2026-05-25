# URL Shortener Service

## Overview

This project is a backend URL Shortener service built using Spring Boot.

It converts long URLs into short, shareable links and redirects users back to the original URL when the short link is accessed.

The project demonstrates backend fundamentals such as REST API design, Base62 short-code generation, hash-based lookup, HTTP redirection, validation, expiry handling, click analytics, and thread-safe storage.

---

## Features

- Generate short URLs for long links
- Redirect short URLs to original URLs using HTTP 302
- Custom alias support
- URL validation
- Expiry time support
- Click count analytics
- Thread-safe in-memory storage
- Fast O(1) lookup using hash-based mapping
- Clean REST API design
- Embedded Tomcat server

---

## Tech Stack

- Java
- Spring Boot
- Maven
- REST APIs
- ConcurrentHashMap
- Embedded Tomcat

---

## High-Level Architecture

```text
Client
  ↓
Spring Boot REST Controller
  ↓
URL Shortener Service
  ↓
Thread-safe URL Mapping Store
  ↓
HTTP 302 Redirect
```

---

## How It Works

1. User sends a long URL to the `/shorten` endpoint.
2. The service validates the URL.
3. If a custom alias is provided, it checks whether the alias is already used.
4. If no alias is provided, the service generates a random Base62 short code.
5. The short code and original URL are stored in a thread-safe map.
6. When the short URL is accessed, the service checks whether it exists and has not expired.
7. If valid, it increments the click count and redirects the user to the original URL.

---

## API Endpoints

### 1. Shorten URL

```http
POST /shorten
```

#### Request Body

```json
{
  "longUrl": "https://www.google.com",
  "customAlias": "google",
  "expiryMinutes": 10
}
```

#### Response

```json
{
  "shortUrl": "http://localhost:8080/u/google",
  "shortCode": "google",
  "longUrl": "https://www.google.com",
  "expiresAt": "2026-05-25T18:30:00"
}
```

---

### 2. Redirect Short URL

```http
GET /u/{code}
```

Example:

```http
http://localhost:8080/u/google
```

If the code exists and is not expired, the service redirects to the original URL using HTTP 302.

---

### 3. URL Analytics

```http
GET /analytics/{code}
```

Example:

```http
http://localhost:8080/analytics/google
```

#### Example Response

```json
{
  "shortCode": "google",
  "longUrl": "https://www.google.com",
  "createdAt": "2026-05-25T18:20:00",
  "expiresAt": "2026-05-25T18:30:00",
  "clickCount": 3
}
```

---

## How to Run Locally

### Prerequisites

- Java 17 or higher
- IntelliJ IDEA
- Maven

### Steps

1. Clone the repository:

```bash
git clone https://github.com/DharunikaHari-98/URL_shortener.git
```

2. Open the project in IntelliJ IDEA.

3. Run the main class:

```java
UrlShortenerApplication.java
```

4. Tomcat will start on:

```http
http://localhost:8080
```

---

## Testing Using PowerShell

### Create Short URL

```powershell
curl -Method POST http://localhost:8080/shorten `
-Headers @{"Content-Type"="application/json"} `
-Body '{"longUrl":"https://www.google.com","customAlias":"google","expiryMinutes":10}'
```

### Open Short URL

```http
http://localhost:8080/u/google
```

### Check Analytics

```http
http://localhost:8080/analytics/google
```

---

## Scalability Considerations

Currently, the project uses in-memory `ConcurrentHashMap` for fast O(1) lookups and thread-safe access.

For production-scale systems, this can be extended with:

- Redis for distributed caching
- MySQL/PostgreSQL for persistent storage
- Load balancers for horizontal scaling
- Rate limiting for API protection
- Expiry cleanup jobs
- Monitoring and analytics dashboards

---

## System Design Concepts Demonstrated

- URL shortening logic
- Base62 code generation
- Collision handling
- HTTP 302 redirection
- O(1) hash-based lookup
- Thread-safe storage
- Expiry handling
- Analytics tracking
- REST API design
- Backend scalability thinking

---

## Future Improvements

- Persistent database storage
- Redis cache integration
- User authentication
- QR code generation
- Advanced analytics dashboard
- Expired URL cleanup scheduler
- Docker deployment
- Cloud deployment

---

## Learning Outcome

This project demonstrates how a real-world URL shortener works internally and covers important backend engineering concepts used in scalable web systems.
