package com.example.hhplus.concert.domain.waitingqueue.service;

import com.example.hhplus.concert.domain.waitingqueue.WaitingQueueRepository;
import com.example.hhplus.concert.domain.waitingqueue.dto.WaitingQueueCommand.CreateWaitingQueueCommand;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueue;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
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

}
