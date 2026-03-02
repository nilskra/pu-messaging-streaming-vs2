# blogBackend

REST-Backend (Quarkus) für Blogposts inkl. asynchroner Textvalidierung über Kafka/Redpanda.

## Was macht der Service?

* Persistiert Blogposts in einer Datenbank (MySQL im `prod`-Profil).
* Publiziert nach dem Erstellen eines Blogposts eine **Validierungsanfrage** nach Kafka.
* Konsumiert das **Validierungsergebnis** und setzt den Status des Blogposts auf `APPROVED` oder `REJECTED`.

## REST API

Base URL: `http://localhost:8080`

| Methode | Pfad | Beschreibung |
|---|---|---|
| `POST` | `/blogs` | Blog erstellen (Status → `PENDING` und Validation Request wird gesendet) |
| `GET` | `/blogs` | Liste aller **approved** Blogs |
| `GET` | `/blogs/{id}` | Blog nach ID (auch `PENDING` / `REJECTED`) |
| `PATCH` | `/blogs/{id}` | Blog ändern (setzt Status wieder auf `PENDING`) |
| `DELETE` | `/blogs/delete/{id}` | Blog löschen |
| `DELETE` | `/blogs/delete` | Alle Blogs löschen |

### Beispiel: Blog erstellen

```bash
curl -s -X POST http://localhost:8080/blogs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Mein Post","content":"Hello world"}'
```

## Messaging

### Kafka Topics

* Outgoing: `blog-validation-request` (Channel: `validation-requests`)
* Incoming: `blog-validation-response` (Channel: `validation-responses`)

### Payloads (JSON)

**Request** (`blog-validation-request`)

```json
{ "blogId": 1, "text": "..." }
```

**Response** (`blog-validation-response`)

```json
{ "blogId": 1, "approved": true, "reason": "OK" }
```

## Konfiguration

### Wichtige Environment Variables (Profil `prod`)

| Variable | Beispiel | Zweck |
|---|---|---|
| `DB_JDBC_URL` | `jdbc:mysql://mysql:3306/blogdb` | JDBC URL |
| `DB_USERNAME` | `app` | DB User |
| `DB_PASSWORD` | `apppw` | DB Passwort |
| `KAFKA_BOOTSTRAP_SERVERS` | `redpanda:9092` | Kafka Bootstrap Servers |

> Hinweis: Im Docker-Setup wird `QUARKUS_PROFILE=prod` verwendet.

## Lokal starten

## Lokal starten

Im Repo-Root:

```bash
cd blogBackend
./mvnw quarkus:dev
```

Dev UI at http://localhost:8080/q/dev während der Service läuft.

## Build

```bash
./mvnw clean package
```

JAR starten:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```
