# NoteKeeper

## Building
```
./mvnw package
```

## Running
```
java -jar target/*.jar
```

# Curl examples

## Get

```
curl -v localhost:8080/notes
curl -v localhost:8080/notes/1
```

## POST

```
curl -v -X POST localhost:8080/notes -H 'Content-type:application/json' -d '{"title":"Note 1.0","text":"text"}'
```

## PUT

```
curl -v -X PUT  localhost:8080/notes/1 -H 'Content-type:application/json' -d '{"title":"Note 1.1","text":"text"}'
```

## DELETE

```
curl -v -X DELETE  localhost:8080/notes/1
```



