package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletResponse;
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

  @GetMapping("/{userId}/wallets")
  @Operation(summary = "지갑 조회", description = "사용자의 지갑을 조회합니다.")
  public ResponseEntity<GetWalletResponse> getWallets(@PathVariable Long userId) {
    Wallet wallet = new Wallet(1L, userId, 10000L);

    return ResponseEntity.ok(new GetWalletResponse(wallet));
  }

  @PutMapping("/{userId}/wallets/{walletId}")
  @Operation(summary = "지갑 수정", description = "사용자의 지갑을 수정합니다.")
  public ResponseEntity<UpdateWalletResponse> updateWallet(@PathVariable Long userId, @PathVariable Long walletId, @RequestBody UpdateWalletRequest request) {
    Wallet wallet = new Wallet(walletId, userId, 10000L);

    return ResponseEntity.ok(new UpdateWalletResponse(wallet));
  }

}
