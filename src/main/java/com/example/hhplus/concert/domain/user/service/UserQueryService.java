package com.example.hhplus.concert.domain.user.service;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public User getUser(GetUserByIdQuery query) {
    return userRepository.getUser(new GetUserByIdParam(query.id()));
  }


  @Transactional(readOnly = true)
  public Wallet getWallet(GetUserWalletByUserIdQuery query) {
    return userRepository.getWallet(new GetUserWalletByUserIdParam(query.userId()));
  }
}
