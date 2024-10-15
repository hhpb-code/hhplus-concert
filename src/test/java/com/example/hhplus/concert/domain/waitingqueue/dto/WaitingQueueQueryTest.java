package com.example.hhplus.concert.domain.waitingqueue.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("대기열 query 단위 테스트")
class WaitingQueueQueryTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("대기열 조회 query By get")
  class GetWaitingQueueQueryTest {

    @Nested
    @DisplayName("대기열 조회 query By id")
    class GetWaitingQueueByIdQueryTest {

      @Test
      @DisplayName("대기열 조회 query 생성 실패 - id가 null")
      void shouldThrowExceptionWhenIdIsNull() {
        // given
        final Long id = null;
        final GetWaitingQueueByIdQuery query = new GetWaitingQueueByIdQuery(id);

        // when
        final Set<ConstraintViolation<GetWaitingQueueByIdQuery>> violations = validator.validate(
            query);

        final ConstraintViolation<GetWaitingQueueByIdQuery> violation = violations.stream()
            .filter(v -> v.getPropertyPath().toString().equals("id"))
            .findFirst()
            .get();

        // then
        assertThat(violation.getMessage()).isEqualTo(
            WaitingQueueConstants.WAITING_QUEUE_ID_NULL_MESSAGE);
      }

      @Test
      @DisplayName("대기열 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetWaitingQueueByIdQuery() {
        // given
        final Long id = 1L;
        final GetWaitingQueueByIdQuery query = new GetWaitingQueueByIdQuery(id);

        // when
        final Set<ConstraintViolation<GetWaitingQueueByIdQuery>> violations = validator.validate(
            query);

        // then
        assertThat(violations).isEmpty();
      }
    }
  }

  @Nested
  @DisplayName("대기열 순서 조회 query")
  class GetWaitingQueuePositionByUuidQueryTest {

    @Test
    @DisplayName("대기열 순서 조회 query 생성 실패 - uuid가 null")
    void shouldThrowExceptionWhenUuidIsNull() {
      // given
      final String uuid = null;
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);

      // when
      final Set<ConstraintViolation<GetWaitingQueuePositionByUuid>> violations = validator
          .validate(query);

      final ConstraintViolation<GetWaitingQueuePositionByUuid> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("uuid"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          WaitingQueueConstants.WAITING_QUEUE_UUID_EMPTY_MESSAGE);
    }

    @Test
    @DisplayName("대기열 순서 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetWaitingQueuePositionByUuidQuery() {
      // given
      final String uuid = "uuid";
      final GetWaitingQueuePositionByUuid query = new GetWaitingQueuePositionByUuid(uuid);

      // when
      final Set<ConstraintViolation<GetWaitingQueuePositionByUuid>> violations = validator
          .validate(query);

      // then
      assertThat(violations).isEmpty();
    }
  }

}