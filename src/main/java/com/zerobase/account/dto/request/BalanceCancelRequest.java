package com.zerobase.account.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceCancelRequest {

    @NotBlank
    private String transactionId;

    @NotBlank
    @Size(min = 10, max = 10)
    private String accountNumber;

    @NotNull
    @Min(10)
    @Max(1_000_000_000)
    private Long amount;
};
