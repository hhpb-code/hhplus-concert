package com.example.hhplus.concert.domain.waitingqueue.dto;

public class WaitingQueueRepositoryParam {

  public record GetWaitingQueueByIdParam(
      Long id
  ) {

  }

  public record GetWaitingQueueByUuidWithLockParam(
      String uuid
  ) {

  }


  public record GetWaitingQueuePositionByIdAndConcertIdParam(
      Long id,
      Long concertId
  ) {

  }

}
