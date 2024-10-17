package com.example.hhplus.concert.domain.payment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.payment.PaymentConstants;
import com.example.hhplus.concert.domain.payment.dto.PaymentQuery.GetPaymentByIdQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Payment Query 단위 테스트")
class PaymentQueryTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("결제 조회 Query 테스트")
  class GetPaymentByIdQueryTest {

    @Test
    @DisplayName("결제 조회 Query 생성 실패 - paymentId가 null인 경우")
    void createGetPaymentByIdQueryWithNullPaymentId() {
      // given
      final Long paymentId = null;
      final GetPaymentByIdQuery query = new GetPaymentByIdQuery(paymentId);

      // when
      final Set<ConstraintViolation<GetPaymentByIdQuery>> violations = validator.validate(
          query);

      final ConstraintViolation<GetPaymentByIdQuery> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("paymentId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(PaymentConstants.PAYMENT_ID_NULL_MESSAGE);
    }

    @Test
    @DisplayName("결제 조회 Query 생성 성공")
    void createGetPaymentByIdQuery() {
      // given
      final Long paymentId = 1L;
      final GetPaymentByIdQuery query = new GetPaymentByIdQuery(paymentId);

      // when
      final Set<ConstraintViolation<GetPaymentByIdQuery>> violations = validator.validate(
          query);

      // then
      assertThat(violations).isEmpty();
    }


  }

}