package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.payment.PaymentConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentCommand {

  public record CreatePaymentCommand(
      @NotNull(message = PaymentConstants.RESERVATION_ID_NULL_MESSAGE)
      Long reservationId,

      @NotNull(message = PaymentConstants.USER_ID_NULL_MESSAGE)
      Long userId,

      @NotNull(message = PaymentConstants.AMOUNT_MUST_NOT_BE_NULL_MESSAGE)
      @Positive(message = PaymentConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE)
      Integer amount) {

  }

}
