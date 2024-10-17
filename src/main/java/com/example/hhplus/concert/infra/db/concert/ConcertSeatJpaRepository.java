package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

  @Query("select cs from ConcertSeat cs where cs.concertScheduleId = :scheduleId and cs.isReserved = false")
  List<ConcertSeat> findReservableConcertSeatsByConcertScheduleId(Long scheduleId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select cs from ConcertSeat cs where cs.id = :concertSeatId")
  Optional<ConcertSeat> findByIdWithLock(Long concertSeatId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select cs from ConcertSeat cs where cs.id in :ids")
  List<ConcertSeat> findAllByIdsWithLock(List<Long> ids);
}
