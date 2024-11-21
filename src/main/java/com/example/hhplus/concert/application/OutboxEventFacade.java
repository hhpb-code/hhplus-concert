package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.PublishOutboxEventCommand;
import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.service.OutboxEventCommandService;
import com.example.hhplus.concert.domain.event.service.OutboxEventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventFacade {

  private final OutboxEventCommandService outboxEventCommandService;

  private final OutboxEventQueryService outboxEventQueryService;

  public void publishOutboxEvent() {
    OutboxEvent event = outboxEventQueryService.getPendingOutboxEvent();

    if (event == null) {
      return;
    }

    outboxEventCommandService.publishOutboxEvent(new PublishOutboxEventCommand(event.getId()));
  }

}
