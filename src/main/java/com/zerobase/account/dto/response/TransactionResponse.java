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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
	private String accountNumber;
	private Transaction transaction;
	private TransactionResult transactionResult;
	private Long amount;
	private Long balanceSnapshot;
	private String transactionId;
	private LocalDateTime transactedAt;
	
	public static TransactionResponse fromEntity(com.zerobase.account.domain.Transaction transaction) {
		return TransactionResponse.builder()
				.accountNumber(transaction.getAccount().getAccountNumber())
				.transaction(transaction.getTransaction())
				.transactionResult(transaction.getTransactionResult())
				.amount(transaction.getAmount())
				.balanceSnapshot(transaction.getBalanceSnapshot())
				.transactionId(transaction.getTransactionId())
				.transactedAt(transaction.getTransactedAt())
				.build();
	}
}
