package com.zerobase.account.dto.response;

import java.time.LocalDateTime;

import com.zerobase.account.type.TransactionResult;
import com.zerobase.account.type.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionQueryResponse {

    private String accountNumber;
    private Transaction transaction;
    private TransactionResult transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;

    public static TransactionQueryResponse from(TransactionResponse transactionResponse) {
        return TransactionQueryResponse.builder()
                .accountNumber(transactionResponse.getAccountNumber())
                .transaction(transactionResponse.getTransaction())
                .transactionResult(transactionResponse.getTransactionResult())
                .transactionId(transactionResponse.getTransactionId())
                .amount(transactionResponse.getAmount())
                .transactedAt(transactionResponse.getTransactedAt())
                .build();
    }
};

