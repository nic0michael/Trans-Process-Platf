````markdown
# Kafka Development Environment

## Deployment instructions
```bash
# run these commands:
docker compose pull

docker compose up -d

```

## Kafka Configuration

- Apache Kafka 4.3.1
- KRaft mode
- Single broker
- Port: 9092
- Data directory: `./data`

## Directory Structure

```text
docker/
└── compose/
    └── kafka/
        ├── .env
        ├── compose.yaml
        └── data/
````

## Start Kafka

```bash
docker compose up -d
```

## Stop Kafka

```bash
docker compose stop
```

## Check Kafka

```bash
docker ps
```

## Create Test Topic

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic test-topic \
  --bootstrap-server localhost:9092
```

Expected:

```text
Created topic test-topic.
```

## List Topics

```bash
docker exec -it kafka /opt/kafka/bin/kafka-topics.sh \
  --list \
  --bootstrap-server localhost:9092
```

## Produce Messages

Run:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh \
  --topic test-topic \
  --bootstrap-server localhost:9092
```

Enter messages:

```text
Hello Kafka
Test message 1
Test message 2
```

Press `Ctrl-C` to stop the producer.

## Consume Messages

Open a second terminal and run:

```bash
docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh \
  --topic test-topic \
  --from-beginning \
  --bootstrap-server localhost:9092
```

Expected:

```text
Hello Kafka
Test message 1
Test message 2
```

Press `Ctrl-C` to stop the consumer.

## Kafka Test Flow

```text
Producer
   |
   v
test-topic
   |
   v
Consumer
```

The Kafka command-line producer sends each input line as a separate event, and the consumer reads the events from the topic. ([kafka.apache.org][1])

```
```

[1]: https://kafka.apache.org/quickstart/?utm_source=chatgpt.com "Quickstart | Apache Kafka"
