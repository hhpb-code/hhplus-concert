package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

}
