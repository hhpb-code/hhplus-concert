package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.CreateWaitingQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.WaitingQueueControllerDto.GetWaitingQueuePositionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "WaitingQueue", description = "대기열 API")
public interface IWaitingQueueController {

  @Operation(summary = "대기열 토큰 생성", description = "새로운 대기열 토큰을 생성합니다.")
  @ApiResponse(responseCode = "201", description = "대기열 토큰 생성 성공")
  ResponseEntity<CreateWaitingQueueTokenResponse> createWaitingQueueToken();

  @Operation(summary = "대기열 순번 조회", description = "대기 중인 토큰의 순번을 조회합니다.")
  ResponseEntity<GetWaitingQueuePositionResponse> getWaitingQueuePosition(
      @Schema(description = "대기열 토큰 UUID", example = "123e4567-e89b-12d3-a456-426614174000")
      String waitingQueueTokenUuid
  );

}
