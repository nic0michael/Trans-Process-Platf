# Transaction Processing Platform — Frontend

Angular frontend for the **Transaction Processing Platform**.

## Technology

* Angular CLI 20.3.37
* Angular 20
* Node.js 20.20.2
* npm 10.8.2
* TypeScript
* CSS

## Prerequisites

Verify the installed versions:

```bash
node --version
npm --version
ng version
```

Expected versions:

```text
Node.js:       v20.20.2
npm:           10.8.2
Angular CLI:   20.3.37
```

## Project Structure

The frontend uses the standard Angular project structure:

```text
frontend/
├── src/
│   ├── app/
│   ├── assets/
│   ├── index.html
│   └── main.ts
├── angular.json
├── package.json
├── tsconfig.json
└── ...
```

Angular tests use the standard `*.spec.ts` convention.

Example:

```text
transaction.service.ts
transaction.service.spec.ts
```

## Development

From the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the Angular development server:

```bash
ng serve
```

The application is normally available at:

```text
http://localhost:4200
```

## Build

Create a production build:

```bash
ng build
```

Build output is created under:

```text
dist/
```

## Testing

Run the Angular unit tests:

```bash
ng test
```

Tests are located using Angular's standard `*.spec.ts` naming convention.

## Backend Integration

The Angular frontend communicates with the Spring Boot REST API.

Backend API base path:

```text
/api-v1/transactions
```

The frontend will use the backend REST API for transaction processing and transaction queries.

## Development Architecture

```text
Angular Frontend
       |
       | REST / JSON
       v
Spring Boot API
       |
       v
Kafka / PostgreSQL / MongoDB
```

The frontend is responsible for the user interface and communication with the REST API.

Business processing remains in the backend.

## Angular CLI

The project was created using Angular CLI 20:

```bash
ng new transaction-frontend --directory frontend --skip-git
```

The project uses:

* Standalone Angular components
* Zone.js
* CSS
* No SSR
* No SSG/prerendering
* Standard Angular testing
