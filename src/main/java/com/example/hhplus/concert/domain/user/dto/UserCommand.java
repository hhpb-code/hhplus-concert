package com.example.hhplus.concert.domain.user.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserErrorCode;

public class UserCommand {

  public record ChargeUserWalletAmountByWalletIdCommand(Long walletId, Integer amount) {

    public ChargeUserWalletAmountByWalletIdCommand {
      if (walletId == null) {
        throw new BusinessException(UserErrorCode.WALLET_ID_MUST_NOT_BE_NULL);
      }
      if (amount == null) {
        throw new BusinessException(UserErrorCode.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new BusinessException(UserErrorCode.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }

  public record WithdrawUserWalletAmountCommand(Long userId, Integer amount) {

    public WithdrawUserWalletAmountCommand {
      if (userId == null) {
        throw new BusinessException(UserErrorCode.USER_ID_MUST_NOT_BE_NULL);
      }
      if (amount == null) {
        throw new BusinessException(UserErrorCode.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new BusinessException(UserErrorCode.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }
}
