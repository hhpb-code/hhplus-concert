package com.example.hhplus.concert.interfaces.scheduler;

import com.example.hhplus.concert.application.ConcertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ConcertScheduler {

  private final ConcertFacade concertFacade;


  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void expireReservations() {
    concertFacade.expireReservations();
  }
}
