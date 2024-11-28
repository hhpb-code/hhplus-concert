package com.example.hhplus.concert.interfaces.scheduler;

import com.example.hhplus.concert.application.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {

  private final ConcertFacade concertFacade;


  @Scheduled(cron = "0 * * * * *")
  public void expireReservations() {
    concertFacade.expireReservations();
  }

  // 매분 예약임박 3분전 콘서트 정보 및 스케쥴 조회(캐싱)
  @Scheduled(cron = "0 * * * * *")
  // 예약 임박 3분전
  public void getUpcomingConcertsAndSchedules() {
    concertFacade.getUpcomingConcertsAndSchedulesWithCache();
  }

}
