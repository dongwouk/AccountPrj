package com.zerobase.account.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.zerobase.account.dto.request.AccountCreateRequest;
import com.zerobase.account.dto.request.AccountDeleteRequest;
import com.zerobase.account.dto.response.AccountCreateResponse;
import com.zerobase.account.dto.response.AccountDeleteResponse;
import com.zerobase.account.dto.response.AccountGetResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/account")
    public List<AccountGetResponse> getAccountByUserId(@RequestParam("user_id") Long userId) {
        return accountService.getAccountsByUserId(userId)
                .stream()
                .map(accountDto -> AccountGetResponse.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("/account")
    public AccountCreateResponse createAccount(@RequestBody AccountCreateRequest request) {
        return AccountCreateResponse.fromDto(
                accountService.createAccount(
                        request.getId(),
                        request.getInitialBalance())
        );
    }

    @DeleteMapping("/account")
    public AccountDeleteResponse deleteAccount(@RequestBody AccountDeleteRequest request) {
        return AccountDeleteResponse.fromDto(
                accountService.unregisterAccount(
                        request.getId(),
                        request.getAccountNumber())
        );
    }
}
