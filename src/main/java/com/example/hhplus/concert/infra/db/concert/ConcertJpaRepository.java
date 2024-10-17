package com.example.hhplus.concert.infra.db.concert;

import com.example.hhplus.concert.domain.concert.model.Concert;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT c FROM Concert c WHERE c.id = :id")
  Optional<Concert> findByIdWithLock(Long id);
}
