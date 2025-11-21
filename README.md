# URL Shortener Service

A simple and efficient **URL shortening service** built with **Spring Boot 3**, **Java 21**, and **Redis**.

The application generates short, unique slugs for long URLs and provides instant redirection via HTTP 302.  
Docker support is included for easy deployment.

---

## Features

- Generate a short slug for any long URL  
- Store slug–URL pairs in Redis with TTL  
- Fast 302 redirect from slug to original URL  
- Clean REST API  
- Docker & Docker Compose ready  
- Lightweight and production-oriented architecture  

---

## Technology Stack

- **Java 21**
- **Spring Boot 3**
  - Spring Web  
  - Spring Data Redis
- **Redis**
- **Docker / Docker Compose**

---

## How It Works

1. User sends a long URL  
2. Service generates a random 6-character slug  
3. Slug and URL are stored in Redis  
4. Requesting `/redirect-url/{slug}` redirects the user to the original URL

---

## API Endpoints

### Generate short URL

**POST** `/api/v1/generate-slug/{url}`  
Returns a generated slug for the given URL.

### Redirect to long URL

**GET** `/api/v1/redirect-url/{slug}`  
Redirects (HTTP 302) to the original long URL.

---

## Docker

Build and run with Docker Compose:

```bash
docker-compose up -d
