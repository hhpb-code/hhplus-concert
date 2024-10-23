package com.example.hhplus.concert.interfaces.scheduler;

import com.example.hhplus.concert.application.WaitingQueueFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingQueueScheduler {

  private final WaitingQueueFacade waitingQueueFacade;


  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void activateWaitingQueues() {
    waitingQueueFacade.activateWaitingQueues();
  }

  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void expireWaitingQueues() {
    waitingQueueFacade.expireWaitingQueues();
  }
}
