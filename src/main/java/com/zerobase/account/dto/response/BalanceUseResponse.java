package com.zerobase.account.dto.response;

import com.zerobase.account.type.TransactionResult;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BalanceUseResponse {


    private String accountNumber;
    private TransactionResult transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactionAt;

    public static BalanceUseResponse fromDto(TransactionResponse transactionResponse) {
        return BalanceUseResponse.builder()
                .accountNumber(transactionResponse.getAccountNumber())
                .transactionResult(transactionResponse.getTransactionResult())
                .transactionId(transactionResponse.getTransactionId())
                .amount(transactionResponse.getAmount())
                .transactionAt(transactionResponse.getTransactedAt())
                .build();
    }
};
	

