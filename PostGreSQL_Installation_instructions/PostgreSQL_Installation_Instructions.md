# ADR-025 — PostgreSQL Local Development Environment

**ADR Reference:** `2026-09-11 10:48`
**Project:** Nico's Java Project / `Trans-Process-Platf`
**Decision:** Run PostgreSQL as a Docker container inside a Proxmox VM using Docker Compose.
**Environment:** Proxmox VM with Docker. Proxmox environment also has K3s Kubernetes.

These instructions are for the **local development environment**. PostgreSQL data must persist independently of the container lifecycle. Docker's PostgreSQL guidance recommends persistent storage for this purpose. ([Docker Documentation][1])

## 1. Directory structure

On the Proxmox VM:

```text
./
├── compose.yaml
├── .env
└── db/
```

The `./` path is intentionally used as the project root.

Create it:

```bash
mkdir -p ./db
cd ./
```

---

## 2. Create `.env`

Create:

```bash
nano .env
```

Use these **default development values**.

```dotenv
# PLEASE CHANGE THESE VALUES FOR YOUR ENVIRONMENT

POSTGRES_USER=nico
POSTGRES_PASSWORD=change_me
POSTGRES_DB=transaction_platform

POSTGRES_PORT=5432
```

**Important:** `.env` should not be committed to Git because it contains credentials. Docker Compose automatically reads `.env` from the project directory for variable substitution. ([Docker Documentation][2])

Add to `.gitignore`:

```text
.env
```

---

## 3. Create `compose.yaml`

Create:

```bash
nano compose.yaml
```

Use:

```yaml
services:

  postgres:
    image: postgres:18
    container_name: nicos-postgres
    restart: unless-stopped

    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}

    ports:
      - "${POSTGRES_PORT}:5432"

    volumes:
      - ./db:/var/lib/postgresql/data

    healthcheck:
      test:
        [
          "CMD-SHELL",
          "pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}"
        ]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s
```

The official PostgreSQL image supports `POSTGRES_USER`, `POSTGRES_PASSWORD` and `POSTGRES_DB`. These initialization values are applied when the database is first created. ([Docker Documentation][1])

---

## 4. Check the Compose configuration

Before starting PostgreSQL:

```bash
docker compose config
```

Check that the configuration is valid and that the environment variables have been substituted.

---

## 5. Start PostgreSQL

```bash
docker compose up -d
```

Check the container:

```bash
docker ps
```

You should see:

```text
nicos-postgres
```

---

## 6. Check PostgreSQL health

```bash
docker compose ps
```

The container should eventually show:

```text
healthy
```

You can also check the logs:

```bash
docker compose logs postgres
```

Or:

```bash
docker logs nicos-postgres
```

---

## 7. Test PostgreSQL

Connect directly to PostgreSQL inside the container:

```bash
docker exec -it nicos-postgres \
  psql -U nico -d transaction_platform
```

Then:

```sql
SELECT version();
```

And:

```sql
\l
```

Exit:

```sql
\q
```

---

## 8. Test from the Proxmox VM

If `psql` is installed on the VM:

```bash
psql \
  -h localhost \
  -p 5432 \
  -U nico \
  -d transaction_platform
```

Enter the password from `.env`.

---

## 9. PostgreSQL data persistence

The important part of the Compose file is:

```yaml
volumes:
  - ./db:/var/lib/postgresql/data
```

This means the database files are stored under:

```text
./db/
```

rather than existing only inside the container.

Therefore:

```bash
docker compose down
```

does **not** remove the database files.

Starting it again:

```bash
docker compose up -d
```

will reuse the existing database.

**Do not delete `./db` unless you intentionally want to destroy the local database.**

---

## 10. Connecting the Java application

When the Java application runs **inside the same Docker Compose network**, it should use:

```text
postgres
```

as the database hostname.

For example:

```text
jdbc:postgresql://postgres:5432/transaction_platform
```

When the Java application runs directly on the VM:

```text
jdbc:postgresql://localhost:5432/transaction_platform
```

Docker Compose automatically creates networking between services in the same Compose project. ([Docker Documentation][1])

---

## 11. Proxmox / K3s consideration

The architecture is currently:

```text
Proxmox
│
├── VM
│   │
│   └── Docker
│       │
│       └── PostgreSQL
│
└── K3s
```

For this project, **do not put PostgreSQL into K3s yet**.

The development architecture deliberately keeps:

```text
PostgreSQL → Docker Compose
Kafka      → Docker Compose
Application → Docker Compose
```

K3s will be used later when we practice the **Kubernetes deployment architecture**.

This gives us a useful progression:

```text
Docker Compose
      ↓
Integration Testing
      ↓
Kubernetes
      ↓
Helm
      ↓
Scaling / HA / Observability
```

---

## 12. Useful commands

### Start

```bash
docker compose up -d
```

### Stop

```bash
docker compose stop
```

### Start again

```bash
docker compose start
```

### Stop and remove container

```bash
docker compose down
```

### View logs

```bash
docker compose logs -f postgres
```

### Check status

```bash
docker compose ps
```

### Open PostgreSQL

```bash
docker exec -it nicos-postgres \
  psql -U nico -d transaction_platform
```

### Restart PostgreSQL

```bash
docker compose restart postgres
```

---

## 13. Architecture decision

This decision gives us a **repeatable, portable PostgreSQL development environment**.

The important architecture principles being practiced are:

* **Infrastructure as Code** — Compose defines the database environment.
* **Configuration separation** — credentials are in `.env`.
* **Persistent data** — database storage is outside the container lifecycle.
* **Health checking** — PostgreSQL health is explicitly monitored.
* **Reproducibility** — another developer can recreate the environment.
* **Containerisation** — PostgreSQL is isolated from the VM host.
* **Kubernetes readiness** — the database architecture can later be evaluated separately for K3s.

Docker's current PostgreSQL guidance also demonstrates Compose-based PostgreSQL with persistent storage and health checking. ([Docker Documentation][1])

**This is a development environment, not a production PostgreSQL HA design.** Production would require additional decisions around backup, replication, failover, security, storage and recovery.

[1]: https://docs.docker.com/guides/postgresql/?utm_source=chatgpt.com "PostgreSQL specific guide | Docker Docs"
[2]: https://docs.docker.com/compose/how-tos/environment-variables/variable-interpolation/?utm_source=chatgpt.com "Set, use, and manage variables in a Compose file with interpolation | Docker Docs"
