package com.example.hhplus.concert.interfaces.consumer;

import com.example.hhplus.concert.domain.support.EventType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  @Getter
  private static final List<String> messages = new ArrayList<>();

  @KafkaListener(topics = EventType.TEST_TOPIC, groupId = "test-group")
  public void consume(String message) {
    log.info("Consumed message: {}", message);

    messages.add(message);
  }

}