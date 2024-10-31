package com.example.hhplus.concert.domain.support.aop;

import com.example.hhplus.concert.domain.support.LockManager;
import com.example.hhplus.concert.domain.support.annotation.DistributedLock;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@RequiredArgsConstructor
public class DistributedLockAop {

  private final LockManager lockManager;

  @Around("@annotation(distributedLock)")
  public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock)
      throws Throwable {
    String dynamicKey = createDynamicKey(joinPoint, distributedLock.keys());
    String lockName = distributedLock.type().lockName() + ":" + dynamicKey;

    return lockManager.lock(lockName, () -> {
      try {
        return joinPoint.proceed();
      } catch (RuntimeException | Error e) {
        throw e;
      } catch (Throwable e) {
        throw new RuntimeException(e);
      }
    });

  }

  private String createDynamicKey(ProceedingJoinPoint joinPoint, String[] keys) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String[] methodParameterNames = methodSignature.getParameterNames();
    Object[] methodArgs = joinPoint.getArgs();

    return Arrays.stream(keys)
        .map(key -> {
          int indexOfKey = Arrays.asList(methodParameterNames).indexOf(key);
          if (indexOfKey == -1 || methodArgs[indexOfKey] == null) {
            throw new CoreException(ErrorType.KEY_NOT_FOUND_OR_NULL);
          }
          return methodArgs[indexOfKey].toString();
        })
        .collect(Collectors.joining(":"));
  }

}

