# Inter-City Cab Management Portal

This is a ConverzAI cab Management Portal Assignment. It keeps the business logic in memory and exposes the functionality through REST APIs.

## Features covered

- Onboard cities
- Register cabs
- Change cab city
- Change cab state
- Book cabs based on availability
- Assign the longest-idle cab first
- Randomly choose between tied cabs
- Return cab snapshots
- Bonus feature implemented: track cab history for each cab

## Requirements

```bash
mvn -version
```

- Java 17 or newer
- Maven 3.9 or newer

## Run the app

```bash
git clone https://github.com/asmijafar20/Cab-Management-Portal.git
cd Cab-Management-Portal
mvn spring-boot:run
```

## Run tests

```bash
cd Cab-Management-Portal
mvn test
```

The app starts on `http://localhost:8080`.

## API summary

- `POST /api/cities` with `{"cityId":"HYD"}`
- `GET /api/cities`
- `POST /api/cabs` with `{"cabId":"CAB-1","cityId":"HYD"}`
- `PUT /api/cabs/{cabId}/city` with `{"cityId":"BLR"}`
- `PUT /api/cabs/{cabId}/state` with `{"state":"IDLE"}`
- `GET /api/cabs`
- `GET /api/cabs/{cabId}/history`
- `POST /api/bookings` with `{"cityId":"HYD"}`

## Curl commands

```bash
curl -X POST http://localhost:8080/api/cities \
  -H "Content-Type: application/json" \
  -d '{"cityId":"HYD"}'
```

```bash
curl -X POST http://localhost:8080/api/cities \
  -H "Content-Type: application/json" \
  -d '{"cityId":"BLR"}'
```

```bash
curl -X POST http://localhost:8080/api/cabs \
  -H "Content-Type: application/json" \
  -d '{"cabId":"CAB-1","cityId":"HYD"}'
```

```bash
curl -X POST http://localhost:8080/api/cabs \
  -H "Content-Type: application/json" \
  -d '{"cabId":"CAB-2","cityId":"HYD"}'
```

```bash
curl http://localhost:8080/api/cities
```

```bash
curl http://localhost:8080/api/cabs
```

```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{"cityId":"HYD"}'
```

```bash
curl -X PUT http://localhost:8080/api/cabs/CAB-1/state \
  -H "Content-Type: application/json" \
  -d '{"state":"IDLE"}'
```

```bash
curl -X PUT http://localhost:8080/api/cabs/CAB-1/city \
  -H "Content-Type: application/json" \
  -d '{"cityId":"BLR"}'
```

```bash
curl http://localhost:8080/api/cabs/CAB-1/history
```
