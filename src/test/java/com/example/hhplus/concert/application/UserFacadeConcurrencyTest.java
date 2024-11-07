package com.example.hhplus.concert.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.hhplus.concert.domain.user.model.User;
import com.example.hhplus.concert.domain.user.model.Wallet;
import com.example.hhplus.concert.infra.db.user.UserJpaRepository;
import com.example.hhplus.concert.infra.db.user.WalletJpaRepository;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("UserFacade 동시성 테스트")
class UserFacadeConcurrencyTest {

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
  @DisplayName("사용자 지갑 잔액 충전 동시성 테스트")
  class ChargeUserWalletAmount {

    @Nested
    @DisplayName("사용자 지갑 잔액 충전 동시성 테스트 - 분산 락")
    class ChargeUserWalletAmountWithDistributionLock {

      @Test
      @DisplayName("사용자 지갑 잔액 충전 성공 - 동시 충전")
      void shouldSuccessfullyChargeUserWalletAmount() {
        // given
        final int threadCount = 100;
        final int perChargeAmount = 100;
        final User user = userJpaRepository.save(User.builder().name("name").build());
        final Wallet wallet = walletJpaRepository.save(
            Wallet.builder().userId(user.getId()).amount(0).build());

        // when
        final List<CompletableFuture<Void>> futures = IntStream.range(0, threadCount)
            .mapToObj(i -> CompletableFuture.runAsync(() -> {

              userFacade.chargeUserWalletAmount(user.getId(), wallet.getId(),
                  perChargeAmount);

            }))
            .toList();

        long start = System.currentTimeMillis();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        long end = System.currentTimeMillis();

        log.info("분산 락 Execution Time: " + (end - start) + "ms");

        // then
        final Wallet updatedWallet = walletJpaRepository.findById(wallet.getId()).get();
        assertThat(updatedWallet.getAmount()).isEqualTo(threadCount * perChargeAmount);
      }
    }

  }

}