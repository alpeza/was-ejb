package com.dummy.mqcli.randomizers;

import lombok.Data;

import java.sql.Timestamp;

@Data
class Transaccion {
    private String txid;
    private Timestamp createdAt;
    private String channel;
    private String status;
    private String type;
    private String data;
    private int compensationId;
}