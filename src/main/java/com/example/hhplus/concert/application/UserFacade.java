package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserErrorCode;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdWithLockQuery;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.domain.user.service.UserCommandService;
import com.example.hhplus.concert.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final UserQueryService userQueryService;

  private final UserCommandService userCommandService;

  @Transactional
  public Wallet chargeUserWalletAmount(Long userId, Long walletId, Integer amount) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var wallet = userQueryService.getWallet(new GetUserWalletByUserIdWithLockQuery(user.getId()));

    if (!wallet.getId().equals(walletId)) {
      throw new BusinessException(UserErrorCode.WALLET_NOT_FOUND);
    }

    userCommandService.chargeUserWalletAmount(
        new ChargeUserWalletAmountByWalletIdCommand(wallet.getId(), amount));

    return userQueryService.getWallet(new GetUserWalletByIdQuery(wallet.getId()));
  }

  @Transactional(readOnly = true)
  public Wallet getWallet(Long userId) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    return userQueryService.getWallet(new GetUserWalletByUserIdQuery(user.getId()));
  }

}
