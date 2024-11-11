package com.example.hhplus.concert.domain.waitingqueue.dto;

public class WaitingQueueRepositoryParam {

  public record GetWaitingQueueByUuidParam(
      String uuid
  ) {

  }

  public record GetWaitingQueuePositionByUuidParam(
      String uuid
  ) {

  }

}
