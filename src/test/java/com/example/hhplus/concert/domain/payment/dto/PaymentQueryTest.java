package com.example.hhplus.concert.domain.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.payment.PaymentErrorCode;
import com.example.hhplus.concert.domain.payment.dto.PaymentQuery.GetPaymentByIdQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Payment Query 단위 테스트")
class PaymentQueryTest {

  @Nested
  @DisplayName("결제 조회 Query 테스트")
  class GetPaymentByIdQueryTest {

    @Test
    @DisplayName("결제 조회 Query 생성 실패 - paymentId가 null인 경우")
    void createGetPaymentByIdQueryWithNullPaymentId() {
      // given
      final Long paymentId = null;

      // when
      final BusinessException exception = assertThrows(BusinessException.class,
          () -> new GetPaymentByIdQuery(paymentId));

      // then
      assertThat(exception.getMessage()).isEqualTo(PaymentErrorCode.PAYMENT_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("결제 조회 Query 생성 성공")
    void createGetPaymentByIdQuery() {
      // given
      final Long paymentId = 1L;

      // when
      final GetPaymentByIdQuery query = new GetPaymentByIdQuery(paymentId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.paymentId()).isEqualTo(paymentId);
    }
  }
}
