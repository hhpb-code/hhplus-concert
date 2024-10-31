package com.example.hhplus.concert.infra.redis;

import com.example.hhplus.concert.domain.support.LockManager;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RedissonLockManager implements LockManager {

  private static final long WAIT_TIME = 5L;
  private static final long LEASE_TIME = 3L;
  private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
  private final RedissonClient redissonClient;

  @Transactional(propagation = Propagation.NEVER)
  @Override
  public Object lock(String lockName, Supplier<Object> operation) throws Throwable {
    RLock rLock = redissonClient.getLock(lockName);

    try {
      boolean available = rLock.tryLock(WAIT_TIME, LEASE_TIME, TIME_UNIT);

      if (!available) {
        throw new CoreException(ErrorType.FAILED_TO_ACQUIRE_LOCK);
      }

      return operation.get();
    } catch (InterruptedException e) {
      throw new InterruptedException();
    } finally {
      rLock.unlock();
    }
  }
}
