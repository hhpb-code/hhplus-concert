package com.example.hhplus.concert.infra.db.payment.impl;

import com.example.hhplus.concert.domain.payment.PaymentRepository;
import com.example.hhplus.concert.domain.payment.dto.PaymentParam.GetPaymentByIdParam;
import com.example.hhplus.concert.domain.payment.model.Payment;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.infra.db.payment.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

  private final PaymentJpaRepository paymentJpaRepository;

  @Override
  public Payment save(Payment payment) {
    return paymentJpaRepository.save(payment);
  }

  @Override
  public Payment getPayment(GetPaymentByIdParam param) {
    return paymentJpaRepository.findById(param.id())
        .orElseThrow(() -> new CoreException(ErrorType.Payment.PAYMENT_NOT_FOUND));
  }
}
