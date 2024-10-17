package com.example.hhplus.concert.infra.db.user.impl;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserErrorCode;
import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByUserIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdWithLockParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletUserIdIdWithLockParam;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import com.example.hhplus.concert.infra.db.user.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  private final WalletJpaRepository walletJpaRepository;

  @Override
  public void saveWallet(Wallet wallet) {

  }

  @Override
  public User getUser(GetUserByIdParam param) {
    return userJpaRepository.findById(param.id())
        .orElseThrow(() -> new BusinessException(UserErrorCode.USER_NOT_FOUND));
  }

  @Override
  public Wallet getWallet(GetUserWalletByUserIdParam param) {
    return null;
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletIdParam param) {
    return null;
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletIdWithLockParam param) {
    return null;
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletUserIdIdWithLockParam param) {
    return walletJpaRepository.findByUserIdWithLock(param.userId())
        .orElseThrow(() -> new BusinessException(UserErrorCode.WALLET_NOT_FOUND));
  }
}
