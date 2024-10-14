package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.waitingqueue.model.WaitingQueueStatus;
import com.example.hhplus.concert.interfaces.api.CommonHttpHeader;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenRequest;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.GetWaitingQueueStatusResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.WaitingQueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "WaitingQueue", description = "대기열 API")
@RestController
@RequestMapping("/api/v1/waiting-queue")
public class WaitingQueueController {

  @PostMapping("/tokens")
  @Operation(summary = "대기열 토큰 생성", description = "새로운 대기열 토큰을 생성합니다.")
  @ApiResponse(responseCode = "201", description = "대기열 토큰 생성 성공")
  public ResponseEntity<CreateWaitingQueueTokenResponse> createWaitingQueueToken(
      @RequestBody CreateWaitingQueueTokenRequest request
  ) {
    UUID token = UUID.randomUUID();
    WaitingQueueResponse waitingQueue = new WaitingQueueResponse(1L, request.concertId(),
        token.toString(),
        WaitingQueueStatus.WAITING, LocalDateTime.now(), LocalDateTime.now(), null);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateWaitingQueueTokenResponse(waitingQueue));
  }

  @GetMapping("/status")
  @Operation(summary = "대기열 상태 조회", description = "토큰을 이용하여 대기열 상태를 조회합니다.")
  @ApiResponse(responseCode = "200", description = "대기열 상태 조회 성공")
  public ResponseEntity<GetWaitingQueueStatusResponse> getWaitingQueueStatus(
      @RequestHeader(CommonHttpHeader.X_WAITING_QUEUE_TOKEN_UUID) String waitingQueueTokenUuid) {
    WaitingQueueResponse waitingQueue = new WaitingQueueResponse(1L, 2L, waitingQueueTokenUuid,
        WaitingQueueStatus.WAITING, LocalDateTime.now(), LocalDateTime.now(), null);

    return ResponseEntity.ok(new GetWaitingQueueStatusResponse(waitingQueue));
  }

}
