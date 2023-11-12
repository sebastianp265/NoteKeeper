# NoteKeeper Backend

## Build and run manually

### Building
```
./mvnw package
```

### Running
```
java -Dspring.profiles.active=local -jar target/*.jar  
```

## Swagger - OpenAPI

Swagger UI URL:\
http://localhost:8080/swagger-ui/index.html

# Docker

## Build by maven plugin

```
./mvnw spring-boot:build-image
```

## Build by dockerfile

```
docker build -t notekeeper .
```

## Running

```
sudo docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=local" notekeeper:0.0.1-SNAPSHOT
```
