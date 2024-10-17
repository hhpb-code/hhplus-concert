package com.example.hhplus.concert.infra.db.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueue, Long> {

}
