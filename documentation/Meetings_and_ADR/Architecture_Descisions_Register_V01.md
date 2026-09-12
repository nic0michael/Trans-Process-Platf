**2026-09-11 09:30**
We agreed to implement the changes from previour ADR
Refer to Specifications_v01

---
## 2026-09-11 08:00
The DevOps team informed me that they use Kafka, not RabbitMQ, and they use Gradle instead of Maven 

---
## 2026-09-11 09:30
We agreed to implement these changes

---
## 2026-09-11 09:47
Nico requested instructions for installing Kafka as a docker container running in a VM using a docker compose file to run in Proxmox

---
## 2026-09-11 10;48
Nico requested instructions for installing PostgreSQL server as a docker container running in a VM using a docker compose file to run in Proxmox

---
 
## 2026-09-11 14:47
Nico proposed we use the following structure for the Transactions (as messages)
But to enable external systems to call this. To identify external systems, we will use an uppercase string externalSystemId
### Transaction Message Structure
 **We now have these fields:**

| Field                    | Java Type       | Database Type   | Description                                          |
| ------------------------ | --------------- | --------------- | ---------------------------------------------------- |
| `timestamp`              | `LocalDateTime` | `TIMESTAMP`     | Transaction timestamp, format `YYYY-MM-dd HH:mm:ss`  |
| `transactionIndex`       | `Long`          | `BIGINT`        | Database-generated transaction identifier            |
| `transactionGuid`        | `UUID`          | `UUID`          | Globally unique transaction identifier               |
| `requestId`              | `UUID`          | `UUID`          | Unique idempotency/request identifier                |
| `externalSystemId`       | `String`        | `VARCHAR`       | Uppercase identifier for the external calling system |
| `transactionType`        | `String` / Enum | `VARCHAR`       | `SALE` or `PURCHASE`                                 |
| `currency`               | `String`        | `CHAR(3)`       | ISO currency code, e.g. `ZAR`, `USD`                 |
| `amount`                 | `BigDecimal`    | `DECIMAL(19,4)` | Monetary transaction amount                          |
| `reference`              | `String`        | `VARCHAR(100)`  | External/business reference                          |
| `transactionDescription` | `String`        | `VARCHAR(500)`  | Description of the transaction                       |
| `companyId`              | `Long`          | `BIGINT`        | Company associated with the transaction              |
| `status`                 | `String` / Enum | `VARCHAR`       | Current processing status                            |

**Status is an Enum with these values:**
- RECEIVED
- VALIDATED
- QUEUED
- PROCESSING
- COMPLETED
- FAILED
- DEAD_LETTER
  
---
## 2026-09-11 18:26
Nico proposed that we accept his design for the API project as provided in this UML Artifact: \
[https://github.com/nic0michael/Trans-Process-Platf/blob/master/backend/api/The_API_Design.md](https://github.com/nic0michael/Trans-Process-Platf/blob/master/backend/api/The_API_Design.md)

---
## 2026-09-12 19:24
Nico proposed we use Gradle version: gradle-8.14.3, as we experienced issues using Gradle 9 in IntelliJ

---
