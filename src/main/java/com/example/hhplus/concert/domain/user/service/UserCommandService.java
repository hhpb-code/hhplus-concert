package com.example.hhplus.concert.domain.user.service;

import com.example.hhplus.concert.domain.user.UserRepository;
import com.example.hhplus.concert.domain.user.dto.UserCommand.ChargeUserWalletAmountByWalletIdCommand;
import com.example.hhplus.concert.domain.user.dto.UserCommand.WithdrawUserWalletAmountCommand;
import com.example.hhplus.concert.domain.user.dto.UserRepositoryParam.GetUserWalletByWalletIdParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserCommandService {

  private final UserRepository userRepository;

  public void chargeUserWalletAmount(ChargeUserWalletAmountByWalletIdCommand command) {
    var wallet = userRepository.getWallet(
        new GetUserWalletByWalletIdParam(command.walletId()));

    wallet.chargeAmount(command.amount());

    userRepository.saveWallet(wallet);
  }

  public void withDrawUserWalletAmount(WithdrawUserWalletAmountCommand command) {
    var wallet = userRepository.getWallet(new GetUserWalletByWalletIdParam(command.walletId()));

    wallet.withdrawAmount(command.amount());

    userRepository.saveWallet(wallet);
  }
}
