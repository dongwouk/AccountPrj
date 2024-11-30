package com.zerobase.account.dto.response;

import java.time.LocalDateTime;

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
public class AccountCreateResponse {


    private Long userId;
    private String accountNumber;
    private LocalDateTime registeredAt;

    public static AccountCreateResponse fromDto(AccountResponse accountResponse) {
        return AccountCreateResponse.builder()
                .userId(accountResponse.getUserId())
                .accountNumber(accountResponse.getAccountNumber())
                .registeredAt(accountResponse.getRegisteredAt())
                .build();
    }
};

