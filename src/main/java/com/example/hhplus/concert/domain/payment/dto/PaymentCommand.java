package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class PaymentCommand {

  public record CreatePaymentCommand(Long reservationId, Long userId, Integer amount) {

    public CreatePaymentCommand {
      if (reservationId == null) {
        throw new CoreException(ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL);
      }
      if (userId == null) {
        throw new CoreException(ErrorType.User.USER_ID_MUST_NOT_BE_NULL);
      }
      if (amount == null) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }
}
