package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {

  @Query("select cs from ConcertSchedule cs where cs.concertId = :concertId and cs.reservationStartAt <= :now and cs.reservationEndAt >= :now")
  List<ConcertSchedule> findByConcertIdAndReservationPeriod(Long concertId, LocalDateTime now);

  List<ConcertSchedule> findByReservationStartAtBetween(LocalDateTime startAt, LocalDateTime endAt);
}
