package com.example.hhplus.concert.infra.db.user.impl;

import com.example.hhplus.concert.domain.support.error.CoreException;
import com.example.hhplus.concert.domain.support.error.ErrorType;
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
  public Wallet saveWallet(Wallet wallet) {
    return walletJpaRepository.save(wallet);
  }

  @Override
  public User getUser(GetUserByIdParam param) {
    return userJpaRepository.findById(param.id())
        .orElseThrow(() -> new CoreException(ErrorType.User.USER_NOT_FOUND));
  }

  @Override
  public Wallet getWallet(GetUserWalletByUserIdParam param) {
    return walletJpaRepository.findByUserId(param.userId())
        .orElseThrow(() -> new CoreException(ErrorType.User.WALLET_NOT_FOUND));
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletIdParam param) {
    return walletJpaRepository.findById(param.walletId())
        .orElseThrow(() -> new CoreException(ErrorType.User.WALLET_NOT_FOUND));
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletIdWithLockParam param) {
    return walletJpaRepository.findByIdWithLock(param.walletId())
        .orElseThrow(() -> new CoreException(ErrorType.User.WALLET_NOT_FOUND));
  }

  @Override
  public Wallet getWallet(GetUserWalletByWalletUserIdIdWithLockParam param) {
    return walletJpaRepository.findByUserIdWithLock(param.userId())
        .orElseThrow(() -> new CoreException(ErrorType.User.WALLET_NOT_FOUND));
  }
}
