package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenRequest;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.GetQueueTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "QueueToken", description = "대기열 토큰 API")
public interface IQueueTokenController {

  @Operation(summary = "대기열 토큰 조회", description = "대기열 토큰을 조회합니다.")
  ResponseEntity<GetQueueTokenResponse> getQueueToken(
      @Schema(description = "대기열 토큰 ID", example = "1")
      Long queueTokenId
  );

  @Operation(summary = "대기열 토큰 발급", description = "대기열 토큰을 발급합니다.")
  @ApiResponse(responseCode = "201", description = "대기열 토큰 발급 성공")
  ResponseEntity<CreateQueueTokenResponse> createQueueToken(
      CreateQueueTokenRequest request
  );

}