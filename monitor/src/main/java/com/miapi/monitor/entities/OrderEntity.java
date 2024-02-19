package com.miapi.monitor.entities;

import lombok.Data;

import javax.persistence.*;


@Data
public class OrderEntity {

    @Column(name = "FROM")
    private String fromid;

    @Column(name = "TO")
    private String toid;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "CURRENCY")
    private String currency;

}
