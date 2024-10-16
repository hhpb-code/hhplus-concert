package com.example.hhplus.concert.domain.concert.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.concert.ConcertConstants;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("콘서트 Query 단위 테스트")
class ConcertQueryTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Nested
  @DisplayName("콘서트 조회 Query By Get")
  class GetConcertByIdQueryTest {

    @Test
    @DisplayName("콘서트 조회 query 생성 실패 - 콘서트 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final GetConcertByIdQuery query = new GetConcertByIdQuery(concertId);

      // when
      final Set<ConstraintViolation<GetConcertByIdQuery>> violations = validator.validate(query);

      final ConstraintViolation<GetConcertByIdQuery> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("id"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.CONCERT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetConcertByIdQuery() {
      // given
      final Long concertId = 1L;
      final GetConcertByIdQuery query = new GetConcertByIdQuery(concertId);

      // when
      final Set<ConstraintViolation<GetConcertByIdQuery>> violations = validator.validate(query);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("예약 가능한 콘서트 스케줄 조회 Query By Find")
  class FindReservableConcertSchedulesQueryTest {

    @Test
    @DisplayName("예약 가능한 콘서트 스케줄 조회 query 생성 실패 - 콘서트 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertIdIsNull() {
      // given
      final Long concertId = null;
      final FindReservableConcertSchedulesQuery query = new FindReservableConcertSchedulesQuery(
          concertId);

      // when
      final Set<ConstraintViolation<FindReservableConcertSchedulesQuery>> violations = validator.validate(
          query);

      final ConstraintViolation<FindReservableConcertSchedulesQuery> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(ConcertConstants.CONCERT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 가능한 콘서트 스케줄 조회 query 생성 성공")
    void shouldSuccessfullyCreateFindAvailableConcertSchedulesQuery() {
      // given
      final Long concertId = 1L;
      final FindReservableConcertSchedulesQuery query = new FindReservableConcertSchedulesQuery(
          concertId);

      // when
      final Set<ConstraintViolation<FindReservableConcertSchedulesQuery>> violations = validator.validate(
          query);

      // then
      assertThat(violations).isEmpty();
    }

  }


  @Nested
  @DisplayName("콘서트 좌석 조회 Query By Get")
  class GetConcertSeatByIdWithLockQueryTest {

    @Test
    @DisplayName("콘서트 좌석 조회 query 생성 실패 - 콘서트 좌석 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertSeatIdIsNull() {
      // given
      final Long concertSeatId = null;
      final GetConcertSeatByIdWithLockQuery query = new GetConcertSeatByIdWithLockQuery(
          concertSeatId);

      // when
      final Set<ConstraintViolation<GetConcertSeatByIdWithLockQuery>> violations = validator.validate(
          query);

      final ConstraintViolation<GetConcertSeatByIdWithLockQuery> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("concertSeatId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.CONCERT_SEAT_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("콘서트 좌석 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetConcertSeatByIdWithLockQuery() {
      // given
      final Long concertSeatId = 1L;
      final GetConcertSeatByIdWithLockQuery query = new GetConcertSeatByIdWithLockQuery(
          concertSeatId);

      // when
      final Set<ConstraintViolation<GetConcertSeatByIdWithLockQuery>> violations = validator.validate(
          query);

      // then
      assertThat(violations).isEmpty();
    }

  }

  @Nested
  @DisplayName("예약 내역 조회 Query By Get")
  class GetReservationByIdQueryTest {

    @Test
    @DisplayName("예약 내역 조회 query 생성 실패 - 예약 ID가 null인 경우")
    void shouldThrowExceptionWhenReservationIdIsNull() {
      // given
      final Long reservationId = null;
      final GetReservationByIdQuery query = new GetReservationByIdQuery(reservationId);

      // when
      final Set<ConstraintViolation<GetReservationByIdQuery>> violations = validator.validate(
          query);

      final ConstraintViolation<GetReservationByIdQuery> violation = violations.stream()
          .filter(v -> v.getPropertyPath().toString().equals("reservationId"))
          .findFirst()
          .get();

      // then
      assertThat(violation.getMessage()).isEqualTo(
          ConcertConstants.RESERVATION_ID_MUST_NOT_BE_NULL);
    }

    @Test
    @DisplayName("예약 내역 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetReservationByIdQuery() {
      // given
      final Long reservationId = 1L;
      final GetReservationByIdQuery query = new GetReservationByIdQuery(reservationId);

      // when
      final Set<ConstraintViolation<GetReservationByIdQuery>> violations = validator.validate(
          query);

      // then
      assertThat(violations).isEmpty();
    }

  }

}