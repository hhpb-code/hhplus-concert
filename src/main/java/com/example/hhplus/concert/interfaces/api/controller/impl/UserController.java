package com.example.hhplus.concert.interfaces.api.controller.impl;

import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.interfaces.api.controller.IUserController;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.WalletDto;
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
    Wallet wallet = new Wallet(1L, userId, 10000L);
    WalletDto walletDto = new WalletDto(wallet.id(), wallet.userId(), wallet.amount());

    return ResponseEntity.ok(new GetWalletResponse(walletDto));
  }

  @PutMapping("/{userId}/wallets/{walletId}")
  public ResponseEntity<UpdateWalletResponse> updateWallet(@PathVariable Long userId,
      @PathVariable Long walletId, @RequestBody UpdateWalletRequest request) {
    Wallet wallet = new Wallet(walletId, userId, 10000L);
    WalletDto walletDto = new WalletDto(wallet.id(), wallet.userId(), wallet.amount());

    return ResponseEntity.ok(new UpdateWalletResponse(walletDto));
  }

}
