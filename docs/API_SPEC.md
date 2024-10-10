# API SPEC

## 콘서트 예약 서비스 API 명세서

### 1. 대기열 토큰 발급 API

#### Request

- **URL**: `/api/v1/queue-tokens`
- **Method**: `POST`
- **Headers**:
  - Content-Type: application/json
- **Body**:
  ```json
  {
    "userId": "Integer"
  }
  ```

#### Response

- **Status Code**: 201 Created
  ```json
  {
    "queueToken": {
        "id": "Integer",
        "userId": "Integer",
        "status": "Enum",
        "expiredAt": "DateTime"
    }
  }
  ```

### 2. 예약 가능 날짜 조회 API

#### Request

- **URL**: `/api/v1/concerts/{concertId}/available-schedules`
- **Method**: `GET`
- **Headers**:
  - Content-Type: application/json
  - X-Queue-Token: [대기열 토큰]
- **Path Parameters**:
  - `concertId`: `Integer`

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "concertSchedules": [
        {
            "id": "Integer",
            "concertId": "Integer",
            "concertAt": "DateTime",
            "reservationStartAt": "DateTime",
            "reservationEndAt": "DateTime"
        }
    ]
  }
  ```

### 3. 예약 가능 좌석 조회 API

#### Request

- **URL**: `/api/v1/concert-schedules/{scheduleId}/available-seats`
- **Method**: `GET`
- **Headers**:
  - Content-Type: application/json
  - X-Queue-Token: [대기열 토큰]
- **Path Parameters**:
  - `scheduleId`: `Integer`

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "concertSeats": [
        {
            "id": "Integer",
            "concertScheduleId": "Integer",
            "number": "Integer",
            "price": "Integer",
            "isReserved": "Boolean"
        }
    ]
  }
  ```

### 4. 좌석 예약 API

#### Request

- **URL**: `/api/v1/reservations`
- **Method**: `POST`
- **Headers**:
  - Content-Type: application/json
  - X-Queue-Token: [대기열 토큰]
- **Body**:
  ```json
    {
        "concertSeatId": "Integer",
        "userId": "Integer"
    }
  ```

#### Response

- **Status Code**: 201 Created
  ```json
  {
    "reservation": {
        "id": "Integer",
        "concertSeatId": "Integer",
        "userId": "Integer",
        "status": "Enum",
        "reservedAt": "DateTime"
    }
  }
  ```

### 5. 잔액 충전 API

#### Request

- **URL**: `/api/v1/users/{userId}/balances/{balanceId}`
- **Method**: `PUT`
- **Headers**:
  - Content-Type: application/json
- **Path Parameters**:
  - `userId`: `Integer`
  - `balanceId`: `Integer`
- **Body**:
  ```json
    {
        "amount": "Integer",
        "operationType": "Enum"
    }
  ```

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "balance": {
        "id": "Integer",
        "userId": "Integer",
        "amount": "Integer"
    }
  }
  ```

### 6. 잔액 조회 API

#### Request

- **URL**: `/api/v1/users/{userId}/balances`
- **Method**: `GET`
- **Headers**:
  - Content-Type: application/json
- **Query Parameters**:
  - `userId`: `Integer`

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "balance": {
        "id": "Integer",
        "userId": "Integer",
        "amount": "Integer"
    }
  }
  ```

### 7. 결제 API

#### Request

- **URL**: `/api/v1/reservations/{reservationId}/payments`
- **Method**: `POST`
- **Headers**:
  - Content-Type: application/json
- **Path Parameters**:
  - `reservationId`: `Integer`
- **Body**:
  ```json
    {
        "amount": "Long"
    }
  ```

#### Response

- **Status Code**: 201 Created
  ```json
  {
    "payment": {
        "id": "Integer",
        "reservationId": "Integer",
        "userId": "Integer",
        "amount": "Integer"
    }
  }
  ```