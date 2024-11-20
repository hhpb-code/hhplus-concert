package com.example.hhplus.concert.interfaces.consumer;

import com.example.hhplus.concert.domain.payment.event.EventTopic;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  @Getter
  private String message = null;

  @KafkaListener(topics = EventTopic.TEST_TOPIC, groupId = "test-group")
  public void consume(String message) {
    log.info("Consumed message: {}", message);
    this.message = message;
  }

}