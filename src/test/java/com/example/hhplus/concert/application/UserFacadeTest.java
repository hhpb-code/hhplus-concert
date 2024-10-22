package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.hhplus.concert.domain.common.exception.BusinessException;
import com.example.hhplus.concert.domain.user.UserConstants;
import com.example.hhplus.concert.domain.user.UserErrorCode;
import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import com.example.hhplus.concert.infra.db.user.WalletJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("UserFacade 통합 테스트")
class UserFacadeTest {

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Autowired
  private WalletJpaRepository walletJpaRepository;


  @BeforeEach
  void setUp() {
    userJpaRepository.deleteAll();
    walletJpaRepository.deleteAll();
  }

  @Nested
  @DisplayName("사용자 지갑 잔액 충전")
  class ChargeUserWalletAmount {

    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 사용자가 존재하지 않음")
    void shouldThrowUserNotFoundException() {
      // given
      final Long userId = 1L;
      final Long walletId = 1L;
      final Integer amount = 1000;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(userId, walletId, amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 지갑이 존재하지 않음")
    void shouldThrowWalletNotFoundException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Long walletId = 1L;
      final Integer amount = 1000;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(user.getId(), walletId, amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.WALLET_NOT_FOUND.getMessage());
    }


    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 지갑의 사용자와 요청한 사용자가 다름")
    void shouldThrowWalletUserNotMatchException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(user.getId() + 1).build());
      final Integer amount = 1000;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(), amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.WALLET_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 충전 금액이 0보다 작음")
    void shouldThrowAmountLessThanZeroException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(Wallet.builder().userId(user.getId()).build());
      final Integer amount = -1;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(), amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 충전 금액이 0")
    void shouldThrowAmountZeroException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(Wallet.builder().userId(user.getId()).build());
      final Integer amount = 0;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(), amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.AMOUNT_MUST_BE_POSITIVE.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 잔액 충전 실패 - 한도 초과")
    void shouldThrowAmountExceedLimitException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(user.getId()).amount(0).build());
      final Integer amount = UserConstants.MAX_WALLET_AMOUNT + 1;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(), amount));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.EXCEED_LIMIT_AMOUNT.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 잔액 충전 성공")
    void shouldSuccessfullyChargeUserWalletAmount() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(
          Wallet.builder().userId(user.getId()).amount(0).build());
      final Integer amount = UserConstants.MAX_WALLET_AMOUNT;

      // when
      final Wallet result = userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(), amount);

      // then
      assertThat(result.getAmount()).isEqualTo(wallet.getAmount() + amount);
    }

  }

  @Nested
  @DisplayName("사용자 지갑 조회")
  class GetWallet {

    @Test
    @DisplayName("사용자 지갑 조회 실패 - 사용자가 존재하지 않음")
    void shouldThrowUserNotFoundException() {
      // given
      final Long userId = 1L;

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.getWallet(userId));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 조회 실패 - 지갑이 존재하지 않음")
    void shouldThrowWalletNotFoundException() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());

      // when
      final BusinessException result = assertThrows(BusinessException.class,
          () -> userFacade.getWallet(user.getId()));

      // then
      assertThat(result.getMessage()).isEqualTo(UserErrorCode.WALLET_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 지갑 조회 성공")
    void shouldSuccessfullyGetWallet() {
      // given
      final User user = userJpaRepository.save(User.builder().name("name").build());
      final Wallet wallet = walletJpaRepository.save(Wallet.builder().userId(user.getId()).build());

      // when
      final Wallet result = userFacade.getWallet(user.getId());

      // then
      assertThat(result.getId()).isEqualTo(wallet.getId());
    }

  }

}