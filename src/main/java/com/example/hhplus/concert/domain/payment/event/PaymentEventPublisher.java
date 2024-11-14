package com.example.hhplus.concert.domain.payment.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public PaymentEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publish(PaymentSuccessEvent event) {
    applicationEventPublisher.publishEvent(event);
  }

}
