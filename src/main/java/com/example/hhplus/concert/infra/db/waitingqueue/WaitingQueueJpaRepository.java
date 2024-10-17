package com.example.hhplus.concert.infra.db.waitingqueue;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueue, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT wq FROM WaitingQueue wq WHERE wq.uuid = :uuid")
  Optional<WaitingQueue> findByUuid(String uuid);

  @Query("SELECT COUNT(wq) FROM WaitingQueue wq WHERE wq.concertId = :concertId AND wq.status = :status AND wq.id < :id")
  Integer countByStatusAndConcertIdAndIdLessThan(WaitingQueueStatus status, Long concertId,
      Long id);

  @Query("SELECT DISTINCT wq.concertId FROM WaitingQueue wq WHERE wq.status = :status")
  List<Long> findAllDistinctConcertIdsByStatus(WaitingQueueStatus status);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT wq FROM WaitingQueue wq WHERE wq.concertId = :concertId AND wq.status = :status ORDER BY wq.id LIMIT :limit")
  List<WaitingQueue> findAllByConcertIdAndStatusWithLimitAndLock(Long concertId,
      WaitingQueueStatus status, int limit);

  Integer countByConcertIdAndStatus(Long concertId, WaitingQueueStatus status);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE WaitingQueue wq SET wq.status = 'EXPIRED' WHERE wq.status = 'PROCESSING' AND wq.expiredAt <= CURRENT_TIMESTAMP")
  void expireWaitingQueues();
}
