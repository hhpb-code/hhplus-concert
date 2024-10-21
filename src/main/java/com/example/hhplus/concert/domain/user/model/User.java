package com.example.hhplus.concert.domain.user.model;

import com.example.hhplus.concert.domain.common.exception.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Builder
  public User(Long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id, createdAt, updatedAt);
    this.name = name;
  }

}
