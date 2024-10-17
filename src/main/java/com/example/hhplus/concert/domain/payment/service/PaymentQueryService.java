package com.example.hhplus.concert.domain.payment.service;

import com.example.hhplus.concert.domain.payment.PaymentRepository;
import com.example.hhplus.concert.domain.payment.dto.PaymentParam.GetPaymentByIdParam;
import com.example.hhplus.concert.domain.payment.dto.PaymentQuery.GetPaymentByIdQuery;
import com.example.hhplus.concert.domain.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentQueryService {

  private final PaymentRepository paymentRepository;


  public Payment getPayment(GetPaymentByIdQuery query) {
    return paymentRepository.getPayment(new GetPaymentByIdParam(query.paymentId()));
  }

}
