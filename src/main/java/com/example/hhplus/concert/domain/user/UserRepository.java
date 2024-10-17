package com.example.hhplus.concert.domain.user;

import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByUserIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdWithLockParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletUserIdIdWithLockParam;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;

public interface UserRepository {

  void saveWallet(Wallet wallet);

  User getUser(GetUserByIdParam param);

  Wallet getWallet(GetUserWalletByUserIdParam param);

  Wallet getWallet(GetUserWalletByWalletIdParam param);

  Wallet getWallet(GetUserWalletByWalletIdWithLockParam param);

  Wallet getWallet(GetUserWalletByWalletUserIdIdWithLockParam param);
}
