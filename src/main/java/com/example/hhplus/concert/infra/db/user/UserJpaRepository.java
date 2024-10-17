package com.example.hhplus.concert.infra.db.user;

import com.example.hhplus.concert.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
