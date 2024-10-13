package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.domain.queuetoken.model.QueueTokenStatus;
import com.example.hhplus.concert.domain.queuetoken.model.Queuetoken;
import com.example.hhplus.concert.interfaces.api.controller.IQueueTokenController;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenRequest;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.CreateQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.GetQueueTokenResponse;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.QueueTokenDto;
import com.example.hhplus.concert.interfaces.api.dto.QueueTokenControllerDto.QueueTokenDtoWithPosition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/queue-tokens")
public class QueueTokenController implements IQueueTokenController {

  @PostMapping
  public ResponseEntity<CreateQueueTokenResponse> createQueueToken(
      @RequestBody CreateQueueTokenRequest request) {
    Queuetoken queueToken = new Queuetoken(1L, request.concertSeatId(), request.userId(),
        QueueTokenStatus.WAITING, null);
    QueueTokenDto queueTokenDto = new QueueTokenDto(queueToken);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreateQueueTokenResponse(queueTokenDto));
  }

  @GetMapping
  public ResponseEntity<GetQueueTokenResponse> getQueueToken(
      @RequestHeader("X-Queue-Token") Long queueTokenId) {
    Queuetoken queueToken = new Queuetoken(queueTokenId, 1L, 1L, QueueTokenStatus.WAITING, null);
    QueueTokenDtoWithPosition queueTokenDto = new QueueTokenDtoWithPosition(queueToken, 10);

    return ResponseEntity.status(HttpStatus.OK).body(new GetQueueTokenResponse(queueTokenDto));
  }

}
