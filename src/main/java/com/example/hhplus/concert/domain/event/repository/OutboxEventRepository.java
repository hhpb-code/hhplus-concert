package com.example.hhplus.concert.domain.event.repository;

import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByIdParam;
import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByIdWithLockParam;
import com.example.hhplus.concert.domain.event.dto.OutboxEventParam.GetByStatusParam;
import com.example.hhplus.concert.domain.event.model.OutboxEvent;

public interface OutboxEventRepository {

  OutboxEvent save(OutboxEvent outboxEvent);

  OutboxEvent getById(GetByIdParam param);

  OutboxEvent getById(GetByIdWithLockParam param);

  OutboxEvent findByStatus(GetByStatusParam param);
}
