package com.example.hhplus.concert.infra.kafka;

import com.example.hhplus.concert.domain.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements EventPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(String topic, String payload) {
    kafkaTemplate.send(topic, payload);
  }
  
}