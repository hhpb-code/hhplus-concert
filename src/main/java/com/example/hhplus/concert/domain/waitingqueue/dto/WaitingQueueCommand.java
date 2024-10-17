package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import jakarta.validation.constraints.NotNull;

public class WaitingQueueCommand {

  public record CreateWaitingQueueCommand(
      @NotNull(message = WaitingQueueConstants.CONCERT_ID_NULL_MESSAGE)
      Long concertId
  ) {

  }


  public record ActivateWaitingQueuesCommand(
      @NotNull(message = WaitingQueueConstants.CONCERT_ID_NULL_MESSAGE)
      Long concertId,
      @NotNull(message = WaitingQueueConstants.AVAILABLE_SLOTS_NULL_MESSAGE)
      Integer availableSlots
  ) {

  }

}
