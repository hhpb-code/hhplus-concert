package com.example.hhplus.concert.domain.payment.event;

public interface PaymentEventPublisher {

  void publish(PaymentSuccessEvent event);

}
