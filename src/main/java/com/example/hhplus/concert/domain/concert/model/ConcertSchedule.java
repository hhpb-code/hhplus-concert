package com.example.hhplus.concert.domain.concert.model;

import java.time.LocalDateTime;

public record ConcertSchedule(
    Long id, Long concertId, LocalDateTime concertAt,
    LocalDateTime reservationStartAt,
    LocalDateTime reservationEndAt
) {


}
