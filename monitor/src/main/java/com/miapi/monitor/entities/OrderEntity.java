package com.miapi.monitor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Data
@AllArgsConstructor
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
