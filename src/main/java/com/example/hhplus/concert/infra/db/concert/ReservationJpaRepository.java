package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.Reservation;
import com.example.hhplus.concert.domain.concert.model.ReservationStatus;
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

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.reservedAt < :expiredAt")
  List<Reservation> findAllByStatusAndReservedAtBefore(ReservationStatus status,
      LocalDateTime expiredAt);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT r FROM Reservation r WHERE r.id IN :ids")
  List<Reservation> findAllByIdsWithLock(List<Long> ids);
}
