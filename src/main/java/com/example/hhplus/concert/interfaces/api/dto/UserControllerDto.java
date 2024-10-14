package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.domain.user.model.WalletOperationType;

public class UserControllerDto {

  public record GetWalletResponse(
      Wallet balance
  ) {

  }

  public record UpdateWalletRequest(
      Long amount, WalletOperationType operationType
  ) {

  }

  public record UpdateWalletResponse(
      Wallet balance
  ) {

  }
}
