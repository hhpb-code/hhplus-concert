# API SPEC

## 콘서트 예약 서비스 API 명세서

### 1. 대기열 토큰 발급 API

#### Request

- **URL**: `/api/v1/waiting-queues`
- **Method**: `POST`
- **Headers**:
    - Content-Type: application/json
- **Body**:
  ```json
    {
        "concertId": "Integer"
    }
  ```

#### Response

- **Status Code**: 201 Created
  ```json
  {
    "waitingQueue": {
        "id": "Integer",
        "concertId": "Integer",
        "uuid": "String",
        "status": "Enum",
        "expiredAt": "DateTime",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```

### 2. 대기열 토큰 상태 조회 API

#### Request

- **URL**: `/api/v1/waiting-queues/position`
- **Method**: `GET`
- **Headers**:
    - Content-Type: application/json
    - X-Waiting-Queue-Token-uuid: [대기열 토큰 UUID]

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "waitingQueue": {
        "id": "Integer",
        "concert_id": "Integer",
        "uuid": "String",
        "status": "Enum",
        "expiredAt": "DateTime",
        "position": "Integer",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```

### 3. 예약 가능 날짜 조회 API

#### Request

- **URL**: `/api/v1/concerts/{concertId}/available-schedules`
- **Method**: `GET`
- **Headers**:
    - Content-Type: application/json
    - X-Waiting-Queue-Token-uuid: [대기열 토큰 UUID]
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
            "reservationEndAt": "DateTime",
            "createdAt": "DateTime",
            "updatedAt": "DateTime"
        }
    ]
  }
  ```

### 4. 예약 가능 좌석 조회 API

#### Request

- **URL**: `/api/v1/concert-schedules/{scheduleId}/available-seats`
- **Method**: `GET`
- **Headers**:
    - Content-Type: application/json
    - X-Waiting-Queue-Token-uuid: [대기열 토큰 UUID]
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
            "isReserved": "Boolean",
            "createdAt": "DateTime",
            "updatedAt": "DateTime"
        }
    ]
  }
  ```

### 5. 좌석 예약 API

#### Request

- **URL**: `/api/v1/concert-seats/{concertSeatId}/reservations`
- **Method**: `POST`
- **Headers**:
    - Content-Type: application/json
    - X-User-Id: [사용자 ID]
    - X-Waiting-Queue-Token-uuid: [대기열 토큰 UUID]
- **Body**:
  ```json
    {
        "concertSeatId": "Integer"
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
        "reservedAt": "DateTime",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```

### 6. 잔액 충전 API

#### Request

- **URL**: `/api/v1/users/{userId}/wallets/{walletId}/charge`
- **Method**: `PUT`
- **Headers**:
    - Content-Type: application/json
- **Path Parameters**:
    - `userId`: `Integer`
    - `walletId`: `Integer`
- **Body**:
  ```json
    {
        "amount": "Integer"
    }
  ```

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "wallet": {
        "id": "Integer",
        "userId": "Integer",
        "amount": "Integer",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```

### 7. 잔액 조회 API

#### Request

- **URL**: `/api/v1/users/{userId}/wallets`
- **Method**: `GET`
- **Headers**:
    - Content-Type: application/json
- **Path Parameters**:
    - `userId`: `Integer`

#### Response

- **Status Code**: 200 OK
  ```json
  {
    "wallet": {
        "id": "Integer",
        "userId": "Integer",
        "amount": "Integer",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```

### 8. 결제 API

#### Request

- **URL**: `/api/v1/reservations/{reservationId}/payments`
- **Method**: `POST`
- **Headers**:
    - Content-Type: application/json
    - X-User-Id: [사용자 ID]
- **Path Parameters**:
    - `reservationId`: `Integer`

#### Response

- **Status Code**: 201 Created
  ```json
  {
    "payment": {
        "id": "Integer",
        "reservationId": "Integer",
        "userId": "Integer",
        "amount": "Integer",
        "createdAt": "DateTime",
        "updatedAt": "DateTime"
    }
  }
  ```