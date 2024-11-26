package com.example.hhplus.concert.domain.event.service;

import com.example.hhplus.concert.domain.event.EventConstants;
import com.example.hhplus.concert.domain.event.EventPublisher;
import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.CreateOutboxEventCommand;
import com.example.hhplus.concert.domain.event.dto.OutboxEventCommand.PublishOutboxEventCommand;
import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.FindAllByStatusAndRetryCountAndRetryAtBeforeWithLockParam;
import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByIdWithLockParam;
import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import com.example.hhplus.concert.domain.event.repository.OutboxEventRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
    try {
      outboxEventRepository.save(outboxEvent);
      eventPublisher.publish(outboxEvent.getType(), outboxEvent.getPayload());
    } catch (RuntimeException e) {
      outboxEvent.fail();
      outboxEventRepository.save(outboxEvent);
      log.error("Failed to publish outbox event", e);
    }
  }

  public void retryFailedOutboxEvents() {
    final List<OutboxEvent> failedEvents = outboxEventRepository.findAllByStatusAndRetryCountAndRetryAtBefore(
        new FindAllByStatusAndRetryCountAndRetryAtBeforeWithLockParam(OutboxEventStatus.FAILED,
            EventConstants.MAX_RETRY_COUNT, LocalDateTime.now()));

    failedEvents.forEach(OutboxEvent::retry);

    outboxEventRepository.saveAll(failedEvents);
  }
}
