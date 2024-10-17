package com.example.hhplus.concert.interfaces.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
      @Schema(description = "지갑 ID", example = "1")
      Long id,

      @Schema(description = "사용자 ID", example = "1")
      Long userId,

      @Schema(description = "잔액", example = "10000")
      Integer amount,

      @Schema(description = "생성 일시", example = "2024-12-31T23:59:59")
      LocalDateTime createdAt,

      @Schema(description = "수정 일시", example = "2024-12-31T23:59:59")
      LocalDateTime updatedAt
  ) {

  }

}
