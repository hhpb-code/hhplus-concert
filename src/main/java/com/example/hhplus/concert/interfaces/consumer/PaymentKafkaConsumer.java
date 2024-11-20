package com.example.hhplus.concert.interfaces.consumer;

import com.example.hhplus.concert.domain.payment.event.EventTopic;
import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import com.example.hhplus.concert.domain.send.DataPlatformSendService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

  private final DataPlatformSendService sendService;

  @Getter
  private Long message = null;

  @KafkaListener(topics = EventTopic.PAYMENT_SUCCESS, groupId = "payment-group")
  public void consume(Long paymentId) {
    log.info("Consumed message: {}", paymentId);
    sendService.send(new PaymentSuccessEvent(paymentId));
    this.message = paymentId;
  }

}