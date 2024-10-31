package com.example.hhplus.concert.domain.support;

import java.util.function.Supplier;

public interface LockManager {

  Object lock(String lockName, Supplier<Object> operation) throws Throwable;

}
