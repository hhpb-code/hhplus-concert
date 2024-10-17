package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.Reservation;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT r FROM Reservation r WHERE r.id = :reservationId")
  Optional<Reservation> findByIdWithLock(Long reservationId);
}
