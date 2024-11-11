package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueConstants;
import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.ActivateWaitingQueuesCommand;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueRepositoryParam.ActivateWaitingQueuesParam;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class WaitingQueueCommandService {

  private final WaitingQueueRepository waitingQueueRepository;

  public String createWaitingQueue() {
    return waitingQueueRepository.addWaitingQueue(UUID.randomUUID().toString());
  }

  public void activateWaitingQueues(ActivateWaitingQueuesCommand command) {
    waitingQueueRepository.activateWaitingQueues(
        new ActivateWaitingQueuesParam(command.availableSlots(),
            WaitingQueueConstants.WAITING_QUEUE_EXPIRE_MINUTES, TimeUnit.MINUTES));
  }
}
