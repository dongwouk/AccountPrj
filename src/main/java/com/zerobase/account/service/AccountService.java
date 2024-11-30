package com.zerobase.account.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.zerobase.account.validator.AccountValidator;
import com.zerobase.account.util.AccountNumberGenerator;
import org.springframework.stereotype.Service;

import com.zerobase.account.domain.Account;
import com.zerobase.account.domain.AccountUser;
import com.zerobase.account.dto.response.AccountResponse;
import com.zerobase.account.exception.AccountException;
import com.zerobase.account.repository.AccountRepository;
import com.zerobase.account.repository.AccountUserRepository;
import com.zerobase.account.type.AccountStatus;
import com.zerobase.account.type.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;
    private final AccountValidator accountValidator;

    /**
     * 사용자 계좌를 생성하는 메소드.
     *
     * @param userId         사용자 ID
     * @param initialBalance 초기 잔액
     * @return 생성된 계좌의 AccountDto
     */
    @Transactional
    public AccountResponse createAccount(Long userId, Long initialBalance) {
        // 계좌 사용자 정보 가져오기
        AccountUser accountUser = getAccountUser(userId);

        // 계좌 생성 유효성 검사 (유효하지 않으면 예외 발생)
        accountValidator.validateCreateAccount(accountUser);

        // 새로운 계좌 생성 및 저장 후 DTO 반환
        return AccountResponse.fromEntity(accountRepository.save(
                Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(AccountStatus.IN_USE)
                        .accountNumber(AccountNumberGenerator.generateNewAccountNumber()) // 고유한 계좌 번호 생성
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build()
        ));
    }

    /**
     * 사용자 정보를 가져오는 메소드.
     *
     * @param userId 사용자 ID
     * @return AccountUser 객체
     * @throws AccountException 사용자 정보가 없을 때 발생
     */
    private AccountUser getAccountUser(Long userId) {
        // 사용자 ID로 사용자 정보 조회. 없으면 예외 발생
        return accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 ID로 해당 사용자의 모든 계좌 정보를 가져오는 메소드.
     *
     * @param userId 사용자 ID
     * @return AccountDto 리스트
     */
    @Transactional(readOnly = true)  // 읽기 전용 트랜잭션으로 설정 (성능 최적화)
    public List<AccountResponse> getAccountsByUserId(Long userId) {
        // 사용자 정보 가져오기
        AccountUser accountUser = getAccountUser(userId);

        // 사용자 계좌 목록 조회 및 DTO로 변환 후 반환
        List<Account> accounts = accountRepository.findByAccountUser(accountUser);
        return accounts.stream()
                .map(AccountResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 계좌 해지 메소드.
     *
     * @param userId        사용자 ID
     * @param accountNumber 해지할 계좌 번호
     * @return 해지된 계좌의 AccountDto
     */
    @Transactional
    public AccountResponse unregisterAccount(Long userId, String accountNumber) {
        // 사용자 정보 가져오기
        AccountUser accountUser = getAccountUser(userId);

        // 계좌 번호로 계좌 정보 조회 (없으면 예외 발생)
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 계좌 삭제 유효성 검사 (예: 잔액이 남아 있거나 다른 사용자가 아닌지 확인)
        accountValidator.validateDeleteAccount(accountUser, account);

        // 계좌 상태를 UNREGISTERED로 변경 및 해지 시간 기록
        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        // 변경된 계좌 정보 저장 후 DTO 반환
        return AccountResponse.fromEntity(accountRepository.save(account));
    }
}
