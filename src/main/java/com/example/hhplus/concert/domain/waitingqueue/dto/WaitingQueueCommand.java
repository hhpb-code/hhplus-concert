package com.example.hhplus.concert.domain.waitingqueue.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class WaitingQueueCommand {

  public record ActivateWaitingQueuesCommand(Integer availableSlots) {

    public ActivateWaitingQueuesCommand {
      if (availableSlots == null) {
        throw new CoreException(ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_NOT_BE_NULL);
      }
      if (availableSlots <= 0) {
        throw new CoreException(ErrorType.WaitingQueue.AVAILABLE_SLOTS_MUST_BE_POSITIVE);
      }
    }
  }
}
