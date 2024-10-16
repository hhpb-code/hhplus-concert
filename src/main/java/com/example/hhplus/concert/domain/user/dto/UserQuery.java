package com.example.hhplus.concert.domain.user.dto;

import com.example.hhplus.concert.domain.user.UserConstants;
import jakarta.validation.constraints.NotNull;

public class UserQuery {

  public record GetUserByIdQuery(
      @NotNull(message = UserConstants.USER_ID_NULL_MESSAGE)
      Long id
  ) {

  }

  public record GetUserWalletByUserIdQuery(
      @NotNull(message = UserConstants.USER_ID_NULL_MESSAGE)
      Long userId
  ) {

  }

}
