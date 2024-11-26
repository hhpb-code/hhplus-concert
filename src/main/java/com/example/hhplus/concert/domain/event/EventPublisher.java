package com.example.hhplus.concert.domain.event;

public interface EventPublisher {

  void publish(String topic, String payload);

}
