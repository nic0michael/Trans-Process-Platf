# ISO Standards Quick Guide for IT Development and Architecture

## 1. The Big Picture

ISO standards can be combined to provide a structured approach to the complete IT project lifecycle.

```text
                 IT GOVERNANCE
                       │
                       ▼
              PROJECT LIFECYCLE
                       │
     ┌─────────────────┼─────────────────┐
     ▼                 ▼                 ▼
 ARCHITECTURE       DEVELOPMENT        SECURITY
     │                 │                 │
     └─────────────────┼─────────────────┘
                       ▼
                     DEVOPS
                       │
                       ▼
              SERVICE MANAGEMENT
                       │
                       ▼
              CONTINUAL IMPROVEMENT

       DOCUMENTATION SUPPORTS ALL AREAS
```

## 2. Core Lifecycle Standards

| Standard                    | Purpose                       | Where it fits             |
| --------------------------- | ----------------------------- | ------------------------- |
| **ISO/IEC/IEEE 15288:2023** | System life-cycle processes   | Entire system lifecycle   |
| **ISO/IEC/IEEE 12207:2026** | Software life-cycle processes | Entire software lifecycle |
| **ISO/IEC/IEEE 29148:2018** | Requirements engineering      | Requirements              |
| **ISO/IEC/IEEE 15289:2019** | Lifecycle documentation       | Entire lifecycle          |

**15288** provides the broader system lifecycle framework.

**12207** provides the software lifecycle framework.

These two standards form the foundation of the lifecycle model.

## 3. Architecture

| Standard                    | Purpose                                                               |
| --------------------------- | --------------------------------------------------------------------- |
| **ISO/IEC/IEEE 42010:2022** | Defines how architecture descriptions are structured and communicated |
| **ISO/IEC/IEEE 42020:2019** | Defines architecture processes                                        |

Architecture is not a single project phase. It evolves throughout the lifecycle as requirements, technology and implementation decisions change.

## 4. Development and Quality

| Standard                    | Purpose                                          |
| --------------------------- | ------------------------------------------------ |
| **ISO/IEC/IEEE 90003:2018** | Applying ISO 9001 quality principles to software |
| **ISO/IEC 25010:2023**      | Software and system product-quality model        |
| **ISO/IEC/IEEE 29148:2018** | Requirements engineering                         |

These standards support requirements, development, testing and quality management.

## 5. Security

| Standard                 | Purpose                                |
| ------------------------ | -------------------------------------- |
| **ISO/IEC 27001:2022**   | Information Security Management System |
| **ISO/IEC 27002:2022**   | Information-security controls          |
| **ISO/IEC 27034 series** | Application security                   |

Security should be considered throughout the lifecycle, from requirements and architecture through development, deployment, operations and decommissioning.

## 6. DevOps and Service Management

| Standard                     | Purpose                                                    |
| ---------------------------- | ---------------------------------------------------------- |
| **ISO/IEC TS 20000-15:2024** | Applying Agile and DevOps principles to service management |
| **ISO/IEC 20000-1:2018**     | Service-management system requirements                     |

DevOps connects development and operations, supporting continuous integration, deployment, monitoring and improvement.

## 7. Governance

**ISO/IEC 38500:2024** provides guidance for the governance of IT within an organisation.

It sits above the project lifecycle and provides governance principles for the use and management of IT.

## 8. Lifecycle at a Glance

```text
ENGAGEMENT
    │
    ▼
REQUIREMENTS
    │
    ▼
ARCHITECTURE
    │
    ▼
DESIGN
    │
    ▼
DEVELOPMENT
    │
    ▼
TESTING
    │
    ▼
DEPLOYMENT
    │
    ▼
OPERATIONS
    │
    ▼
MAINTENANCE
    │
    ▼
DECOMMISSIONING
```

Across these stages:

* **Architecture** guides the technical solution.
* **Security** protects the solution and information.
* **Development** creates and maintains the software.
* **DevOps** connects development and operations.
* **Documentation** records lifecycle information.
* **Governance** provides organisational oversight.
* **Quality** provides measurable quality objectives and controls.

## 9. The Key Idea

The standards should not be viewed as separate processes.

**The lifecycle provides the structure, while architecture, development, security, DevOps, quality and documentation operate across that lifecycle.**

## Bibliography

1. **ISO/IEC/IEEE 15288:2023** — *Systems and software engineering — System life cycle processes*. ISO.
2. **ISO/IEC/IEEE 12207:2026** — *Systems and software engineering — Software life cycle processes*. ISO.
3. **ISO/IEC/IEEE 42010:2022** — *Software, systems and enterprise — Architecture description*. ISO.
4. **ISO/IEC/IEEE 42020:2019** — *Software, systems and enterprise — Architecture processes*. ISO.
5. **ISO/IEC 27001:2022** — *Information security management systems — Requirements*. ISO.
6. **ISO/IEC TS 20000-15:2024** — *Information technology — Service management — Guidance on Agile and DevOps principles*. ISO.

