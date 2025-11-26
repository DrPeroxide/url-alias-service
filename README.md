# URL Shortener

A simple utility for shortening urls. Composed of a Vue.js webapp supported by a Spring Boot REST API and mongoDB database.

> [!TIP]
> See documentation for individual services here:
>
> - [Rest API](url-alias-api/README.md)
> - [WEB UI](url-alias-webui/README.md)

## Quick Start

> [!IMPORTANT]
> **Prerequisites**
>
> - Docker daemon is available
> - Ports 8080 and 8081 are free

Build and run the service locally with the following commands

```bash
docker compose build #build
docker compose up -d #run
```

The web UI will be hosted on [http://localhost:8080](http://localhost:8080).
The backend REST API can be also interacted with via curl or another HTTP client on `http://localhost:8081`.

Shut down the service when you are done with `docker compose down`.
