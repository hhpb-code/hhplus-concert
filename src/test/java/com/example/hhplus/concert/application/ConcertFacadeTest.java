package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.concert.ConcertErrorCode;
import com.example.hhplus.concert.domain.concert.model.Concert;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.infra.db.concert.ConcertJpaRepository;
import com.example.hhplus.concert.infra.db.concert.ConcertScheduleJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("ConcertFacade 통합 테스트")
class ConcertFacadeTest {

  @Autowired
  private ConcertFacade concertFacade;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;


  @BeforeEach
  void setUp() {
    concertScheduleJpaRepository.deleteAll();
    concertJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("예약 가능한 콘서트 일정 조회")
  class GetReservableConcertSchedulesTest {

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 실패 - 콘서트가 존재하지 않음")
    void shouldThrowConcertNotFoundException() {
      // given
      final Long concertId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class, () -> {
        concertFacade.getReservableConcertSchedules(concertId);
      });

      // then
      assertThat(result.getErrorCode()).isEqualTo(ConcertErrorCode.CONCERT_NOT_FOUND);
    }

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 성공 - 일정 없음")
    void shouldSuccessfullyGetReservableConcertSchedules() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      // when
      final List<ConcertSchedule> result = concertFacade.getReservableConcertSchedules(concertId);

      // then
      assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("예약 가능한 콘서트 일정 조회 성공 - 일정 있음")
    void shouldSuccessfullyGetReservableConcertSchedulesWithSchedule() {
      // given
      final Concert concert = concertJpaRepository.save(
          Concert.builder().title("title").description("description").build());
      final Long concertId = concert.getId();

      final List<ConcertSchedule> concertSchedules =
          concertScheduleJpaRepository.saveAll(
              List.of(
                  ConcertSchedule.builder().concertId(concertId)
                      .concertAt(LocalDateTime.now().plusDays(1))
                      .reservationStartAt(LocalDateTime.now())
                      .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build(),
                  ConcertSchedule.builder().concertId(concertId)
                      .concertAt(LocalDateTime.now().plusDays(2))
                      .reservationStartAt(LocalDateTime.now())
                      .reservationEndAt(LocalDateTime.now().plusMinutes(1)).build()
              ));

      // when
      final List<ConcertSchedule> result = concertFacade.getReservableConcertSchedules(concertId);

      // then
      assertThat(result).hasSize(concertSchedules.size());
      for (int i = 0; i < result.size(); i++) {
        final ConcertSchedule concertSchedule = result.get(i);
        final ConcertSchedule expected = concertSchedules.get(i);

        assertThat(concertSchedule.getId()).isNotNull();
        assertThat(concertSchedule.getConcertId()).isEqualTo(expected.getConcertId());
        assertThat(concertSchedule.getConcertAt()).isEqualTo(expected.getConcertAt());
        assertThat(concertSchedule.getReservationStartAt()).isEqualTo(
            expected.getReservationStartAt());
        assertThat(concertSchedule.getReservationEndAt()).isEqualTo(expected.getReservationEndAt());
      }
    }

  }


}