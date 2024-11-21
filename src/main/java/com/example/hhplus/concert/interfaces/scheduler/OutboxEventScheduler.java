package com.example.hhplus.concert.interfaces.scheduler;

import com.example.hhplus.concert.application.OutboxEventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OutboxEventScheduler {

  private final OutboxEventFacade outboxEventFacade;


  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void publishOutboxEvent() {
    outboxEventFacade.publishOutboxEvent();
  }
}
