package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

}
