package com.example.hhplus.concert.interfaces.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  private String message = null;

  @KafkaListener(topics = "test-topic", groupId = "test-group")
  public void consume(String message) {
    log.info("Consumed message: {}", message);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

}