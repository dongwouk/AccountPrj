package com.zerobase.account.dto.response;

import java.time.LocalDateTime;

import com.zerobase.account.domain.Account;

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
public class AccountResponse {
	private Long userId;
	private String accountNumber;
	private Long balance;
	
	private LocalDateTime registeredAt;
	private LocalDateTime unRegisteredAt;
	
	public static AccountResponse fromEntity(Account account) {
		return AccountResponse.builder()
				.userId(account.getAccountUser().getId())
				.accountNumber(account.getAccountNumber())
				.balance(account.getBalance())
				.registeredAt(account.getRegisteredAt())
				.unRegisteredAt(account.getUnRegisteredAt())
				.build();
	}
}
