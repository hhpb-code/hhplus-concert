package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.domain.user.model.WalletOperationType;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserControllerDto {

  public record GetWalletResponse(
      WalletDto balance
  ) {

  }

  public record UpdateWalletRequest(
      Long amount, WalletOperationType operationType
  ) {

  }

  public record UpdateWalletResponse(
      WalletDto balance
  ) {

  }

  // NOTE: Presentation POJO (DTO) for Wallet
  @Schema(description = "지갑 정보")
  public record WalletDto(
      Long id,
      Long userId,
      Long amount
  ) {

    public WalletDto(Wallet wallet) {
      this(wallet.id(), wallet.userId(), wallet.amount());
    }

  }
}
