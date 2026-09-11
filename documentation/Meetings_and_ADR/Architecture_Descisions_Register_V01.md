**2026-09-11 09:30**
We agreed to implement the changes from previour ADR
Refer to Specifications_v01

---

**2026-09-11 09:47**
Nico requested instructions for installing Kafka as a docker container running in a VM using a docker compose file
### ADR-025 — Local Kafka Development Environment agreed upon

---


---
 
## 2026-09-11 14:47
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

