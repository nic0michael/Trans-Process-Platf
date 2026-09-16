
## To launch this API
```bash
# Run this command:
./gradlew :backend:api:bootRun

```

## Swagger can be viewed here
**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
## testing with the swagger
here is a realistic transaction you can paste directly into Swagger:

```json
{
  "timestamp": "2026-09-16 16:40:00",
  "transactionIndex": "100007",
  "transactionGuid": "",
  "requestId": "b82d4f61-93a7-4c25-ae18-607f3b9d5246",
  "externalSystemId": "WEB_PORTAL",
  "transactionType": "PURCHASE",
  "currency": "ZAR",
  "amount": "3250.75",
  "reference": "TEST-TRANS-007",
  "transactionDescription": "Kafka purchase test",
  "companyId": "2001",
  "status": "PENDING"
}
```
Expect to get:

```json
{
  "responseCode": "200",
  "responseMessage": "Transaction sent to Kafka",
  "timestamp": "2026-09-16 16:40:00",
  "transactionIndex": "100007",
  "transactionGuid": "cf48ac00-a29d-4572-84a4-8894895f9547",
  "requestId": "b82d4f61-93a7-4c25-ae18-607f3b9d5246",
  "externalSystemId": "WEB_PORTAL",
  "transactionType": "PURCHASE",
  "currency": "ZAR",
  "amount": "3250.75",
  "reference": "TEST-TRANS-007",
  "transactionDescription": "Kafka purchase test",
  "companyId": "2001",
  "status": "PENDING"
}  
```
}

### Another useful test

For testing a **purchase**:

```json
{
  "timestamp": "2026-09-16 15:35:00",
  "transactionIndex": "100002",
  "transactionGuid": "",
  "requestId": "9b2f3c4d-1234-4567-8901-abcdef123456",
  "externalSystemId": "WEB_PORTAL",
  "transactionType": "PURCHASE",
  "currency": "ZAR",
  "amount": "599.99",
  "reference": "ORD-2026-0042",
  "transactionDescription": "Online purchase",
  "companyId": "1001",
  "status": "PENDING"
}
```

Expect to get:
```json
{
  "responseCode": "200",
  "responseMessage": "Transaction sent to Kafka",
  "timestamp": "2026-09-16 15:35:00",
  "transactionIndex": "100002",
  "transactionGuid": "7ed070a0-92b6-4eb2-a007-bd2070f530f5",
  "requestId": "9b2f3c4d-1234-4567-8901-abcdef123456",
  "externalSystemId": "WEB_PORTAL",
  "transactionType": "PURCHASE",
  "currency": "ZAR",
  "amount": "599.99",
  "reference": "ORD-2026-0042",
  "transactionDescription": "Online purchase",
  "companyId": "1001",
  "status": "PENDING"
}
```
Now we test getting database record with this GUID
```json
7ed070a0-92b6-4eb2-a007-bd2070f530f5
```
expect to get:
```json
{
  "responseCode": "200",
  "responseMessage": "Retrieved GUID Record from Database",
  "timestamp": "2026-09-16 15:35:00",
  "transactionIndex": "9",
  "transactionGuid": "7ed070a0-92b6-4eb2-a007-bd2070f530f5",
  "requestId": "9b2f3c4d-1234-4567-8901-abcdef123456",
  "externalSystemId": "WEB_PORTAL",
  "transactionType": "PURCHASE",
  "currency": "ZAR",
  "amount": "599.9900",
  "reference": "ORD-2026-0042",
  "transactionDescription": "Online purchase",
  "companyId": "1001",
  "status": "PENDING"
}
```
I would use the **first one initially**, as it gives you clean values to follow through REST → database → Kafka.

## we provided dockge
This is an app for monitoring Docker Containers
please look inside the docker-compose/ folder 
for instructions to run 3 docker containers
from the Stacks folder


## Open Dockage your browser and use admin user with the strong password
**[http://127.0.0.0:5001/](http://127.0.0.0:5001/)



