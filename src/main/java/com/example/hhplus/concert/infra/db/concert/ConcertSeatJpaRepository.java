package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat, Long> {

  @Query("select cs from ConcertSeat cs where cs.concertScheduleId = :scheduleId and cs.isReserved = false")
  List<ConcertSeat> findReservableConcertSeatsByConcertScheduleId(Long scheduleId);
}
