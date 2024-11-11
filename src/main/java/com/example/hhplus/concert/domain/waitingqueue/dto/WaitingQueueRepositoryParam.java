package com.example.hhplus.concert.domain.waitingqueue.dto;

import java.util.concurrent.TimeUnit;

public class WaitingQueueRepositoryParam {

  public record GetWaitingQueuePositionByUuidParam(
      String uuid
  ) {

  }

  public record ActivateWaitingQueuesParam(
      int availableSlots,
      long timeout,
      TimeUnit unit
  ) {

  }
}
