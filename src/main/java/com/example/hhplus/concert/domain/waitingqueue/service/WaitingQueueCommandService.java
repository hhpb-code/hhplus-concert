package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WaitingQueueCommandService {

  private final WaitingQueueRepository waitingQueueRepository;

  @Transactional
  public Long createWaitingQueue(CreateWaitingQueueCommand command) {
    return waitingQueueRepository.save(
        WaitingQueue.builder()
            .uuid(UUID.randomUUID().toString())
            .concertId(command.concertId())
            .status(WaitingQueueStatus.WAITING)
            .build()
    ).getId();
  }

  @Transactional
  public void activateWaitingQueues(ActivateWaitingQueuesCommand command) {
    List<WaitingQueue> waitingQueues = waitingQueueRepository.findAllWaitingQueues(
        new FindAllWaitingQueuesByConcertIdAndStatusWithLimitAndLockParam(
            command.concertId(), WaitingQueueStatus.WAITING,
            command.availableSlots()));

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expiredAt = now.plusMinutes(WaitingQueueConstants.WAITING_QUEUE_EXPIRE_MINUTES);

    waitingQueues.forEach(waitingQueue -> waitingQueue.activate(expiredAt));

    waitingQueueRepository.saveAll(waitingQueues);
  }

  @Transactional
  public void expireWaitingQueues() {
    waitingQueueRepository.expireWaitingQueues();
  }
}
