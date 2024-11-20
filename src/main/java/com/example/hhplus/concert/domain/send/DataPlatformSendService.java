package com.example.hhplus.concert.domain.send;

import com.example.hhplus.concert.domain.payment.event.PaymentSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataPlatformSendService {

  public void send(PaymentSuccessEvent event) {
    log.info("Send to data platform: {}", event);
  }

}
