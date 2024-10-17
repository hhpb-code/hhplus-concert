package com.example.hhplus.concert.infra.db.user;

import com.example.hhplus.concert.domain.user.model.Wallet;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT w FROM Wallet w WHERE w.userId = :userId")
  Optional<Wallet> findByUserIdWithLock(Long userId);
}
