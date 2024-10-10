package com.example.hhplus.concert.domain.concert.model;

import java.time.LocalDateTime;

public record Reservation(
    Long id, Long concertSeatId, Long userId, ReservationStatus status, LocalDateTime reservedAt
) {

}
