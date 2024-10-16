package com.example.hhplus.concert.domain.user.dto;

import com.example.hhplus.concert.domain.user.UserConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UserCommand {

  public record ChargeUserWalletAmountByWalletIdCommand(
      @NotNull(message = UserConstants.WALLET_ID_MUST_NOT_BE_NULL_MESSAGE)
      Long walletId,

      @Positive(message = UserConstants.AMOUNT_MUST_BE_POSITIVE_MESSAGE)
      @NotNull(message = UserConstants.AMOUNT_MUST_NOT_BE_NULL_MESSAGE)
      Integer amount
  ) {

  }

}
