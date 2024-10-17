package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.interfaces.api.controller.IUserController;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.ChargeWalletAmountRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.ChargeWalletAmountResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.WalletResponse;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements IUserController {

  @GetMapping("/{userId}/wallets")
  public ResponseEntity<GetWalletResponse> getWallets(@PathVariable Long userId) {
    WalletResponse wallet = new WalletResponse(1L, userId, 10000, LocalDateTime.now(),
        LocalDateTime.now());

    return ResponseEntity.ok(new GetWalletResponse(wallet));
  }

  @PutMapping("/{userId}/wallets/{walletId}/charge")
  public ResponseEntity<ChargeWalletAmountResponse> chargeWallet(@PathVariable Long userId,
      @PathVariable Long walletId, @RequestBody ChargeWalletAmountRequest request) {
    WalletResponse wallet = new WalletResponse(walletId, userId, request.amount(),
        LocalDateTime.now(),
        LocalDateTime.now());

    return ResponseEntity.ok(new ChargeWalletAmountResponse(wallet));
  }

}
