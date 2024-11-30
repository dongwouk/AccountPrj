package com.zerobase.account.validator;

import com.google.common.base.Objects;
import com.zerobase.account.domain.Account;
import com.zerobase.account.domain.AccountUser;
import com.zerobase.account.exception.AccountException;
import com.zerobase.account.repository.AccountRepository;
import com.zerobase.account.type.AccountStatus;
import com.zerobase.account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidator {

    private final AccountRepository accountRepository;

    // 계좌 생성 유효성 검사
    public void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(ErrorCode.MAX_COUNT_PER_USER_10);
        }
    }

    // 계좌 삭제 유효성 검사
    public void validateDeleteAccount(AccountUser accountUser,
                                      Account account) {
        if (!Objects.equal(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UNMATCH);
        }

        if (account.getAccountStatus().equals(AccountStatus.UNREGISTERED)) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }

        if (account.getBalance() > 0) {
            throw new AccountException(ErrorCode.BALANCE_NOT_EMPTY);
        }
    }
}
