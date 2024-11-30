package com.zerobase.account.validator;

import com.google.common.base.Objects;
import com.zerobase.account.domain.Account;
import com.zerobase.account.domain.AccountUser;
import com.zerobase.account.domain.Transaction;
import com.zerobase.account.exception.AccountException;
import com.zerobase.account.type.AccountStatus;
import com.zerobase.account.type.ErrorCode;
import com.zerobase.account.util.TransactionConstants;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {

    public void validateUseBalance(Long amount, AccountUser accountUser, Account account) {
        if (!Objects.equal(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UNMATCH);
        }

        if (!Objects.equal(account.getAccountStatus(), AccountStatus.IN_USE)) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }

        if (account.getBalance() < amount) {
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }

        if (amount < TransactionConstants.MIN_AMOUNT) {
            throw new AccountException(ErrorCode.AMOUNT_TOO_SMALL);
        }
        if (amount >= TransactionConstants.MAX_AMOUNT) {
            throw new AccountException(ErrorCode.AMOUNT_TOO_BIG);
        }
    }

    public void validateCancelBalance(Long amount, Transaction transaction) {
        if (!Objects.equal(transaction.getAmount(), amount)) {
            throw new AccountException(ErrorCode.CANCEL_AMOUNT_UNMATCH);
        }
    }
}
