package com.wallet.mono.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer walletId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String currency;

    private Double balance;

    private Double limitValue;

    @Column(nullable = false)
    private Boolean isExcluded;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}