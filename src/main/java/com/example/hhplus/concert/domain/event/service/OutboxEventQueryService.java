package com.example.hhplus.concert.domain.event.service;

import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByStatusParam;
import com.example.hhplus.concert.domain.event.model.OutboxEvent;
import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import com.example.hhplus.concert.domain.event.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OutboxEventQueryService {

  private final OutboxEventRepository outboxEventRepository;

  public OutboxEvent getPendingOutboxEvent() {
    return outboxEventRepository.findByStatus(new GetByStatusParam(OutboxEventStatus.PENDING));
  }
}
