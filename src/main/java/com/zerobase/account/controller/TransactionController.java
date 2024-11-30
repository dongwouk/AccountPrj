package com.zerobase.account.controller;

import javax.validation.Valid;

import com.zerobase.account.dto.response.BalanceUseResponse;
import com.zerobase.account.dto.response.BalanceCancelResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.account.aop.AccountLock;
import com.zerobase.account.dto.request.BalanceCancelRequest;
import com.zerobase.account.dto.response.TransactionQueryResponse;
import com.zerobase.account.dto.request.BalanceUseRequest;
import com.zerobase.account.exception.AccountException;
import com.zerobase.account.service.TransactionService;
import com.zerobase.account.type.TransactionResult;
import com.zerobase.account.type.Transaction;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public TransactionQueryResponse queryTransaction(@PathVariable String transactionId) {
        return TransactionQueryResponse.from(
                transactionService.queryTransactionId(transactionId)
        );
    }

    @PostMapping("/use")
    @AccountLock
    public BalanceUseResponse useBalance(@Valid @RequestBody BalanceUseRequest request) {
        try {
            return BalanceUseResponse.fromDto(transactionService.debitAccount(
                    request.getUserId(),
                    request.getAccountNumber(),
                    request.getAmount()));
        } catch (AccountException e) {
            transactionService.saveFailedTransaction(
                    Transaction.USE,
                    TransactionResult.FAILURE,
                    request.getAccountNumber(),
                    request.getAmount());
            throw e;
        }
    }

    @DeleteMapping("/cancel")
    @AccountLock
    public BalanceCancelResponse cancelBalance(@Valid @RequestBody BalanceCancelRequest request) {
        try {
            return BalanceCancelResponse.fromDto(
                    transactionService.refundTransaction(
                            request.getTransactionId(),
                            request.getAccountNumber(),
                            request.getAmount())
            );
        } catch (AccountException e) {
            transactionService.saveFailedTransaction(
                    Transaction.CANCEL,
                    TransactionResult.FAILURE,
                    request.getAccountNumber(),
                    request.getAmount());
            throw e;
        }
    }
}
