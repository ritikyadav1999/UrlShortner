🚀 Scalable URL Shortener (Spring Boot + Redis)

A production-oriented URL Shortener built with Spring Boot, Redis, Docker, and JPA, designed with real backend engineering principles such as caching, write-behind analytics, and durability.

This project focuses on scalability, performance, and system design thinking, not just CRUD functionality.

✨ Features

🔗 Shorten long URLs

🔁 Fast redirection using Redis cache

📊 High-performance click tracking

⚡ Read-heavy workload optimization

🔄 Background batch sync to database

💾 Redis durability using AOF

🧠 Clean separation of read and write concerns

🏗 Architecture Overview

Request Flow

User
↓
Spring Boot Application
↓
Redis (Cache Layer)
↓
Redis (Atomic Click Counter)
↓
Database (Periodic Sync)

⚡ Caching Strategy (Cache-Aside Pattern)

On redirect:

Check Redis first

If cache miss → fetch from DB

Store in Redis (TTL = 24 hours)

Namespaced keys:

shortUrl:<shortCode>

Why?

Reduces DB load

Improves latency

Handles traffic spikes efficiently

📊 Click Tracking (Write Optimization)

Instead of updating the DB on every redirect:

Uses Redis INCR for atomic counting

Stores counters as:

clickCount:<shortCode>

Background job runs every 5 minutes:

Reads Redis counters

Adds to DB

Deletes Redis keys

Pattern Used: Write-Behind / Eventual Consistency

💾 Redis Durability

Redis runs with AOF (Append Only File) enabled:

redis-server --appendonly yes

Ensures:

Write operations are logged to disk

Data survives Redis restarts

Click counters are durable

🧠 Engineering Concepts Implemented

Cache-aside pattern

Atomic operations (Redis INCR)

Write-behind batch processing

Eventual consistency

Separation of read and write paths

Graceful degradation (DB fallback if Redis fails)

Redis persistence (AOF)

Scheduled background jobs

🛠 Tech Stack

Java 17

Spring Boot

Spring Data JPA

Redis

Docker

MySQL / PostgreSQL

🚀 Running the Project
1️⃣ Start Redis (with persistence)
docker run -d \
  --name redis-container \
  -p 6379:6379 \
  -v redis-data:/data \
  redis redis-server --appendonly yes
2️⃣ Run Spring Boot
mvn spring-boot:run
📌 Why This Project Stands Out

Most URL shorteners stop at DB storage and redirect.

This implementation demonstrates:

Real-world scalability patterns

Performance optimization

Distributed system fundamentals

Production-aware backend design

👨‍💻 Author

Built to practice and demonstrate advanced backend engineering and system design concepts.
