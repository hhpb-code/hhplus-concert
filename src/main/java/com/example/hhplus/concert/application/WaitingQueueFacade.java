package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertByIdWithLockQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertScheduleByIdQuery;
import com.example.hhplus.concert.domain.concert.dto.ConcertQuery.GetConcertSeatByIdQuery;
import com.example.hhplus.concert.domain.concert.model.ConcertSchedule;
import com.example.hhplus.concert.domain.concert.model.ConcertSeat;
import com.example.hhplus.concert.domain.concert.service.ConcertQueryService;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.CountWaitingQueueByConcertIdAndStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.FindDistinctConcertIdsByStatusQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByIdQuery;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueueByUuid;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueQuery.GetWaitingQueuePositionByUuid;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueWithPosition;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueCommandService;
import com.example.hhplus.concert.domain.waitingqueue.service.WaitingQueueQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WaitingQueueFacade {

  private final WaitingQueueQueryService waitingQueueQueryService;

  private final WaitingQueueCommandService waitingQueueCommandService;

  private final ConcertQueryService concertQueryService;


  @Transactional
  public WaitingQueue createWaitingQueueToken(Long concertId) {
    Long waitingQueueId = waitingQueueCommandService.createWaitingQueue(
        new CreateWaitingQueueCommand(concertId));

    return waitingQueueQueryService.getWaitingQueue(new GetWaitingQueueByIdQuery(waitingQueueId));
  }

  public WaitingQueueWithPosition getWaitingQueueWithPosition(String waitingQueueUuid) {
    return waitingQueueQueryService.getWaitingQueuePosition(
        new GetWaitingQueuePositionByUuid(waitingQueueUuid));
  }

  public void validateWaitingQueueProcessingAndConcertId(String waitingQueueUuid, Long concertId) {
    WaitingQueue waitingQueue = waitingQueueQueryService.getWaitingQueue(
        new GetWaitingQueueByUuid(waitingQueueUuid));

    waitingQueue.validateProcessing();
    waitingQueue.validateConcertId(concertId);
  }

  public void validateWaitingQueueProcessingAndScheduleId(String waitingQueueUuid,
      Long scheduleId) {
    WaitingQueue waitingQueue = waitingQueueQueryService.getWaitingQueue(
        new GetWaitingQueueByUuid(waitingQueueUuid));
    waitingQueue.validateProcessing();

    ConcertSchedule concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(scheduleId));
    waitingQueue.validateConcertId(concertSchedule.getConcertId());
  }

  public void validateWaitingQueueProcessingAndSeatId(String waitingQueueUuid, Long seatId) {
    WaitingQueue waitingQueue = waitingQueueQueryService.getWaitingQueue(
        new GetWaitingQueueByUuid(waitingQueueUuid));
    waitingQueue.validateProcessing();

    ConcertSeat concertSeat = concertQueryService.getConcertSeat(
        new GetConcertSeatByIdQuery(seatId));
    ConcertSchedule concertSchedule = concertQueryService.getConcertSchedule(
        new GetConcertScheduleByIdQuery(concertSeat.getConcertScheduleId()));
    waitingQueue.validateConcertId(concertSchedule.getConcertId());
  }

  @Transactional
  public void activateWaitingQueues() {
    final List<Long> concertIds = waitingQueueQueryService.findDistinctConcertIds(
        new FindDistinctConcertIdsByStatusQuery(WaitingQueueStatus.WAITING));

    for (Long concertId : concertIds) {
      concertQueryService.getConcert(new GetConcertByIdWithLockQuery(concertId));

      Integer processingWaitingQueueCount = waitingQueueQueryService.countWaitingQueueByConcertIdAndStatus(
          new CountWaitingQueueByConcertIdAndStatusQuery(concertId, WaitingQueueStatus.PROCESSING)
      );
      Integer availableWaitingQueueCount =
          WaitingQueueConstants.MAX_PROCESSING_WAITING_QUEUE_COUNT - processingWaitingQueueCount;

      if (availableWaitingQueueCount <= 0) {
        continue;
      }

      waitingQueueCommandService.activateWaitingQueues(
          new ActivateWaitingQueuesCommand(concertId, availableWaitingQueueCount));
    }
  }

  @Transactional
  public void expireWaitingQueues() {
    waitingQueueCommandService.expireWaitingQueues();
  }
}
