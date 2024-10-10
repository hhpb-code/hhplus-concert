package com.example.hhplus.concert.interfaces.api.dto;

import com.example.hhplus.concert.domain.user.model.Balance;
import com.example.hhplus.concert.domain.user.model.BalanceOperationType;

public class UserControllerDto {

  public record GetBalanceResponse(
      Balance balance
  ) {

  }

  public record UpdateBalanceRequest(
      Long amount, BalanceOperationType operationType
  ) {

  }

  public record UpdateBalanceResponse(
      Balance balance
  ) {

  }
}
