package com.example.hhplus.concert.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.user.UserConstants;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("유저 Query 단위 테스트")
class UserQueryTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }


  @Nested
  @DisplayName("유저 조회 Query By get")
  class GetUserQueryTest {

    @Nested
    @DisplayName("유저 조회 Query By id")
    class GetUserByIdQueryTest {

      @Test
      @DisplayName("유저 조회 Query 생성 실패 - id가 null")
      void shouldThrowExceptionWhenIdIsNull() {
        // given
        final Long id = null;
        final GetUserByIdQuery query = new GetUserByIdQuery(id);

        // when
        final Set<ConstraintViolation<GetUserByIdQuery>> violations = validator.validate(query);

        final ConstraintViolation<GetUserByIdQuery> violation = violations.stream()
            .filter(v -> v.getPropertyPath().toString().equals("id"))
            .findFirst()
            .get();

        // then
        assertThat(violation.getMessage()).isEqualTo(UserConstants.USER_ID_NULL_MESSAGE);
      }

      @Test
      @DisplayName("유저 조회 Query 생성 성공")
      void shouldSuccessfullyCreateGetUserByIdQuery() {
        // given
        final Long id = 1L;
        final GetUserByIdQuery query = new GetUserByIdQuery(id);

        // when
        final Set<ConstraintViolation<GetUserByIdQuery>> violations = validator.validate(query);

        // then
        assertThat(violations).isEmpty();
      }
    }

  }
}