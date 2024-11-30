package com.zerobase.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequest {

    @NotNull
    @Min(1)
    private Long id;

    @NotNull
    @Min(0)
    private Long initialBalance;
}

