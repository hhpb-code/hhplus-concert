package com.example.hhplus.concert.domain.user.service;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdWithLockQuery;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByUserIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdParam;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletUserIdWithLockParam;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public User getUser(GetUserByIdQuery query) {
    return userRepository.getUser(new GetUserByIdParam(query.id()));
  }

  public Wallet getWallet(GetUserWalletByUserIdQuery query) {
    return userRepository.getWallet(new GetUserWalletByUserIdParam(query.userId()));
  }

  public Wallet getWallet(GetUserWalletByUserIdWithLockQuery query) {
    return userRepository.getWallet(new GetUserWalletByWalletUserIdWithLockParam(query.userId()));
  }

  public Wallet getWallet(GetUserWalletByIdQuery query) {
    return userRepository.getWallet(new GetUserWalletByWalletIdParam(query.walletId()));
  }

}
