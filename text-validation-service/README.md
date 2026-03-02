# text-validation-service

Validator-Service (Quarkus), der Textinhalte von Blogposts asynchron über Kafka/Redpanda prüft.

## Was macht der Service?

* Konsumiert Validierungsanfragen aus Kafka (`blog-validation-request`).
* Prüft den Text gegen eine einfache **Blocklist** (siehe `ValidationProcessor`).
* Publiziert das Validierungsergebnis zurück nach Kafka (`blog-validation-response`).

> Dieser Service hat keine eigene DB.

## Messaging

### Kafka Topics

* Incoming: `blog-validation-request` (Channel: `validation-requests`)
* Outgoing: `blog-validation-response` (Channel: `validation-responses`)

### Payloads (JSON)

**Request**

```json
{ "blogId": 1, "text": "..." }
```

**Response**

```json
{ "blogId": 1, "approved": true, "reason": "OK" }
```

## HTTP

Der Service läuft standardmäßig auf Port **8081**.

In diesem Beispiel gibt es bewusst keine REST-Endpunkte (reines Streaming/Messaging).

## Konfiguration

### Wichtige Environment Variables (Profil `prod`)

| Variable | Beispiel | Zweck |
|---|---|---|
| `KAFKA_BOOTSTRAP_SERVERS` | `redpanda:9092` | Kafka Bootstrap Servers |

> Im Docker-Setup wird `QUARKUS_PROFILE=prod` gesetzt und `KAFKA_BOOTSTRAP_SERVERS` injiziert.

## Lokal starten

Im Repo-Root:

```bash
cd text-validation-service
./mvnw quarkus:dev
```

## Build

```bash
./mvnw clean package
```

JAR starten:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```
