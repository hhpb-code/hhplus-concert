package com.example.hhplus.concert.domain.event.dto;

import com.example.hhplus.concert.domain.event.model.OutboxEventStatus;

public class OutboxEventParam {

  public record GetByIdParam(Long id) {

  }

  public record GetByIdWithLockParam(Long id) {

  }

  public record GetByStatusParam(OutboxEventStatus status) {

  }

}
