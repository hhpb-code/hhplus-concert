package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.application.WaitingQueueFacade;
import com.example.hhplus.concert.interfaces.api.controller.IWaitingQueueController;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.GetWaitingQueuePositionResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.WaitingQueueResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.WaitingQueueResponseWithPosition;
import com.example.hhplus.concert.interfaces.api.support.CommonHttpHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/waiting-queues")
@RequiredArgsConstructor
public class WaitingQueueController implements IWaitingQueueController {

  private final WaitingQueueFacade waitingQueueFacade;

  @PostMapping("/tokens")
  public ResponseEntity<CreateWaitingQueueTokenResponse> createWaitingQueueToken() {
    WaitingQueueResponse waitingQueue = new WaitingQueueResponse(
        waitingQueueFacade.createWaitingQueueToken());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateWaitingQueueTokenResponse(waitingQueue));
  }

  @GetMapping("/position")
  public ResponseEntity<GetWaitingQueuePositionResponse> getWaitingQueuePosition(
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid
  ) {
    WaitingQueueResponseWithPosition waitingQueue = new WaitingQueueResponseWithPosition(
        waitingQueueFacade.getWaitingQueueWithPosition(waitingQueueTokenUuid));

    return ResponseEntity.ok(new GetWaitingQueuePositionResponse(waitingQueue));
  }

}
