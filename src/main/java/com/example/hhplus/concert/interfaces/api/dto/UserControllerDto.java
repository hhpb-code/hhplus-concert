package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.user.model.WalletOperationType;
import java.time.LocalDateTime;

public class UserControllerDto {

  // NOTE: Request, Response DTO

  public record GetWalletResponse(
      WalletResponse wallet
  ) {

  }

  public record UpdateWalletRequest(
      Integer amount,
      WalletOperationType operationType
  ) {

  }

  public record UpdateWalletResponse(
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
