package com.zerobase.account.util;

import java.util.UUID;

public class AccountNumberGenerator {

    // 계좌 번호 생성 메소드
    public static String generateNewAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}