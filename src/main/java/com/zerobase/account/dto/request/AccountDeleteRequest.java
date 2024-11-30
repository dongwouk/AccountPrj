package com.zerobase.account.dto.request;

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
@NoArgsConstructor
@AllArgsConstructor
public class AccountDeleteRequest {

    @NotNull
    @Min(1)
    private Long id;

    @NotBlank
    @Size(min = 10, max = 10)
    private String accountNumber;
};
