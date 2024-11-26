package com.example.hhplus.concert.kafka;

import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.domain.support.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TestEvent implements Event {

  private final String payload;

  @Override
  public String getType() {
    return EventType.TEST_TOPIC;
  }

  @Override
  public String getPayload() {
    return payload;
  }

}