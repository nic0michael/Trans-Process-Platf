# Nico's Java Project — Complete Revised Specification

**ADR Change Timestamp: 2026-09-11 09:30:00**

The agreed architectural changes are now incorporated:

* **RabbitMQ → Apache Kafka**
* **Maven → Gradle**
* Kafka **topics, partitions and consumer groups** are now explicit architectural components.
* Kafka **consumer lag** is an observability requirement.
* Gradle **multi-project build + Gradle Wrapper** are requirements.

The current Spring Kafka documentation confirms stable Spring for Apache Kafka 4.1.1, with support for topics, partitions, sending/receiving messages, monitoring, transactions and error handling. ([Home][1])
The current Gradle documentation identifies Gradle 9.7.1 and recommends the Gradle Wrapper for consistent builds; Gradle also supports multi-project builds. ([Gradle Documentation][2])

---

# 1. Project Name

## Nico's Java Project

### Transaction Processing Platform


Short name(GiHub): Trans-Process-Platf
GitHub URL: https://github.com/nic0michael/Trans-Process-Platf

```text
NTP
```

---

# 2. Project Objective

Build a production-style **Java/Angular transaction-processing platform** that can be developed and run entirely on a laptop.

The project is specifically designed to demonstrate and strengthen the areas identified in the Senior Software Engineer, Backend Engineer and Senior Technical Architect feedback.

The system shall demonstrate:

* Java 21
* Spring Boot
* Angular
* REST APIs
* PostgreSQL
* Apache Kafka
* asynchronous processing
* Outbox Pattern
* idempotency
* database constraints
* database indexing
* retry processing
* dead-letter processing
* service virtualization
* fault injection
* contract testing
* unit testing
* integration testing
* performance testing
* Docker
* Docker Compose
* observability
* Prometheus
* Grafana
* Gradle
* Kubernetes-ready deployment
* CI/CD-ready architecture

---

# 3. Technology Stack

## Backend

```text
Java 21
Spring Boot
Spring Web
Spring Data JPA
Spring Validation
Spring for Apache Kafka
Spring Actuator
PostgreSQL
Gradle
```

Spring for Apache Kafka provides the Spring programming model for Kafka applications, including producers, consumers, topics, partitions and error handling. ([Home][3])

## Frontend

```text
Angular
TypeScript
RxJS
Angular HttpClient
Angular Router
Reactive Forms
```

## Messaging

```text
Apache Kafka
```

## Testing

```text
JUnit
Mockito
AssertJ
Testcontainers
Contract Testing
JMeter
JaCoCo
SonarQube
```

## Infrastructure

```text
Docker
Docker Compose
Kubernetes
Helm
Prometheus
Grafana
```

---

# 4. Architecture Principles

The architecture shall follow these principles:

1. **API and processing are separated.**
2. **Long-running processing is asynchronous.**
3. **Database state is authoritative.**
4. **Kafka provides asynchronous event distribution.**
5. **Transactions are idempotent.**
6. **Events must not be lost between database commit and Kafka publication.**
7. **Failures must be recoverable.**
8. **Permanent failures must be isolated.**
9. **All important operations must be observable.**
10. **The system must be testable without external production services.**
11. **The system must run locally using Docker.**
12. **The architecture must be Kubernetes-ready.**

---

# 5. High-Level Architecture

```text
                         +----------------+
                         |    Angular     |
                         |   Frontend    |
                         +-------+--------+
                                 |
                                 | REST
                                 v
                         +---------------+
                         |   API Service |
                         +-------+-------+
                                 |
                    +------------+------------+
                    |                         |
                    v                         v
             +-------------+          +---------------+
             | PostgreSQL  |          | Outbox Table  |
             +-------------+          +-------+-------+
                                             |
                                             v
                                      +-------------+
                                      |    Kafka    |
                                      +------+------+
                                             |
                              +--------------+--------------+
                              |              |              |
                              v              v              v
                         Processor 1    Processor 2    Processor N
                              |              |              |
                              +--------------+--------------+
                                             |
                                             v
                                      +-------------+
                                      | PostgreSQL  |
                                      +-------------+

External:
                         +----------------------+
                         | Risk Evaluation      |
                         | Service              |
                         +----------------------+

Observability:
                         +-------------+
                         | Prometheus  |
                         +------+------+
                                |
                                v
                         +-------------+
                         |  Grafana    |
                         +-------------+
```

---

# 6. Architectural Components

The system contains these major components:

| Component             | Responsibility                |
| --------------------- | ----------------------------- |
| Angular               | User interface                |
| API Service           | REST API                      |
| PostgreSQL            | Persistent state              |
| Outbox                | Reliable event publication    |
| Kafka                 | Event distribution            |
| Transaction Processor | Asynchronous processing       |
| Risk Service          | Simulated external dependency |
| Prometheus            | Metrics                       |
| Grafana               | Monitoring                    |
| Docker                | Local deployment              |
| Kubernetes            | Future deployment target      |

---

# 7. Architecture Decision Records

The following ADRs are mandatory:

```text
ADR-001  Java 21
ADR-002  Spring Boot
ADR-003  Gradle
ADR-004  Angular
ADR-005  PostgreSQL
ADR-006  Apache Kafka
ADR-007  Asynchronous Processing
ADR-008  Outbox Pattern
ADR-009  Idempotency Strategy
ADR-010  Kafka Partitioning Strategy
ADR-011  Kafka Consumer Group Strategy
ADR-012  Database Index Strategy
ADR-013  Retry Strategy
ADR-014  Dead Letter Strategy
ADR-015  API Error Model
ADR-016  Authentication Strategy
ADR-017  Observability
ADR-018  Docker Compose
ADR-019  Service Virtualization
ADR-020  Contract Testing
ADR-021  Fault Injection
ADR-022  Kubernetes Deployment
ADR-023  CI/CD
ADR-024  Performance Testing
```

## ADR Change Record

```text
2026-09-11 09:27:32
RabbitMQ replaced with Apache Kafka.
Maven replaced with Gradle.

2026-09-11 09:30:00
Changes formally incorporated into the complete project specification.

Kafka topics, partitions, consumer groups and consumer lag
are now explicit architectural requirements.

Gradle multi-project build and Gradle Wrapper
are explicit project requirements.
```

---

# 8. Functional Requirements

## FR-001 Submit Transaction

The user shall be able to submit a transaction.

Example:

```json
{
  "requestId": "8c4d...",
  "customerId": "CUST-10001",
  "transactionType": "PAYMENT",
  "amount": 1250.50,
  "currency": "ZAR"
}
```

---

## FR-002 Validate Transaction

The API shall validate:

* request ID
* customer ID
* transaction type
* amount
* currency
* supported currency
* maximum transaction amount

Invalid requests shall not enter Kafka processing.

---

# 9. Transaction States

The transaction state model shall be:

```text
RECEIVED
    |
    v
VALIDATED
    |
    v
QUEUED
    |
    v
PROCESSING
    |
    +----------+
    |          |
    v          v
COMPLETED    FAILED
               |
               v
             RETRY
               |
          +----+----+
          |         |
          v         v
     PROCESSING   DEAD_LETTER
```

Invalid state transitions shall be rejected.

---

# 10. Idempotency

Every request shall contain:

```text
requestId
```

The database shall enforce:

```text
UNIQUE(request_id)
```

If the same request is submitted multiple times:

```text
Request 1 -> Process
Request 2 -> Duplicate
Request 3 -> Duplicate
```

Only one transaction shall be processed.

The system shall support concurrent duplicate requests.

---

# 11. Outbox Pattern

The API transaction shall use an Outbox Pattern.

Conceptually:

```text
BEGIN TRANSACTION

INSERT transaction

INSERT outbox_event

COMMIT
```

The system shall not rely on an independent:

```text
Database commit
       +
Kafka publish
```

sequence.

The Outbox Publisher shall publish persisted events to Kafka.

---

# 12. Kafka Architecture

Kafka is the project's messaging platform.

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

Kafka is used for:

* asynchronous processing
* decoupling
* workload distribution
* horizontal processing
* event retention
* retry processing
* failure isolation

---

# 13. Kafka Event

A transaction event shall contain:

```text
transactionId
requestId
customerId
eventType
timestamp
attempt
correlationId
```

The event shall contain sufficient information for the processor to identify and process the transaction.

---

# 14. Kafka Partitioning

The primary transaction topic shall contain multiple partitions.

The initial design shall use:

```text
customerId
```

as the Kafka partition key.

Example:

```text
transaction-events

Partition 0
Partition 1
Partition 2
Partition 3
```

The design objective is:

```text
Same customer
      |
      v
Same partition
      |
      v
Ordered customer events
```

while allowing different customers to be processed concurrently.

Kafka ordering is maintained within a partition rather than across the entire topic.

---

# 15. Kafka Consumer Groups

Transaction processors shall use:

```text
transaction-processor-group
```

Example:

```text
transaction-events

Partition 0 ---> Processor 1
Partition 1 ---> Processor 2
Partition 2 ---> Processor 3
Partition 3 ---> Processor 4
```

Processor instances belonging to the same consumer group shall share the workload.

The design shall account for the relationship between:

```text
Kafka partitions
        |
        v
Consumer instances
        |
        v
Processing throughput
```

---

# 16. Kafka Retry Architecture

Processing failures shall use Kafka retry topics.

```text
transaction-events
        |
        v
   Processor
        |
    +---+---+
    |       |
 SUCCESS   FAIL
            |
            v
   transaction-retry
            |
            v
       Processor
            |
       +----+----+
       |         |
    SUCCESS    FAIL
                  |
                  v
          transaction-dlq
```

Maximum attempts:

```text
3
```

The attempt count shall be persisted.

---

# 17. Dead Letter Topic

Permanent failures shall be sent to:

```text
transaction-dlq
```

The system shall retain sufficient information to diagnose the failure.

The Angular UI shall expose dead-letter transactions.

---

# 18. Database Model

## customer

```text
id
customer_number
name
created_at
```

## transaction

```text
id
request_id
customer_id
transaction_type
amount
currency
status
attempt_count
created_at
updated_at
completed_at
failure_reason
```

## outbox_event

```text
id
aggregate_id
event_type
payload
created_at
published_at
```

## processing_attempt

```text
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

---

# 19. Database Index Requirements

The following query requirements must be supported:

```text
Find transaction by requestId

Find customer transaction history

Find failed transactions

Find queued transactions

Dashboard status statistics
```

Initial index candidates:

```text
transaction(request_id)

transaction(customer_id)

transaction(status)

transaction(created_at)
```

Composite indexes shall be justified by actual query requirements.

Potential examples:

```text
(customer_id, created_at)

(status, created_at)
```

The project shall document why each production index exists.

---

# 20. API Requirements

Required endpoints:

```text
POST /api/transactions

GET /api/transactions/{id}

GET /api/transactions

GET /api/transactions/{id}/status
```

Additional endpoints may be added for:

```text
metrics
health
dead-letter management
```

---

# 21. API Error Model

All API errors shall use a consistent format:

```json
{
  "timestamp": "...",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Invalid transaction",
  "path": "/api/transactions",
  "correlationId": "..."
}
```

No controller shall create its own incompatible error structure.

---

# 22. Correlation ID

A correlation ID shall travel through:

```text
HTTP
 |
 v
API
 |
 v
Outbox
 |
 v
Kafka
 |
 v
Processor
 |
 v
Database
```

The correlation ID shall be present in relevant application logs.

---

# 23. External Risk Service

Create a simulated external service:

```text
Risk Evaluation Service
```

Possible responses:

```text
APPROVED
DECLINED
HTTP 500
TIMEOUT
SLOW RESPONSE
```

This service exists specifically to support:

* integration testing
* service virtualization
* fault injection
* retry testing
* resilience testing

---

# 24. Fault Injection

The processor shall support controlled test scenarios:

```text
NORMAL

FAIL_ONCE

FAIL_ALWAYS

SLOW_PROCESSING

TIMEOUT
```

These scenarios must be deterministic.

Example:

```text
FAIL_ONCE

Attempt 1 -> FAILURE
Attempt 2 -> SUCCESS
```

and:

```text
FAIL_ALWAYS

Attempt 1 -> FAILURE
Attempt 2 -> FAILURE
Attempt 3 -> FAILURE
              |
              v
            DLQ
```

---

# 25. Observability

The system shall expose:

### Application metrics

```text
transactions.received
transactions.completed
transactions.failed
transactions.retried
transactions.deadlettered
transaction.processing.time
```

### Kafka metrics

```text
kafka.records.produced
kafka.records.consumed
kafka.consumer.lag
kafka.errors
```

**Consumer lag is a key operational metric.**

Example:

```text
Messages produced: 10,000
Messages processed: 9,700

Consumer lag: 300
```

---

# 26. Health Endpoints

Required:

```text
/actuator/health
/actuator/info
/actuator/metrics
/prometheus
```

Health shall account for dependencies such as:

```text
PostgreSQL
Kafka
Risk Service
```

---

# 27. Angular Dashboard

The dashboard shall display:

```text
Transactions Received
Transactions Processing
Transactions Completed
Transactions Failed
Dead Letter Transactions
Kafka Consumer Lag
Average Processing Time
```

---

# 28. Angular Transaction Screen

Columns:

```text
Transaction ID
Customer
Amount
Currency
Status
Attempts
Created
Updated
```

Filtering:

```text
Customer
Status
Date Range
Transaction Type
```

Sorting:

```text
Created
Amount
Status
```

Pagination shall be supported.

---

# 29. Transaction Detail

Display:

```text
Transaction Details

ID
Request ID
Customer
Amount
Currency
Status

Processing History

Attempt 1
Attempt 2
Attempt 3

Event History

Received
Validated
Queued
Processing
Completed
```

Kafka information should be available for diagnostic purposes:

```text
Topic
Partition
Offset
```

---

# 30. Java Core Requirements

A dedicated Java core package shall demonstrate:

## equals/hashCode

Tests shall demonstrate:

```text
equal objects
different objects
HashMap lookup
HashSet behaviour
```

## HashMap

The project documentation shall explain:

```text
hash
bucket
collision
equals()
hashCode()
```

## LinkedList

A small implementation/test exercise shall demonstrate:

```text
Node
next
Traversal
Insert
Remove
```

## Interface Features

Examples shall cover appropriate use of:

```text
default methods
static methods
private interface methods
```

---

# 31. Algorithm Requirements

A dedicated algorithm package shall contain exercises covering:

```text
sorting
filtering
greedy selection
HashMap
LinkedList
Queue
PriorityQueue
```

One practical problem:

> Given pending transactions and a limited processing capacity, select which transactions should be processed first according to defined business-priority rules.

The implementation shall document:

```text
algorithm
time complexity
space complexity
trade-offs
```

---

# 32. Testing Strategy

Testing shall use several levels.

```text
Unit
   |
Integration
   |
Contract
   |
Fault Injection
   |
Performance
```

No single testing technique should be treated as sufficient.

---

# 33. Unit Testing

Use:

```text
JUnit
Mockito
AssertJ
```

Test:

```text
business rules
validation
state transitions
idempotency
retry decisions
algorithm logic
```

Mockito shall be used where isolation is appropriate, but not as a substitute for integration testing.

---

# 34. Integration Testing

Integration tests shall use real infrastructure where appropriate:

```text
PostgreSQL
Kafka
```

The tests should verify:

```text
API
 |
 v
Database
 |
 v
Kafka
 |
 v
Processor
 |
 v
Database
```

---

# 35. Testcontainers

Testcontainers shall provide reproducible infrastructure for integration testing.

Required infrastructure candidates:

```text
PostgreSQL
Kafka
```

---

# 36. Contract Testing

Contract tests shall verify:

```text
API requests
API responses
Validation errors
Error responses
Transaction status
```

The purpose is to prevent accidental API incompatibility.

---

# 37. Fault Testing

Required deterministic scenarios:

```text
Risk service HTTP 500

Risk service timeout

Risk service slow response

Kafka unavailable

Database unavailable

Duplicate request

Processor failure

Retry exhaustion
```

Each test must define an explicit expected outcome.

---

# 38. Concurrency Testing

Test:

```text
100 identical requests
```

using the same:

```text
requestId
```

Expected:

```text
1 transaction
1 successful processing
99 duplicate requests
```

The exact response semantics shall be defined during the API design phase.

---

# 39. Performance Testing

JMeter shall test:

```text
100 requests
1,000 requests
10,000 requests
```

Measurements:

```text
requests/second
average response time
95th percentile
99th percentile
error rate
Kafka consumer lag
queue/partition backlog
processing time
```

A key requirement is to demonstrate the difference between:

```text
API response time
```

and:

```text
actual transaction processing time
```

because processing is asynchronous.

---

# 40. Docker Architecture

The complete initial environment shall run on one laptop.

```text
ntp-frontend
ntp-api
ntp-processor
ntp-postgres
ntp-kafka
ntp-prometheus
ntp-grafana
```

The target is:

```text
docker compose up
```

to start the local environment.

---

# 41. Kubernetes Architecture

The project shall eventually provide Kubernetes deployment definitions.

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
|
+-- Monitoring
```

---

# 42. Kubernetes Requirements

## API

Initial design:

```text
2 replicas
```

## Processor

Multiple replicas shall be supported.

Scaling shall consider:

```text
Kafka partitions
Consumer group
Consumer lag
CPU
Memory
```

The architecture shall explicitly document why simply increasing pod count does not automatically guarantee increased Kafka throughput.

---

# 43. Kubernetes Health

Deployments shall support:

```text
Readiness probes
Liveness probes
```

A pod shall not receive application traffic before it is ready.

---

# 44. Kubernetes Ingress

Request path:

```text
Client
  |
  v
DNS
  |
  v
Ingress
  |
  v
Service
  |
  v
Pod
```

The architecture shall document:

```text
DNS
TLS
Ingress
Service
Pod
```

---

# 45. Secrets

Credentials shall not be stored in:

```text
Git
Dockerfiles
application source
Kubernetes manifests
```

Local configuration shall use environment-based configuration.

Future deployment shall support Kubernetes Secrets or an equivalent secure secret-management mechanism.

---

# 46. Gradle Build Architecture

The project shall use a **Gradle multi-project build**.

```text
nicos-java-project
|
+-- backend-api
|
+-- backend-processor
|
+-- backend-common
|
+-- backend-test-support
```

Gradle's multi-project model is specifically intended for separating related components into focused subprojects while retaining a single build. ([Gradle Documentation][4])

---

# 47. Gradle Wrapper

The repository shall contain:

```text
gradlew
gradlew.bat

gradle/
    wrapper/
```

The Gradle Wrapper shall define the project's Gradle version.

The Wrapper is the recommended mechanism for ensuring that developers and CI use the same Gradle version. ([Gradle Documentation][2])

---

# 48. Project Structure

```text
nicos-java-project/
│
├── architecture/
│   ├── adr/
│   ├── diagrams/
│   └── requirements/
│
├── backend-api/
│
├── backend-processor/
│
├── backend-common/
│
├── backend-test-support/
│
├── frontend/
│   └── angular/
│
├── tests/
│   ├── integration/
│   ├── contract/
│   ├── performance/
│   └── fault-injection/
│
├── infrastructure/
│   ├── docker/
│   ├── compose/
│   │   └── kafka/
│   └── kubernetes/
│       └── kafka/
│
├── docs/
│
├── gradle/
│   └── wrapper/
│
├── build.gradle
├── settings.gradle
├── gradlew
├── gradlew.bat
├── compose.yaml
└── README.md
```

---

# 49. Design Patterns

The project shall deliberately cover:

| Pattern         | Application                |
| --------------- | -------------------------- |
| Outbox          | Reliable event publication |
| State           | Transaction lifecycle      |
| Strategy        | Processing rules           |
| Factory         | Processor creation         |
| Observer        | Processing events          |
| Decorator       | Instrumentation            |
| Repository      | Database access            |
| Adapter         | Risk service               |
| Retry           | Failed processing          |
| Circuit Breaker | External dependency        |
| DTO             | API boundary               |

Kafka architecture additionally demonstrates:

| Kafka Concept  | Application            |
| -------------- | ---------------------- |
| Topic          | Transaction events     |
| Partition      | Parallelism/order      |
| Key            | Customer-based routing |
| Consumer Group | Processor scaling      |
| Offset         | Message position       |
| Consumer Lag   | Operational monitoring |

---

# 50. Non-Functional Requirements

## Performance

Target:

```text
API p95 < 500 ms
```

under normal local test conditions.

---

## Reliability

The system shall:

* survive processor restart
* preserve unprocessed transactions
* retry recoverable failures
* prevent duplicate processing
* isolate permanent failures
* provide diagnostic information

---

## Security

The system shall:

* validate input
* protect administrative functionality
* avoid credentials in source code
* use secure configuration
* support authentication/authorization architecture

---

## Maintainability

Required:

```text
SOLID principles
clear packages
small classes
ADR documentation
automated tests
static analysis
code coverage
```

---

# 51. Architecture Diagrams

Required diagrams:

## C4 Context

```text
User
 |
 v
NTP
 |
 +---- Risk Evaluation Service
```

## C4 Container

```text
Angular
API
Processor
PostgreSQL
Outbox
Kafka
Risk Service
Monitoring
```

## Component diagrams

Required:

```text
API
Transaction Processor
Outbox Publisher
```

## Sequence diagrams

Required:

```text
Successful Transaction

Duplicate Transaction

Retry

Dead Letter

Kafka Consumer Failure
```

## Deployment diagram

Docker:

```text
Laptop
|
+-- Angular
+-- API
+-- Processor
+-- PostgreSQL
+-- Kafka
+-- Prometheus
+-- Grafana
```

Kubernetes:

```text
Cluster
|
+-- Ingress
+-- API Pods
+-- Processor Pods
+-- Kafka
+-- PostgreSQL
+-- Monitoring
```

---

# 52. Development Phases

```text
PHASE 1
Requirements
Architecture
ADRs
C4 Diagrams

        ↓

PHASE 2
Domain Model
Database Model
API Contract

        ↓

PHASE 3
TDD
Core Transaction Functionality

        ↓

PHASE 4
Angular Application

        ↓

PHASE 5
Kafka
Topics
Partitions
Consumer Groups
Asynchronous Processing

        ↓

PHASE 6
Outbox
Idempotency
Retry Topics
Dead Letter Topic

        ↓

PHASE 7
Integration Testing
Contract Testing
Fault Injection

        ↓

PHASE 8
Observability
Kafka Consumer Lag
Prometheus
Grafana

        ↓

PHASE 9
Docker Compose

        ↓

PHASE 10
JMeter Performance Testing

        ↓

PHASE 11
Kubernetes Architecture

        ↓

PHASE 12
CI/CD
Deployment
Rollback
```

---

# 53. Final Project Goal

The finished project should allow you to demonstrate the following story:

> **I designed and built a Java/Angular transaction-processing platform using Spring Boot, PostgreSQL, Kafka and Gradle. The REST API is separated from asynchronous processing. I used the Outbox Pattern for reliable event publication and idempotency to prevent duplicate processing. Kafka partitions and consumer groups provide scalable processing, while retry topics and a dead-letter topic handle failures. I tested the system using unit, integration, contract, fault-injection and performance testing, and packaged the complete environment using Docker with a Kubernetes-ready architecture.**

That gives you a single project from which you can demonstrate both your **existing strengths** and the **specific areas the interview feedback identified for improvement**.

[1]: https://docs.spring.io/spring-kafka/reference/reference.html?utm_source=chatgpt.com "Reference :: Spring Kafka"
[2]: https://docs.gradle.org/current/userguide/gradle_wrapper_basics.html?utm_source=chatgpt.com "Wrapper Basics"
[3]: https://docs.spring.io/spring-kafka/reference/kafka.html?utm_source=chatgpt.com "Using Spring for Apache Kafka :: Spring Kafka"
[4]: https://docs.gradle.org/current/userguide/multi_project_builds.html?utm_source=chatgpt.com "Multi-Project Builds"

