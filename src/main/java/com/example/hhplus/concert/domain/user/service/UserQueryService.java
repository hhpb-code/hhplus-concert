package com.example.hhplus.concert.domain.user.service;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

  private final UserRepository userRepository;

  public User getUser(GetUserByIdQuery query) {
    return userRepository.getUser(new GetUserByIdParam(query.id()));
  }

}
