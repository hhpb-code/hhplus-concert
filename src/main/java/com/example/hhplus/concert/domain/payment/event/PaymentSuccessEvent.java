package com.example.hhplus.concert.domain.payment.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PaymentSuccessEvent {

  private final Long paymentId;

}