package com.example.hhplus.concert.domain.send;

import com.example.hhplus.concert.domain.payment.event.PaymentSuccessPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataPlatformSendService {

  public void send(PaymentSuccessPayload payload) {
    log.info("Send to data platform: {}", payload);

    throw new RuntimeException("Data platform is not available");
  }

}
