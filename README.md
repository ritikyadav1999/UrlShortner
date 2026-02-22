# 🚀 Scalable URL Shortener (Spring Boot + Redis)

A production-oriented URL Shortener built using **Spring Boot, Redis, Docker, and JPA**, designed with real backend engineering principles such as caching, write-behind analytics, and durability.

This project focuses on **performance, scalability, and clean system design**, not just CRUD functionality.

---

## ✨ Features

- 🔗 Generate short URLs
- 🔁 Fast redirection using Redis cache
- 📊 High-performance click tracking
- ⚡ Optimized for read-heavy workloads
- 🔄 Background batch sync to database
- 💾 Redis durability using AOF
- 🧠 Clean separation of read and write concerns

---

## 🏗 Architecture Overview

### Request Flow

User  
↓  
Spring Boot Application  
↓  
Redis (Cache Layer)  
↓  
Redis (Atomic Click Counter)  
↓  
Database (Periodic Sync)

---

## ⚡ Caching Strategy (Cache-Aside Pattern)

On redirect:

1. Check Redis for `shortUrl:<shortCode>`
2. If cache miss → fetch from DB
3. Store in Redis (TTL = 24 hours)
4. Return original URL

### Why?

- Reduces database load
- Improves latency
- Handles traffic spikes efficiently

---

## 📊 Click Tracking (Write Optimization)

Instead of updating the database on every redirect:

- Uses Redis `INCR` for atomic click counting
- Stores counters as: clickCount:<shortCode>



A scheduled background job (every 5 minutes):

- Reads Redis click counters
- Adds counts to DB
- Deletes Redis keys after sync

**Pattern Used:** Write-Behind / Eventual Consistency

---

## 💾 Redis Durability

Redis runs with **AOF (Append Only File)** enabled:
redis-server --appendonly yes



This ensures:

- Write operations are logged to disk
- Data survives Redis restarts
- Click counters are durable

---

## 🧠 Engineering Concepts Implemented

- Cache-aside pattern
- Redis atomic operations (`INCR`)
- Write-behind batch processing
- Eventual consistency
- Separation of read and write paths
- Graceful degradation (DB fallback if Redis fails)
- Redis persistence (AOF)
- Scheduled background jobs

---

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Redis
- Docker
- MySQL / PostgreSQL

---

## 🚀 Running the Project

### 1️⃣ Start Redis (with persistence)

```bash
docker run -d \
  --name redis-container \
  -p 6379:6379 \
  -v redis-data:/data \
  redis redis-server --appendonly yes
  
```


### Why This Project Stands Out

-Most URL shortener implementations stop at saving data and redirecting.
-This project demonstrates:
-Real-world performance optimization
-Scalable architecture patterns
-Distributed system fundamentals
-Production-aware backend design
