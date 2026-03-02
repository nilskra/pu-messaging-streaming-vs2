# pu-messaging-streaming

Kleines Beispielprojekt für **Event-driven Communication** mit **Quarkus** und **Kafka/Redpanda**.

## Architektur

**blogBackend** (REST + MySQL) erstellt Blogposts und publiziert nach dem Speichern eine Validierungsanfrage an Kafka.
Der **text-validation-service** konsumiert diese Anfrage, prüft den Text (Blocklist) und publiziert das Ergebnis zurück.
Das **blogBackend** konsumiert die Antwort und setzt den Status des Blogposts entsprechend.

**Kafka Topics**

* `blog-validation-request` – Validierungsanfragen (JSON String)
* `blog-validation-response` – Validierungsergebnisse (JSON String)

## Services & Ports (docker-compose)

| Service | Zweck | Port (Host → Container) |
|---|---|---|
| MySQL | Persistenz für blogBackend | `3306 → 3306` |
| Redpanda | Kafka-compatible Broker | `9092 → 9092` |
| Redpanda Console | Kafka UI | `8088 → 8080` |
| blogBackend | REST API | `8080 → 8080` |
| text-validation-service | Validator | `8081 → 8081` |

## Quickstart (alles via Docker)

```bash
docker compose up -d
```

Danach:

* REST API: `http://localhost:8080`
* Validator: `http://localhost:8081`
* Redpanda Console: `http://localhost:8088`

### Beispiel-Flow

1) Blogpost erstellen (Status startet als `PENDING`):

```bash
curl -s -X POST http://localhost:8080/blogs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Hallo","content":"Das ist sauberer Content."}'
```

2) Blogpost abfragen (nach kurzer Zeit wird `status` zu `APPROVED` oder `REJECTED`):

```bash
curl -s http://localhost:8080/blogs/1
```

3) Nur freigegebene Blogs listen:

```bash
curl -s http://localhost:8080/blogs
```

> Tipp: In der Redpanda Console (8088) kannst du die Topics und Nachrichten live ansehen.

## Troubleshooting

* **MySQL ist noch nicht ready**: `docker compose ps` prüfen – der `blogbackend` Service hängt am `mysql` Healthcheck.
* **Keine Messages in Kafka**: In der Redpanda Console prüfen, ob `blog-validation-request` / `blog-validation-response` existieren.
* **Status bleibt PENDING**: Validator läuft? (Container `vs2-validator`) und `KAFKA_BOOTSTRAP_SERVERS` korrekt gesetzt?
