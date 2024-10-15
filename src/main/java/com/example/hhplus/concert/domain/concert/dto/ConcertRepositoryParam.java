package com.example.hhplus.concert.domain.concert.dto;

public class ConcertRepositoryParam {

  public record GetReservationByIdParam(Long id) {

  }

  public record GetConcertSeatByIdWithLockParam(Long concertSeatId) {

  }

}
