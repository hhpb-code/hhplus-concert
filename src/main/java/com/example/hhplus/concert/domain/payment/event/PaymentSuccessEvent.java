package com.example.hhplus.concert.domain.payment.event;

import com.example.hhplus.concert.domain.support.Event;
import com.example.hhplus.concert.domain.support.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PaymentSuccessEvent implements Event {

  private final Long paymentId;

  @Override
  public String getType() {
    return EventType.PAYMENT_SUCCESS;
  }

  @Override
  public String getPayload() {
    return paymentId.toString();
  }

}