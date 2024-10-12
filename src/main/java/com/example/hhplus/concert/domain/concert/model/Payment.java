package com.example.hhplus.concert.domain.concert.model;

public record Payment(
    Long id, Long reservationId, Long userId, Long amount
) {

}
