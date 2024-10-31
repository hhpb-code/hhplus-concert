package com.example.hhplus.concert.application;

import com.example.hhplus.concert.domain.support.DistributedLockType;
import com.example.hhplus.concert.domain.support.annotation.DistributedLock;
import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType.User;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdWithLockQuery;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.domain.user.service.UserCommandService;
import com.example.hhplus.concert.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final UserQueryService userQueryService;

  private final UserCommandService userCommandService;

  @Deprecated(forRemoval = false)
  @Transactional
  public Wallet chargeUserWalletAmount(Long userId, Long walletId, Integer amount) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var wallet = userQueryService.getWallet(new GetUserWalletByUserIdWithLockQuery(user.getId()));

    if (!wallet.getId().equals(walletId)) {
      throw new CoreException(User.WALLET_NOT_MATCH_USER);
    }

    userCommandService.chargeUserWalletAmount(
        new ChargeUserWalletAmountByWalletIdCommand(wallet.getId(), amount));

    return userQueryService.getWallet(new GetUserWalletByIdQuery(wallet.getId()));
  }

  @Deprecated(forRemoval = false)
  @Retryable(
      retryFor = RuntimeException.class,
      noRetryFor = CoreException.class,
      backoff = @Backoff(50),
      maxAttempts = 100
  )
  public Wallet chargeUserWalletAmountWithOptimisticLock(Long userId, Long walletId,
      Integer amount) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var wallet = userQueryService.getWallet(new GetUserWalletByUserIdQuery(user.getId()));

    if (!wallet.getId().equals(walletId)) {
      throw new CoreException(User.WALLET_NOT_MATCH_USER);
    }

    userCommandService.chargeUserWalletAmount(
        new ChargeUserWalletAmountByWalletIdCommand(wallet.getId(), amount));

    return userQueryService.getWallet(new GetUserWalletByIdQuery(wallet.getId()));
  }

  @DistributedLock(type = DistributedLockType.USER_WALLET, keys = "userId")
  public Wallet chargeUserWalletAmountWithDistributionLock(Long userId, Long walletId,
      Integer amount) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    var wallet = userQueryService.getWallet(new GetUserWalletByUserIdQuery(user.getId()));

    if (!wallet.getId().equals(walletId)) {
      throw new CoreException(User.WALLET_NOT_MATCH_USER);
    }

    userCommandService.chargeUserWalletAmount(
        new ChargeUserWalletAmountByWalletIdCommand(wallet.getId(), amount));

    return userQueryService.getWallet(new GetUserWalletByIdQuery(wallet.getId()));
  }


  public Wallet getWallet(Long userId) {
    var user = userQueryService.getUser(new GetUserByIdQuery(userId));

    return userQueryService.getWallet(new GetUserWalletByUserIdQuery(user.getId()));
  }

}
