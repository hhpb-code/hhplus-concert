# ERD

## 콘서트 예약 시스템 ERD

모든 Entity 에는 생성 시간과 수정 시간을 관리하기 위한 공통 필드가 존재합니다.
Entity 의 생성 시간과 수정 시간이 중요하지 않은 경우에는 ERD 에선 생략하였습니다.

```mermaid
---
title: "콘서트 예약 시스템 ERD"
---

erDiagram
    User {
        Long id PK "AUTO_INCREMENT"
        String name
    }

    Balance {
        Long id PK "AUTO_INCREMENT"
        Long user_id FK, UK
        Long amount
    }

    QueueToken {
        Long id PK "AUTO_INCREMENT"
        Long user_id FK
        QueueTokenStatus status "WAITING, PROCESSING, EXPIRED"
        Datetime expired_at
    }

    Concert {
        Long id PK "AUTO_INCREMENT"
        String title
        String description
    }

    ConcertSchedule {
        Long id PK "AUTO_INCREMENT"
        Long concert_id FK
        Datetime concert_at
        DateTime reservation_start_at
        DateTime reservation_end_at
    }

    ConcertSeat {
        Long id PK "AUTO_INCREMENT"
        Long concert_schedule_id FK
        Long number "concert_schedule_id, number 복합키"
        Long price
        Boolean is_reserved
    }

    Reservation {
        Long id PK "AUTO_INCREMENT"
        Long concert_seat_id FK
        Long user_id FK
        Enum status "WAITING, CONFIRMED, CANCELED"
        Datetime reserved_at
    }

    Payment {
        Long id PK "AUTO_INCREMENT"
        Long reservation_id FK
        Long user_id FK
        Long amount
    }

    User ||--o{ QueueToken: ""
    User ||--|| Balance: ""
    Concert ||--|{ ConcertSchedule: ""
    ConcertSchedule ||--|{ ConcertSeat: ""
    ConcertSeat ||--o| Reservation: ""
    Reservation ||--o| Payment: ""
    User ||--o{ Reservation: ""
    User ||--o{ Payment: ""
```