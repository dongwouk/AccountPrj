package com.zerobase.account.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.zerobase.account.type.TransactionResult;

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
@Entity
public class Transaction extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private com.zerobase.account.type.Transaction transaction;

    @Enumerated(EnumType.STRING)
    private TransactionResult transactionResult;

    @ManyToOne
    private Account account;

    private Long amount;
    private Long balanceSnapshot;

    private String transactionId;
    private LocalDateTime transactedAt;
}
