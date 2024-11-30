package com.zerobase.account.dto.response;

import com.zerobase.account.type.TransactionResult;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BalanceCancelResponse {


    private String accountNumber;
    private TransactionResult transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;

    public static BalanceCancelResponse fromDto(TransactionResponse transactionResponse) {
        return BalanceCancelResponse.builder()
                .accountNumber(transactionResponse.getAccountNumber())
                .transactionResult(transactionResponse.getTransactionResult())
                .transactionId(transactionResponse.getTransactionId())
                .amount(transactionResponse.getAmount())
                .transactedAt(transactionResponse.getTransactedAt())
                .build();
    }
};

