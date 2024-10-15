package com.example.hhplus.concert.domain.user;

import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserByIdParam;
import com.example.hhplus.concert.domain.user.model.User;

public interface UserRepository {

  User getUser(GetUserByIdParam param);
  
}
