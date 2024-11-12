package com.example.hhplus.concert.domain.user.dto;

public class UserRepositoryParam {

  public record GetUserByIdParam(
      Long id
  ) {

  }

  public record GetUserWalletByUserIdParam(
      Long userId
  ) {

  }

  public record GetUserWalletByWalletIdParam(
      Long walletId
  ) {

  }

  public record GetUserWalletByWalletUserIdWithLockParam(
      Long userId
  ) {

  }
}
