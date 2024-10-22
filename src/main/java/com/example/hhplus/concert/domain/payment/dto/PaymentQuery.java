package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.payment.PaymentErrorCode;

public class PaymentQuery {

  public record GetPaymentByIdQuery(Long paymentId) {

    public GetPaymentByIdQuery {
      if (paymentId == null) {
        throw new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND);
      }
    }
  }
}
