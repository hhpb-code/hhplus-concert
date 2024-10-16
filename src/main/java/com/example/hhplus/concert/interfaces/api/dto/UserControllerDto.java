package com.example.hhplus.concert.interfaces.api.dto;

import java.time.LocalDateTime;

public class UserControllerDto {

  // NOTE: Request, Response DTO

  public record GetWalletResponse(
      WalletResponse wallet
  ) {

  }

  public record ChargeWalletAmountRequest(
      Integer amount
  ) {

  }

  public record ChargeWalletAmountResponse(
      WalletResponse wallet
  ) {

  }

  // NOTE: Common DTO

  public record WalletResponse(
      Long id,
      Long userId,
      Integer amount,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) {

  }

}
