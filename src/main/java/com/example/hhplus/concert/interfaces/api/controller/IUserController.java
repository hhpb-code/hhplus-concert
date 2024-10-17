package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.ChargeWalletAmountRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.ChargeWalletAmountResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "사용자 API")
public interface IUserController {

  @Operation(summary = "지갑 조회", description = "사용자의 지갑을 조회합니다.")
  ResponseEntity<GetWalletResponse> getWallets(
      @Schema(description = "사용자 ID", example = "1")
      Long userId
  );

  @Operation(summary = "지갑 충전", description = "사용자의 지갑을 충전합니다.")
  ResponseEntity<ChargeWalletAmountResponse> chargeWallet(
      @Schema(description = "사용자 ID", example = "1")
      Long userId,

      @Schema(description = "지갑 ID", example = "1")
      Long walletId,

      ChargeWalletAmountRequest request
  );

}
