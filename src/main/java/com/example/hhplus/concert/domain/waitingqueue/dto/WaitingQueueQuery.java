package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import jakarta.validation.constraints.NotNull;

public class WaitingQueueQuery {

  public record GetWaitingQueueByIdQuery(
      @NotNull(message = WaitingQueueConstants.WAITING_QUEUE_ID_NULL_MESSAGE)
      Long id
  ) {

  }

}
