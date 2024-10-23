package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class PaymentQuery {

  public record GetPaymentByIdQuery(Long paymentId) {

    public GetPaymentByIdQuery {
      if (paymentId == null) {
        throw new CoreException(ErrorType.Payment.PAYMENT_ID_MUST_NOT_BE_NULL);
      }
    }
  }
}
