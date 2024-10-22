package com.example.hhplus.concert.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.support.error.ErrorType;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdQuery;
import com.example.hhplus.concert.domain.user.dto.UserQuery.GetUserWalletByUserIdWithLockQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("유저 Query 단위 테스트")
class UserQueryTest {

  @Nested
  @DisplayName("유저 조회 Query 테스트")
  class GetUserByIdQueryTest {

    @Test
    @DisplayName("유저 조회 Query 생성 실패 - id가 null인 경우")
    void shouldThrowBusinessExceptionWhenIdIsNull() {
      // given
      final Long id = null;

      // when & then
      final Exception exception = assertThrows(Exception.class,
          () -> new GetUserByIdQuery(id));

      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.USER_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("유저 조회 Query 생성 성공")
    void shouldSuccessfullyCreateGetUserByIdQuery() {
      // given
      final Long id = 1L;

      // when
      final GetUserByIdQuery query = new GetUserByIdQuery(id);

      // then
      assertThat(query).isNotNull();
      assertThat(query.id()).isEqualTo(id);
    }
  }

  @Nested
  @DisplayName("유저 지갑 조회 Query 테스트")
  class GetUserWalletByUserIdQueryTest {

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 실패 - userId가 null인 경우")
    void shouldThrowBusinessExceptionWhenUserIdIsNull() {
      // given
      final Long userId = null;

      // when & then
      final Exception exception = assertThrows(Exception.class,
          () -> new GetUserWalletByUserIdQuery(userId));

      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.USER_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 성공")
    void shouldSuccessfullyCreateGetUserWalletByUserIdQuery() {
      // given
      final Long userId = 1L;

      // when
      final GetUserWalletByUserIdQuery query = new GetUserWalletByUserIdQuery(userId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.userId()).isEqualTo(userId);
    }
  }

  @Nested
  @DisplayName("유저 지갑 조회 Query 테스트 (with lock)")
  class GetUserWalletByUserIdWithLockQueryTest {

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 실패 - userId가 null인 경우")
    void shouldThrowBusinessExceptionWhenUserIdIsNull() {
      // given
      final Long userId = null;

      // when & then
      final Exception exception = assertThrows(Exception.class,
          () -> new GetUserWalletByUserIdWithLockQuery(userId));

      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.USER_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 성공")
    void shouldSuccessfullyCreateGetUserWalletByUserIdWithLockQuery() {
      // given
      final Long userId = 1L;

      // when
      final GetUserWalletByUserIdWithLockQuery query = new GetUserWalletByUserIdWithLockQuery(
          userId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.userId()).isEqualTo(userId);
    }
  }

  @Nested
  @DisplayName("유저 지갑 조회 Query 테스트 (by walletId)")
  class GetUserWalletByIdQueryTest {

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 실패 - walletId가 null인 경우")
    void shouldThrowBusinessExceptionWhenWalletIdIsNull() {
      // given
      final Long walletId = null;

      // when & then
      final Exception exception = assertThrows(Exception.class,
          () -> new GetUserWalletByIdQuery(walletId));

      assertThat(exception.getMessage()).isEqualTo(
          ErrorType.User.WALLET_ID_MUST_NOT_BE_NULL.getMessage());
    }

    @Test
    @DisplayName("유저 지갑 조회 Query 생성 성공")
    void shouldSuccessfullyCreateGetUserWalletByIdQuery() {
      // given
      final Long walletId = 1L;

      // when
      final GetUserWalletByIdQuery query = new GetUserWalletByIdQuery(walletId);

      // then
      assertThat(query).isNotNull();
      assertThat(query.walletId()).isEqualTo(walletId);
    }
  }
}
