package com.example.hhplus.concert.domain.concert.model;

public record ConcertSeat(
    Long id, Long concertScheduleId, Long number, Long price, Boolean isReserved
) {

}
