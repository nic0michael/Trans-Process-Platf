Please note that, in this diagram, the **ExternalSystem** class represents an **External System** or the Frontend **Angular System**
```mermaid id="8n4q2m"
classDiagram

    class ExternalSystem
    class RestController
    class Service
    class Repository {
        <<interface>>
    }
    class DataBase {
        <<database>>
    }
    class QueProducer
    class Kafka {
        <<ProcessQueue>>
    }
    class Request
    class Response
    class TransactionDTO
    class TransactionEntity
    class TransactionMessage

    ExternalSystem --> RestController
    RestController --> ExternalSystem

    RestController --> Service
    Service --> Repository
    Repository --> DataBase

    Service --> QueProducer
    QueProducer --> Kafka

    ExternalSystem *-- Request : composition
    RestController *-- Response : composition
    RestController o-- Request : aggregation
    RestController *-- TransactionDTO : composition

    Service o-- TransactionDTO : aggregation
    Service *-- TransactionEntity : composition
    Service *-- TransactionMessage : composition

    QueProducer o-- TransactionMessage : aggregation
```
