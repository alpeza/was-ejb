package com.dummyRecorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;


@Data
public class Transaccion {
    @JsonProperty("txid")
    private String txid;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("type")
    private String type;
    @JsonProperty("data")
    private String data;
    @JsonProperty("path")
    private String path;
    @JsonProperty("method")
    private String method;
    @JsonProperty("requestParams")
    private String requestParams;
    @JsonProperty("state")
    private String state;
    @JsonProperty("tier")
    private int tier;

}