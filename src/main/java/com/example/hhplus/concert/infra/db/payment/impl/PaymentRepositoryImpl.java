package com.example.hhplus.concert.infra.db.payment.impl;

import com.example.hhplus.concert.domain.payment.PaymentRepository;
import com.example.hhplus.concert.domain.payment.dto.PaymentParam.GetPaymentByIdParam;
import com.example.hhplus.concert.domain.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

  @Override
  public Payment save(Payment payment) {
    return null;
  }

  @Override
  public Payment getPayment(GetPaymentByIdParam param) {
    return null;
  }
}
