package com.example.hhplus.concert.infra.db.user.impl;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByUserIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdWithLockParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletUserIdIdWithLockParam;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  @Override
  public void saveWallet(Wallet wallet) {

  }

  @Override
  public User getUser(GetUserByIdParam param) {
    return null;
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
    return null;
  }
}
