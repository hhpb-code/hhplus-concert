package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.application.WaitingQueueFacade;
import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.controller.IWaitingQueueController;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenRequest;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.GetWaitingQueuePositionResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.WaitingQueueResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.WaitingQueueResponseWithPosition;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/waiting-queues")
@RequiredArgsConstructor
public class WaitingQueueController implements IWaitingQueueController {

  private final WaitingQueueFacade waitingQueueFacade;

  @PostMapping("/tokens")
  public ResponseEntity<CreateWaitingQueueTokenResponse> createWaitingQueueToken(
      @RequestBody CreateWaitingQueueTokenRequest request
  ) {
    WaitingQueueResponse waitingQueue = new WaitingQueueResponse(
        waitingQueueFacade.createWaitingQueueToken(request.concertId()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateWaitingQueueTokenResponse(waitingQueue));
  }

  @GetMapping("/position")
  public ResponseEntity<GetWaitingQueuePositionResponse> getWaitingQueuePosition(
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid
  ) {
    WaitingQueueResponseWithPosition waitingQueue = new WaitingQueueResponseWithPosition(1L, 1L,
        waitingQueueTokenUuid, WaitingQueueStatus.WAITING, 10, LocalDateTime.now(),
        LocalDateTime.now(), null);

    return ResponseEntity.ok(new GetWaitingQueuePositionResponse(waitingQueue));
  }

}
