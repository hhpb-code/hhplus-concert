package com.example.hhplus.concert.domain.event.dto;

import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;
import java.time.LocalDateTime;

public class OutboxEventParam {

  public record GetByIdParam(Long id) {

  }

  public record GetByIdWithLockParam(Long id) {

  }

  public record FindByStatusParam(OutboxEventStatus status) {

  }

  public record FindAllByStatusAndRetryCountAndRetryAtBeforeWithLockParam(
      OutboxEventStatus status, int maxRetryCount, LocalDateTime retryAtBefore) {

  }

}
