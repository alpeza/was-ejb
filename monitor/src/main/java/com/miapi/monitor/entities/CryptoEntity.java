package com.miapi.monitor.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "crypto")
@Data
public class CryptoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRYPTO_ID")
    private Long cryptoId;

    @Column(name = "CRYPTO")
    private String crypto;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "PRICE")
    private Double price;

}
