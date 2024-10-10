package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.queuetoken.model.QueueTokenStatus;
import com.example.hhplus.concert.domain.queuetoken.model.Queuetoken;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenRequestBody;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "QueueToken", description = "대기열 토큰 API")
@RestController
@RequestMapping("/api/v1/queue-tokens")
public class QueueTokenController {

  @PostMapping
  @Operation(summary = "대기열 토큰 발급", description = "대기열 토큰을 발급합니다.")
  @ApiResponse(responseCode = "201", description = "대기열 토큰 발급 성공")
  public ResponseEntity<CreateQueueTokenResponse> createQueueToken(
      @RequestBody CreateQueueTokenRequestBody request) {
    Queuetoken queueToken = new Queuetoken(1L, request.userId(), QueueTokenStatus.WAITING, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(new CreateQueueTokenResponse(queueToken));
  }
}
