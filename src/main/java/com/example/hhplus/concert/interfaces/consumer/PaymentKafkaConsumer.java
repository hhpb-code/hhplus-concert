package com.example.hhplus.concert.interfaces.consumer;

import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import com.example.hhplus.concert.domain.send.DataPlatformSendService;
import com.example.hhplus.concert.domain.support.EventType;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

  @Getter
  private static final List<String> messages = new ArrayList<>();
  private final DataPlatformSendService sendService;

  @KafkaListener(topics = EventType.PAYMENT_SUCCESS, groupId = "payment-group")
  public void consume(Long paymentId) {
    log.info("Consumed message: {}", paymentId);
    sendService.send(new PaymentSuccessEvent(paymentId));
    messages.add(paymentId.toString());
  }

}