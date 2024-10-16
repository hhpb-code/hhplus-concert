package com.example.hhplus.concert.domain.payment;

import com.example.hhplus.concert.domain.payment.dto.PaymentParam.GetPaymentByIdParam;
import com.example.hhplus.concert.domain.payment.model.Payment;

public interface PaymentRepository {

  Payment save(Payment payment);

  Payment getPayment(GetPaymentByIdParam param);
  
}
