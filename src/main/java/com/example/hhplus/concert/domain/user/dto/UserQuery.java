package com.example.hhplus.concert.domain.user.dto;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserErrorCode;

public class UserQuery {

  public record GetUserByIdQuery(Long id) {

    public GetUserByIdQuery {
      if (id == null) {
        throw new BusinessException(UserErrorCode.USER_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetUserWalletByUserIdQuery(Long userId) {

    public GetUserWalletByUserIdQuery {
      if (userId == null) {
        throw new BusinessException(UserErrorCode.USER_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetUserWalletByUserIdWithLockQuery(Long userId) {

    public GetUserWalletByUserIdWithLockQuery {
      if (userId == null) {
        throw new BusinessException(UserErrorCode.USER_ID_MUST_NOT_BE_NULL);
      }
    }
  }

  public record GetUserWalletByIdQuery(Long walletId) {

    public GetUserWalletByIdQuery {
      if (walletId == null) {
        throw new BusinessException(UserErrorCode.WALLET_ID_MUST_NOT_BE_NULL);
      }
    }
  }

}
