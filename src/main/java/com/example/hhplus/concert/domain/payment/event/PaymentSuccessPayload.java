package com.example.hhplus.concert.domain.payment.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentSuccessPayload {

  private final Long paymentId;

  public PaymentSuccessPayload(PaymentSuccessEvent event) {
    this.paymentId = event.getPaymentId();
  }

}
