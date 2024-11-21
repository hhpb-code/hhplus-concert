package com.example.hhplus.concert.domain.event.service;

import com.example.hhplus.concert.domain.event.EventPublisher;
import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.CreateOutboxEventCommand;
import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.PublishOutboxEventCommand;
import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByIdWithLockParam;
import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import com.example.hhplus.concert.domain.event.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OutboxEventCommandService {

  private final OutboxEventRepository outboxEventRepository;

  private final EventPublisher eventPublisher;

  public OutboxEvent createOutboxEvent(CreateOutboxEventCommand command) {
    return outboxEventRepository.save(OutboxEvent.builder()
        .type(command.event().getType())
        .payload(command.event().getPayload())
        .status(OutboxEventStatus.PENDING)
        .build());
  }

  public void publishOutboxEvent(PublishOutboxEventCommand command) {
    final OutboxEvent outboxEvent = outboxEventRepository.getById(
        new GetByIdWithLockParam(command.id()));

    outboxEvent.publish();

    eventPublisher.publish(outboxEvent.getType(), outboxEvent.getPayload());

    outboxEventRepository.save(outboxEvent);
  }
}
