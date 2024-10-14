package com.example.hhplus.concert.domain.queuetoken.model;

import java.time.LocalDateTime;

public record Queuetoken(
    Long id, Long userId, QueueTokenStatus status, LocalDateTime expiredAt
) {

}
