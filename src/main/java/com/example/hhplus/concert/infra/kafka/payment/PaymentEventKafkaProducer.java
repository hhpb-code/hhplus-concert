package com.example.hhplus.concert.infra.kafka.payment;

import com.example.hhplus.concert.domain.payment.event.EventTopic;
import com.example.hhplus.concert.domain.payment.event.PaymentEventPublisher;
import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventKafkaProducer implements PaymentEventPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(PaymentSuccessEvent event) {
    String paymentId = event.getPaymentId().toString();

    kafkaTemplate.send(EventTopic.PAYMENT_SUCCESS, paymentId, paymentId);
  }

}
