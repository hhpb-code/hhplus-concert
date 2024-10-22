package com.example.hhplus.concert.domain.payment.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.payment.PaymentErrorCode;

public class PaymentCommand {

  public record CreatePaymentCommand(Long reservationId, Long userId, Integer amount) {

    public CreatePaymentCommand {
      if (reservationId == null) {
        throw new BusinessException(PaymentErrorCode.RESERVATION_ID_NULL);
      }
      if (userId == null) {
        throw new BusinessException(PaymentErrorCode.USER_ID_NULL);
      }
      if (amount == null) {
        throw new BusinessException(PaymentErrorCode.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new BusinessException(PaymentErrorCode.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }
}
