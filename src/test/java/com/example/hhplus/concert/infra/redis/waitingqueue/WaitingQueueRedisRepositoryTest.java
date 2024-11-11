package com.example.hhplus.concert.infra.redis.waitingqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("WaitingQueueRedisRepository 테스트")
class WaitingQueueRedisRepositoryTest {

  @Autowired
  private WaitingQueueRedisRepository target;

  @Autowired
  private RedisTemplate redisTemplate;

  @BeforeEach
  void setUp() {
    redisTemplate.keys("*").forEach(redisTemplate::delete);
  }


}