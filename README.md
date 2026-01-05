
# URL Shortener (Spring Boot)

## Overview
This project is a **backend URL Shortener service** built using **Spring Boot**.  
It converts long URLs into short, shareable links and redirects users back to the original URL when accessed.

The application is designed to demonstrate **backend fundamentals**, including REST APIs, hashing strategies, concurrency handling, and HTTP redirection.

---

##  Features
- Generate short URLs for long links
- Redirect short URLs to original URLs
- Thread-safe in-memory storage
- Fast O(1) lookup using hash-based mapping
- Clean REST API design
- Embedded Tomcat server

---

##  Tech Stack
- Java
- Spring Boot
- Maven
- REST APIs
- ConcurrentHashMap
- Embedded Tomcat

---

##  How It Works
1. User sends a long URL to the `/shorten` endpoint
2. Service generates a **Base62-encoded short code**
3. Short code is stored in a thread-safe map
4. Accessing `/u/{code}` redirects to the original URL using HTTP **302 redirect**

---



### 🔹 Shorten URL
**POST**
