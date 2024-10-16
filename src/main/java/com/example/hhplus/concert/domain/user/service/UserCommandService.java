package com.example.hhplus.concert.domain.user.service;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdWithLockParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandService {

  private final UserRepository userRepository;

  @Transactional
  public void chargeUserWalletAmount(ChargeUserWalletAmountByWalletIdCommand command) {
    var wallet = userRepository.getWallet(
        new GetUserWalletByWalletIdWithLockParam(command.walletId()));

    wallet.chargeAmount(command.amount());

    userRepository.saveWallet(wallet);
  }
}
