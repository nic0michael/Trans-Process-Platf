# Meeting Minutes

## 2026-09-11 08:00
The DevOps team informed me that they use Kafka, not RabbitMQ, and they use Gradle instead of Maven 

---
## 2026-09-11 09:30
We agreed to implement these changes

---
##2026-09-11 09:47
Nico requested instructions for installing Kafka as a docker container running in a VM using a docker compose file

---
## 2026-09-11 09:47
Nico requested instructions for installing Kafka as a docker container running in a VM using a docker compose file to run in Proxmox

---
## 2026-09-11 10;48
Nico requested instructions for installing PostgreSQL server as a docker container running in a VM using a docker compose file to run in Proxmox

---

## 2026-09-11 14:11
Nico proposed we use the following structure for the Transactions (as messages)

# Transaction Message Structure

Nico proposes that the platform use the following structure for a Transaction message.

| Field                    | Java Type       | Database Type   | Description                                         |
| ------------------------ | --------------- | --------------- | --------------------------------------------------- |
| `timestamp`              | `LocalDateTime` | `TIMESTAMP`     | Transaction timestamp, format `YYYY-MM-dd HH:mm:ss` |
| `transactionIndex`       | `Long`          | `BIGINT`        | Database-generated transaction identifier           |
| `transactionGuid`        | `UUID`          | `UUID`          | Globally unique transaction identifier              |
| `requestId`              | `UUID`          | `UUID`          | Unique idempotency/request identifier               |
| `transactionType`        | `String` / Enum | `VARCHAR`       | `SALE` or `PURCHASE`                                |
| `currency`               | `String`        | `CHAR(3)`       | ISO currency code, e.g. `ZAR`, `USD`                |
| `amount`                 | `BigDecimal`    | `DECIMAL(19,4)` | Monetary transaction amount                         |
| `reference`              | `String`        | `VARCHAR(100)`  | External/business reference                         |
| `transactionDescription` | `String`        | `VARCHAR(500)`  | Description of the transaction                      |
| `companyId`              | `Long`          | `BIGINT`        | Company associated with the transaction             |
| `status`                 | `String` / Enum | `VARCHAR`       | Current processing status                           |

**Status is an Enum with these values:**
- RECEIVED
- VALIDATED
- QUEUED
- PROCESSING
- COMPLETED
- FAILED
- DEAD_LETTER

---
 
## 2026-09-11 14:11
Nico proposed we use the following structure for the Transactions (as messages)

# Transaction Message Structure

Nico proposes that the platform use the previous structure for a Transaction message.
But to enable external systems to call this. To identify external systems, we will use an uppercase string externalSystemId
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
