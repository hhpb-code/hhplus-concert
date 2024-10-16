package com.example.hhplus.concert.domain.payment.service;

import com.example.hhplus.concert.domain.payment.PaymentRepository;
import com.example.hhplus.concert.domain.payment.dto.PaymentCommand.CreatePaymentCommand;
import com.example.hhplus.concert.domain.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCommandService {

  private final PaymentRepository paymentRepository;

  public Long createPayment(CreatePaymentCommand command) {
    return paymentRepository.save(Payment.builder()
        .amount(command.amount())
        .reservationId(command.reservationId())
        .userId(command.userId())
        .build()).getId();
  }
}
