package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.user.model.Balance;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetBalanceResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateBalanceRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateBalanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @GetMapping("/{userId}/balances")
  @Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.")
  public ResponseEntity<GetBalanceResponse> getBalance(
      @PathVariable Long userId
  ) {
    Balance balance = new Balance(1L, userId, 3000L);

    return ResponseEntity.ok(new GetBalanceResponse(balance));
  }

  @PutMapping("/{userId}/balances/{balanceId}")
  @Operation(summary = "잔액 변경", description = "사용자의 잔액을 변경합니다.")
  public ResponseEntity<UpdateBalanceResponse> updateBalance(
      @PathVariable Long userId,
      @PathVariable Long balanceId,
      @RequestBody UpdateBalanceRequest request
  ) {
    Balance balance = new Balance(balanceId, userId, request.amount());

    return ResponseEntity.ok(new UpdateBalanceResponse(balance));
  }

}
