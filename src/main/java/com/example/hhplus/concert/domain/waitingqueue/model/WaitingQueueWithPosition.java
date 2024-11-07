package com.example.hhplus.concert.domain.waitingqueue.model;

import java.time.LocalDateTime;

public record WaitingQueueWithPosition(
    Long concertId,
    String uuid,
    WaitingQueueStatus status,
    LocalDateTime expiredAt,
    LocalDateTime createdAt,
    Integer position
) {

}
