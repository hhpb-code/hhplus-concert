package com.example.hhplus.concert.domain.support.annotation;

import com.example.hhplus.concert.domain.support.DistributedLockType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

  DistributedLockType type();

  String[] keys();

}
