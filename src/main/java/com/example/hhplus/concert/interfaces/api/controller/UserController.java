package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
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

  @GetMapping("/{userId}/wallets")
  @Operation(summary = "지갑 조회", description = "사용자의 지갑을 조회합니다.")
  public ResponseEntity<GetWalletResponse> getWallets(@PathVariable Long userId) {
    WalletResponse wallet = new WalletResponse(1L, userId, 10000, LocalDateTime.now(),
        LocalDateTime.now());

    return ResponseEntity.ok(new GetWalletResponse(wallet));
  }

  @PutMapping("/{userId}/wallets/{walletId}")
  @Operation(summary = "지갑 수정", description = "사용자의 지갑을 수정합니다.")
  public ResponseEntity<UpdateWalletResponse> updateWallet(@PathVariable Long userId,
      @PathVariable Long walletId, @RequestBody UpdateWalletRequest request) {
    WalletResponse wallet = new WalletResponse(walletId, userId, request.amount(),
        LocalDateTime.now(),
        LocalDateTime.now());

    return ResponseEntity.ok(new UpdateWalletResponse(wallet));
  }

}
