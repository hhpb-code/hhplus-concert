package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import jakarta.validation.constraints.NotNull;

public class WaitingQueueQuery {

  public record GetWaitingQueueByIdQuery(
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_ID_NULL_MESSAGE)
      Long id
  ) {

  }

  public record GetWaitingQueueByUuid(
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_UUID_EMPTY_MESSAGE)
      String uuid
  ) {

  }

  public record GetWaitingQueuePositionByUuid(
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_UUID_EMPTY_MESSAGE)
      String uuid
  ) {

  }

  public record FindDistinctConcertIdsByStatusQuery(
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_STATUS_NULL_MESSAGE)
      WaitingQueueStatus status
  ) {

  }

  public record CountWaitingQueueByConcertIdAndStatusQuery(
      @NotNull(message = WaitingQueueConstants.CONCERT_ID_NULL_MESSAGE)
      Long concertId,
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_STATUS_NULL_MESSAGE)
      WaitingQueueStatus status
  ) {

  }

}
