package com.wallet.mono.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer accountId;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String accountCurrency;

    private Double accountBalance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
