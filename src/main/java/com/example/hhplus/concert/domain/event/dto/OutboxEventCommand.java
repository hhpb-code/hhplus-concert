package com.example.hhplus.concert.domain.event.dto;

import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public record OutboxEventCommand() {

  public record CreateOutboxEventCommand(Event event) {

    public CreateOutboxEventCommand {
      if (event == null) {
        throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_MUST_NOT_BE_NULL);
      }
    }

  }

  public record PublishOutboxEventCommand(Long id) {

    public PublishOutboxEventCommand {
      if (id == null) {
        throw new CoreException(ErrorType.OutboxEvent.OUTBOX_EVENT_ID_MUST_NOT_BE_NULL);
      }
    }

  }

}
