package com.example.hhplus.concert.domain.user;

import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByUserIdParam;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;

public interface UserRepository {

  User getUser(GetUserByIdParam param);
  
  Wallet getWallet(GetUserWalletByUserIdParam param);
}
