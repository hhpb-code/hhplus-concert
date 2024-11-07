package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;

public class WaitingQueueRepositoryParam {

  public record GetWaitingQueueByIdParam(
      Long id
  ) {

  }

  public record GetWaitingQueueByUuidParam(
      String uuid
  ) {

  }

  public record GetWaitingQueueByUuidWithLockParam(
      String uuid
  ) {

  }


  public record GetWaitingQueuePositionByUuidAndConcertIdParam(
      String uuid,
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

  public record FindAllWaitingQueuesByStatusWithLimitAndLockParam(
      WaitingQueueStatus status,
      int limit
  ) {

  }

}
