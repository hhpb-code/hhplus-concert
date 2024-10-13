package com.example.hhplus.concert.interfaces.api.controller;

import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.GetWalletResponse;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletRequest;
import com.example.hhplus.concert.interfaces.api.dto.UserControllerDto.UpdateWalletResponse;
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

  @Operation(summary = "지갑 수정", description = "사용자의 지갑을 수정합니다.")
  ResponseEntity<UpdateWalletResponse> updateWallet(
      @Schema(description = "사용자 ID", example = "1")
      Long userId,

      @Schema(description = "지갑 ID", example = "1")
      Long walletId,

      UpdateWalletRequest request);

}
