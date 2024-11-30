package com.zerobase.account.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDeleteResponse {

    private Long userId;
    private String accountNumber;
    private LocalDateTime unRegisteredAt;

    public static AccountDeleteResponse fromDto(AccountResponse accountResponse) {
        return AccountDeleteResponse.builder()
                .userId(accountResponse.getUserId())
                .accountNumber(accountResponse.getAccountNumber())
                .unRegisteredAt(accountResponse.getUnRegisteredAt())
                .build();
    }
};


