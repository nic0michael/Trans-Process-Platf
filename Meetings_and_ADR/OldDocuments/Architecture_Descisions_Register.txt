# Nico's Java Project

**Change timestamp: 2026-09-11 09:27:32**

I checked the current official Kafka and Gradle documentation. Kafka's partition/consumer-group model actually strengthens the project because it gives us a better way to demonstrate **scaling, ordering, partitioning and consumer fault tolerance**. ([Apache Kafka][1]) Gradle is also the appropriate build system for the project and supports Java multi-project builds and testing directly. ([Gradle Documentation][2])

Only the chapters that need changing are below.

---

# 3. Technology stack — UPDATED

## Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Kafka
* Spring Actuator
* PostgreSQL
* **Apache Kafka**
* Gradle

**Architecture decision:** Kafka replaces RabbitMQ because the target production environment uses Kafka.

Kafka's topic/partition/consumer-group architecture is particularly useful for this project because it lets us demonstrate scalable parallel processing and ordering by partition key. ([Apache Kafka][1])

---

# 6. Architecture — UPDATED

The messaging architecture becomes:

```text
                     +----------------+
                     |    Angular     |
                     +-------+--------+
                             |
                             v
                     +---------------+
                     |   API Service |
                     +-------+-------+
                             |
                   +---------+---------+
                   |                   |
                   v                   v
              PostgreSQL          Outbox
                                       |
                                       v
                                  Kafka Topic
                                       |
                         +-------------+-------------+
                         |                           |
                         v                           v
                  Processor 1                  Processor 2
                  Consumer                    Consumer
                         |                           |
                         +-------------+-------------+
                                       |
                                       v
                                  PostgreSQL
```

The processors belong to the same **Kafka consumer group**.

Kafka assigns topic partitions across consumer instances in the group, providing the basis for horizontal processing and fault tolerance. ([Apache Kafka][3])

---

# 7. Architecture Decision Records — UPDATED

Add/change the ADR list to:

```text
ADR-001 Java 21
ADR-002 Spring Boot
ADR-003 Gradle
ADR-004 Angular
ADR-005 PostgreSQL
ADR-006 Apache Kafka
ADR-007 Asynchronous Processing
ADR-008 Outbox Pattern
ADR-009 Idempotency Strategy
ADR-010 Kafka Partitioning Strategy
ADR-011 Kafka Consumer Group Strategy
ADR-012 Database Index Strategy
ADR-013 Retry Strategy
ADR-014 Dead Letter Strategy
ADR-015 API Error Model
ADR-016 Authentication Strategy
ADR-017 Observability
ADR-018 Docker Compose
ADR-019 Service Virtualization
ADR-020 Contract Testing
ADR-021 Fault Injection
ADR-022 Kubernetes Deployment
```

**Important ADR change:** `ADR-006` should document **why Kafka was selected instead of RabbitMQ**, based on the actual target environment.

---

# 12. Kafka — NEW / REPLACES RABBITMQ

Kafka becomes the project's asynchronous event platform.

Primary topic:

```text
transaction-events
```

Additional topics:

```text
transaction-retry
transaction-dlq
transaction-results
```

Each transaction event should contain:

```text
transactionId
requestId
customerId
eventType
timestamp
attempt
```

---

# 13. Kafka Partitioning — NEW

The `transaction-events` topic should have multiple partitions.

The architecture must define a partition key.

Recommended key:

```text
customerId
```

This means transactions for the same customer are directed consistently to the same partition, allowing ordering for that customer's events while still allowing different customers to be processed in parallel.

Kafka guarantees ordering within a partition, not across the entire topic. ([Apache Kafka][1])

This gives you an excellent architecture interview topic:

> **Why did you choose customerId as the Kafka partition key?**

---

# 14. Kafka Consumer Group — NEW

The transaction processors use one consumer group:

```text
transaction-processor-group
```

Example:

```text
transaction-events
       |
       +-- Partition 0 ---> Processor 1
       |
       +-- Partition 1 ---> Processor 2
       |
       +-- Partition 2 ---> Processor 3
       |
       +-- Partition 3 ---> Processor 4
```

If one processor stops, Kafka can rebalance its partitions among the remaining consumers.

This directly strengthens the project's demonstration of:

* scalability
* fault tolerance
* horizontal scaling
* workload distribution

Kafka consumer groups are specifically designed to distribute partitions across consumer instances. ([Apache Kafka][3])

---

# 15. Retry architecture — UPDATED

Replace the RabbitMQ retry queues with Kafka retry topics.

```text
transaction-events
        |
        v
    Processor
        |
        +---- SUCCESS
        |
        +---- FAILURE
                 |
                 v
        transaction-retry
                 |
                 v
             Processor
                 |
          +------+------+
          |             |
       SUCCESS       FAILURE
                        |
                        v
                transaction-dlq
```

The retry policy remains:

```text
Maximum attempts = 3
```

The attempt count must be persisted with the transaction/event.

---

# 16. Dead Letter Queue — UPDATED

Use a Kafka **dead-letter topic** rather than a RabbitMQ DLQ.

```text
transaction-dlq
```

The Angular application should display:

* transaction ID
* request ID
* failure reason
* attempt count
* timestamp
* original event information

---

# 18. Database model — UPDATED

The database model remains largely unchanged.

However, `processing_attempt` should also record Kafka information:

```text
processing_attempt
------------------
id
transaction_id
attempt_number
kafka_topic
kafka_partition
kafka_offset
started_at
completed_at
status
error_message
```

This gives us an excellent operational audit trail.

For example:

```text
Transaction
   |
   +-- Kafka topic
   +-- Partition 3
   +-- Offset 18492
   +-- Processing attempt 2
```

---

# 22. Correlation ID — UPDATED

The correlation ID must travel through Kafka.

```text
HTTP Request
     |
     v
correlationId
     |
     v
Outbox
     |
     v
Kafka message
     |
     v
Processor
     |
     v
Database
```

Logs from the API and processor must therefore be traceable using the same correlation ID.

---

# 23. Observability — UPDATED

Add Kafka-specific metrics:

```text
kafka.consumer.lag
kafka.records.consumed
kafka.records.produced
kafka.processing.time
kafka.errors
```

Most importantly:

### Consumer lag

```text
Produced:
1000 messages

Processed:
800 messages

Lag:
200
```

Consumer lag becomes an important indicator of whether the processor fleet is keeping up with incoming transactions.

---

# 28. Docker architecture — UPDATED

Replace:

```text
ntp-rabbitmq
```

with Kafka.

Initial Docker environment:

```text
ntp-frontend
ntp-api
ntp-processor
ntp-postgres
ntp-kafka
ntp-prometheus
ntp-grafana
```

Kafka can be run locally in Docker, making the entire project laptop-based.

---

# 35. Kubernetes requirements — UPDATED

Kafka now becomes part of the Kubernetes architecture.

```text
Kubernetes Cluster
|
+-- Ingress
|
+-- API Pods
|
+-- Processor Pods
|
+-- Kafka
|
+-- PostgreSQL
```

The important scaling relationship becomes:

```text
More Kafka partitions
        |
        v
More processor consumers
        |
        v
Greater parallel processing
```

There is an important Kafka constraint here: a consumer group cannot actively assign more traditional consumer instances than there are partitions for a topic. ([Apache Kafka][3])

That gives you a very good interview question to practice:

> **Why doesn't simply adding more Kubernetes pods necessarily increase Kafka processing throughput?**

---

# 42. Project structure — UPDATED

Change:

```text
infrastructure/
├── docker/
├── compose/
└── kubernetes/
```

to:

```text
infrastructure/
├── docker/
├── compose/
│   └── kafka/
└── kubernetes/
    └── kafka/
```

The Kafka configuration should be treated as an explicit infrastructure component rather than hidden inside the application.

---

# 45. Gradle structure — UPDATED

The project remains a **multi-project Gradle build**:

```text
nicos-java-project
│
├── backend-api
├── backend-processor
├── backend-common
└── backend-test-support
```

Use the **Gradle Wrapper** so the project specifies the Gradle version required to build it consistently. Gradle's current documentation recommends the wrapper as part of the build workflow. ([Gradle Documentation][4])

Gradle natively supports Java compilation, testing, packaging and multi-project builds, so it fits the architecture well. ([Gradle Documentation][2])

---

# 48. Design patterns — UPDATED

The patterns remain, but add:

| Pattern            | Where                          |
| ------------------ | ------------------------------ |
| Outbox             | Transaction events             |
| State              | Transaction states             |
| Strategy           | Transaction processing rules   |
| Factory            | Processor creation             |
| Observer           | Processing events              |
| Decorator          | Processing instrumentation     |
| Repository         | Database access                |
| Adapter            | External Risk Service          |
| Retry              | Kafka retry processing         |
| Circuit Breaker    | External service               |
| DTO                | API boundary                   |
| **Consumer Group** | Kafka parallel processing      |
| **Partitioning**   | Transaction event distribution |

---

# 51. Development phases — UPDATED

The messaging phase changes from RabbitMQ to Kafka:

```text
PHASE 1
Requirements
Architecture
ADRs
C4 diagrams

        ↓

PHASE 2
Domain model
Database model
API contract

        ↓

PHASE 3
TDD
Core transaction functionality

        ↓

PHASE 4
Angular application

        ↓

PHASE 5
Kafka
Asynchronous processing
Topics
Partitions
Consumer Groups

        ↓

PHASE 6
Outbox
Idempotency
Retry Topics
Dead Letter Topic

        ↓

PHASE 7
Integration testing
Contract testing
Fault injection

        ↓

PHASE 8
Observability
Kafka Consumer Lag

        ↓

PHASE 9
Docker Compose

        ↓

PHASE 10
JMeter/performance

        ↓

PHASE 11
Kubernetes design

        ↓

PHASE 12
CI/CD and deployment
```

### Key architectural change

The project is now actually **better aligned with your target environment**:

**RabbitMQ → Kafka**

**Maven → Gradle**

The most important new architecture subjects are **Kafka topics, partitions, keys, consumer groups, offsets, consumer lag and rebalancing**. Those should become explicit parts of the Architecture/TDD specification rather than merely swapping the dependency name. ([Apache Kafka][1])

[1]: https://kafka.apache.org/intro/?utm_source=chatgpt.com "Introduction | Apache Kafka"
[2]: https://docs.gradle.org/current/userguide/building_java_projects.html?utm_source=chatgpt.com "Building Java & JVM projects"
[3]: https://kafka.apache.org/0100/getting-started/introduction/?utm_source=chatgpt.com "Introduction | Apache Kafka"
[4]: https://docs.gradle.org/current/userguide/gradle_basics.html?utm_source=chatgpt.com "Core Concepts"

---



