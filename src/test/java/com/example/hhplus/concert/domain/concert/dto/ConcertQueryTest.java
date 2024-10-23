package com.example.hhplus.concert.domain.concert.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSchedulesQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.FindReservableConcertSeatsQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetReservationByIdWithLockQuery;
import com.example.hhplus.concert.domain.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("콘서트 Query 단위 테스트")
class ConcertQueryTest {

  @Nested
  @DisplayName("콘서트 조회 Query By Get")
  class GetConcertQueryTest {

    @Nested
    @DisplayName("콘서트 조회 Query By Id")
    class GetConcertByIdQueryTest {

      @Test
      @DisplayName("콘서트 조회 query 생성 실패 - 콘서트 ID가 null인 경우")
      void shouldThrowExceptionWhenConcertIdIsNull() {
        // given
        final Long concertId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetConcertByIdQuery(concertId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("콘서트 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetConcertByIdQuery() {
        // given
        final Long concertId = 1L;

        // when
        final GetConcertByIdQuery query = new GetConcertByIdQuery(concertId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.id()).isEqualTo(concertId);
      }
    }

    @Nested
    @DisplayName("콘서트 조회 Query By Id With Lock")
    class GetConcertByIdWithLockQueryTest {

      @Test
      @DisplayName("콘서트 조회 query 생성 실패 - 콘서트 ID가 null인 경우")
      void shouldThrowExceptionWhenConcertIdIsNull() {
        // given
        final Long concertId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetConcertByIdWithLockQuery(concertId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("콘서트 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetConcertByIdWithLockQuery() {
        // given
        final Long concertId = 1L;

        // when
        final GetConcertByIdWithLockQuery query = new GetConcertByIdWithLockQuery(concertId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.id()).isEqualTo(concertId);
      }
    }
  }

  @Nested
  @DisplayName("콘서트 스케줄 조회 Query By Get")
  class GetConcertScheduleByIdQueryTest {

    @Test
    @DisplayName("콘서트 스케줄 조회 query 생성 실패 - 콘서트 스케줄 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertScheduleIdIsNull() {
      // given
      final Long concertScheduleId = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new GetConcertScheduleByIdQuery(concertScheduleId));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("콘서트 스케줄 조회 query 생성 성공")
    void shouldSuccessfullyCreateGetConcertScheduleByIdQuery() {
      // given
      final Long concertScheduleId = 1L;

      // when
      final GetConcertScheduleByIdQuery query = new GetConcertScheduleByIdQuery(concertScheduleId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.concertScheduleId()).isEqualTo(concertScheduleId);
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

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new FindReservableConcertSchedulesQuery(concertId));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("예약 가능한 콘서트 스케줄 조회 query 생성 성공")
    void shouldSuccessfullyCreateFindAvailableConcertSchedulesQuery() {
      // given
      final Long concertId = 1L;

      // when
      final FindReservableConcertSchedulesQuery query = new FindReservableConcertSchedulesQuery(
          concertId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.concertId()).isEqualTo(concertId);
    }
  }

  @Nested
  @DisplayName("콘서트 좌석 조회 Query By Get")
  class GetConcertSeatQueryTest {

    @Nested
    @DisplayName("콘서트 좌석 조회 Query By Id")
    class GetConcertSeatByIdQueryTest {

      @Test
      @DisplayName("콘서트 좌석 조회 query 생성 실패 - 콘서트 좌석 ID가 null인 경우")
      void shouldThrowExceptionWhenConcertSeatIdIsNull() {
        // given
        final Long concertSeatId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetConcertSeatByIdQuery(concertSeatId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("콘서트 좌석 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetConcertSeatByIdQuery() {
        // given
        final Long concertSeatId = 1L;

        // when
        final GetConcertSeatByIdQuery query = new GetConcertSeatByIdQuery(concertSeatId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.concertSeatId()).isEqualTo(concertSeatId);
      }
    }

    @Nested
    @DisplayName("콘서트 좌석 조회 Query By Id with Lock")
    class GetConcertSeatByIdWithLockQueryTest {

      @Test
      @DisplayName("콘서트 좌석 조회 query 생성 실패 - 콘서트 좌석 ID가 null인 경우")
      void shouldThrowExceptionWhenConcertSeatIdIsNull() {
        // given
        final Long concertSeatId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetConcertSeatByIdWithLockQuery(concertSeatId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.CONCERT_SEAT_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("콘서트 좌석 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetConcertSeatByIdWithLockQuery() {
        // given
        final Long concertSeatId = 1L;

        // when
        final GetConcertSeatByIdWithLockQuery query = new GetConcertSeatByIdWithLockQuery(
            concertSeatId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.concertSeatId()).isEqualTo(concertSeatId);
      }
    }
  }

  @Nested
  @DisplayName("예약 가능한 콘서트 좌석 조회 Query By Find")
  class FindReservableConcertSeatsQueryTest {

    @Test
    @DisplayName("예약 가능한 콘서트 좌석 조회 query 생성 실패 - 콘서트 스케줄 ID가 null인 경우")
    void shouldThrowExceptionWhenConcertScheduleIdIsNull() {
      // given
      final Long concertScheduleId = null;

      // when
      final Exception exception = assertThrows(Exception.class,
          () -> new FindReservableConcertSeatsQuery(concertScheduleId));

      // then
      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.Concert.CONCERT_SCHEDULE_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("예약 가능한 콘서트 좌석 조회 query 생성 성공")
    void shouldSuccessfullyCreateFindReservableConcertSeatsQuery() {
      // given
      final Long concertScheduleId = 1L;

      // when
      final FindReservableConcertSeatsQuery query = new FindReservableConcertSeatsQuery(
          concertScheduleId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.concertScheduleId()).isEqualTo(concertScheduleId);
    }
  }

  @Nested
  @DisplayName("예약 내역 조회 Query By Get")
  class GetReservationQueryTest {

    @Nested
    @DisplayName("예약 내역 조회 Query By Id")
    class GetReservationByIdQueryTest {

      @Test
      @DisplayName("예약 내역 조회 query 생성 실패 - 예약 ID가 null인 경우")
      void shouldThrowExceptionWhenReservationIdIsNull() {
        // given
        final Long reservationId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetReservationByIdQuery(reservationId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("예약 내역 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetReservationByIdQuery() {
        // given
        final Long reservationId = 1L;

        // when
        final GetReservationByIdQuery query = new GetReservationByIdQuery(reservationId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.reservationId()).isEqualTo(reservationId);
      }
    }

    @Nested
    @DisplayName("예약 내역 조회 Query By Id With Lock")
    class GetReservationByIdWithLockQueryTest {

      @Test
      @DisplayName("예약 내역 조회 query 생성 실패 - 예약 ID가 null인 경우")
      void shouldThrowExceptionWhenReservationIdIsNull() {
        // given
        final Long reservationId = null;

        // when
        final Exception exception = assertThrows(Exception.class,
            () -> new GetReservationByIdWithLockQuery(reservationId));

        // then
        assertThat(exception.getMessage()).isEqualTo(
            ErrorType.Concert.RESERVATION_ID_MUST_NOT_BE_NULL.getMessage());
      }

      @Test
      @DisplayName("예약 내역 조회 query 생성 성공")
      void shouldSuccessfullyCreateGetReservationByIdWithLockQuery() {
        // given
        final Long reservationId = 1L;

        // when
        final GetReservationByIdWithLockQuery query = new GetReservationByIdWithLockQuery(
            reservationId);

        // then
        assertThat(query).isNotNull();
        assertThat(query.reservationId()).isEqualTo(reservationId);
      }
    }
  }
}
