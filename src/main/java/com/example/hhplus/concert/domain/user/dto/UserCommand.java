package com.example.hhplus.concert.domain.user.dto;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;

public class UserCommand {

  public record ChargeUserWalletAmountByWalletIdCommand(Long walletId, Integer amount) {

    public ChargeUserWalletAmountByWalletIdCommand {
      if (walletId == null) {
        throw new CoreException(ErrorType.User.WALLET_ID_MUST_NOT_BE_NULL);
      }
      if (amount == null) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }

  public record WithdrawUserWalletAmountCommand(Long walletId, Integer amount) {

    public WithdrawUserWalletAmountCommand {
      if (walletId == null) {
        throw new CoreException(ErrorType.User.WALLET_ID_MUST_NOT_BE_NULL);
      }
      if (amount == null) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_NOT_BE_NULL);
      }
      if (amount <= 0) {
        throw new CoreException(ErrorType.User.AMOUNT_MUST_BE_POSITIVE);
      }
    }
  }
}
