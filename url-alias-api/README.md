# URL Alias API

Rest API handling server-side logic and data management for the URl Alias app.

## Building the JAR

### Prerequisites
- Docker desktop is running
- Maven and JDK 21 are available

Compile the application JAR by running `mvn clean install` via CLI or IntelliJ Maven plugin.

This will build generated classes, compile the code and run all unit and integration tests against it.

Note that integration tests require setting up dependency containers
configured in [the compose-api-dependencies.yaml file](./compose-api-dependencies.yaml).
As such, please ensure an empty Docker environment before building.

## Configuration

This application uses Spring profiles for environment management. 
Profiles can be set by configuring the property `spring.profiles.active` with the profiles you want.
Via the CLI, you can add `--spring.profiles.active=local`.

### Available profiles

- `local`
  - Automatically hosts dependencies via docker-compose
  - Used by integration tests

## OpenAPI Spec Additions
- Added operationId used to generate API method names