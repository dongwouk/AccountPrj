package com.zerobase.account.service;

import java.time.LocalDateTime;
import java.util.UUID;


import com.zerobase.account.validator.TransactionValidator;
import org.springframework.stereotype.Service;

import com.zerobase.account.domain.Account;
import com.zerobase.account.domain.AccountUser;
import com.zerobase.account.dto.response.TransactionResponse;
import com.zerobase.account.exception.AccountException;
import com.zerobase.account.repository.AccountRepository;
import com.zerobase.account.repository.AccountUserRepository;
import com.zerobase.account.repository.TransactionRepository;
import com.zerobase.account.type.ErrorCode;
import com.zerobase.account.type.TransactionResult;
import com.zerobase.account.type.Transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;
    private final TransactionValidator transactionValidator;

    @Transactional
    public TransactionResponse debitAccount(Long userId, String accountNumber, Long amount) {
        AccountUser accountUser = getAccountUserById(userId);

        Account account = getAccountByNumber(accountNumber);

        transactionValidator.validateUseBalance(amount, accountUser, account);

        account.useBalance(amount);

        return TransactionResponse.fromEntity(saveAndGetTransaction(
                Transaction.USE,
                TransactionResult.SUCCESS,
                amount,
                account));
    }

    private com.zerobase.account.domain.Transaction saveAndGetTransaction(
            Transaction transaction,
            TransactionResult transactionResult,
            Long amount, Account account) {
        return transactionRepository.save(
                com.zerobase.account.domain.Transaction.builder()
                        .transaction(transaction)
                        .transactionResult(transactionResult)
                        .account(account)
                        .amount(amount)
                        .balanceSnapshot(account.getBalance())
                        .transactionId(UUID.randomUUID().toString().replace("-", ""))
                        .transactedAt(LocalDateTime.now())
                        .build()
        );
    }


    @Transactional
    public void saveFailedTransaction(
            Transaction transactionType,
            TransactionResult transactionResult, String accountNumber, Long amount) {
        Account account = getAccountByNumber(accountNumber);

        saveAndGetTransaction(
                transactionType,
                transactionResult,
                amount,
                account);
    }

    @Transactional
    public TransactionResponse refundTransaction(String transactionId, String accountNumber, Long amount) {
        com.zerobase.account.domain.Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND));

        Account account = getAccountByNumber(accountNumber);

        transactionValidator.validateCancelBalance(amount, transaction);

        account.cancelBalance(amount);

        return TransactionResponse.fromEntity(saveAndGetTransaction(
                Transaction.CANCEL,
                TransactionResult.SUCCESS,
                amount,
                account));
    }


    @Transactional(readOnly = true)
    public TransactionResponse queryTransactionId(String transactionId) {
        return TransactionResponse.fromEntity(
                transactionRepository.findByTransactionId(transactionId)
                        .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND))
        );
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
    }

    private AccountUser getAccountUserById(Long userId) {
        return accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
    }

}
