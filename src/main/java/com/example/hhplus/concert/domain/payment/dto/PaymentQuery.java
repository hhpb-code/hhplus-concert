package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.payment.PaymentConstants;
import jakarta.validation.constraints.NotNull;

public class PaymentQuery {

  public record GetPaymentByIdQuery(
      @NotNull(message = PaymentConstants.PAYMENT_ID_NULL_MESSAGE)
      Long paymentId
  ) {

  }

}
