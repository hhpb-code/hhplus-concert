package com.example.hhplus.concert.domain.waitingqueue.model;

import java.time.LocalDateTime;

public record WaitingQueueWithPosition(
    Long id,
    Long concertId,
    String uuid,
    WaitingQueueStatus status,
    LocalDateTime expiredAt,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Integer position
) {

}
