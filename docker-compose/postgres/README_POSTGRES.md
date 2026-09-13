# PostgreSQL Development Environment

## PostgreSQL Configuration

* PostgreSQL 17
* Docker Compose
* Port: 5432
* Persistent data directory: `./data`

## Directory Structure

```text
docker/
└── compose/
    └── postgres/
        ├── .env
        ├── compose.yaml
        └── data/
```

## Environment Configuration

The `.env` file contains:

```dotenv
POSTGRES_DB=mydatabase
POSTGRES_USER=myuser
POSTGRES_PASSWORD=ChangeThisToAStrongPassword
POSTGRES_PORT=5432
```

`POSTGRES_DB`, `POSTGRES_USER`, and `POSTGRES_PASSWORD` are used by the PostgreSQL Docker image when the database is initially created.

## Start PostgreSQL

```bash
docker compose up -d
```

## Stop PostgreSQL

```bash
docker compose stop
```

## Check PostgreSQL

```bash
docker ps
```

View the container logs:

```bash
docker logs postgres
```

## Test PostgreSQL

Connect to PostgreSQL from inside the container:

```bash
docker exec -it postgres psql \
  -U myuser \
  -d mydatabase
```

Run:

```sql
SELECT version();
```

Test the database:

```sql
SELECT current_database();
```

Exit:

```sql
\q
```

## Data Persistence

PostgreSQL data is stored in the local `data` directory:

```yaml
volumes:
  - ./data:/var/lib/postgresql/data
```

For PostgreSQL 17 and earlier, `/var/lib/postgresql/data` is the appropriate container location for persistent database storage.

The database data therefore remains available when the PostgreSQL container is stopped, restarted, or recreated.

## PostgreSQL Connection

Applications can connect using:

```text
Host:     <PostgreSQL-server>
Port:     5432
Database: mydatabase
Username: myuser
Password: ChangeThisToAStrongPassword
```

## Docker Compose File

```yaml
services:
  postgres:
    image: postgres:17
    container_name: postgres
    restart: unless-stopped

    env_file:
      - .env

    ports:
      - "${POSTGRES_PORT}:5432"

    volumes:
      - ./data:/var/lib/postgresql/data

    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}"]
      interval: 10s
      timeout: 5s
      retries: 5
```

## Test Result

PostgreSQL is ready when the container is running and the following command successfully connects:

```bash
docker exec -it postgres psql -U myuser -d mydatabase
```
