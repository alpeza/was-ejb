package com.dummyRecorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Transaccion {
    @JsonProperty("txid")
    private String txid;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private String data;
    @JsonProperty("compensationId")
    private int compensationId;
}