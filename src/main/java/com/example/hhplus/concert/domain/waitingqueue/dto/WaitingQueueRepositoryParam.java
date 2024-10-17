package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;

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

  public record FindDistinctConcertIdsByStatusParam(
      WaitingQueueStatus status
  ) {

  }

  public record CountWaitingQueueByConcertIdAndStatusParam(
      Long concertId,
      WaitingQueueStatus status
  ) {

  }

  public record FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam(
      Long concertId,
      WaitingQueueStatus status,
      int limit
  ) {

  }

}
