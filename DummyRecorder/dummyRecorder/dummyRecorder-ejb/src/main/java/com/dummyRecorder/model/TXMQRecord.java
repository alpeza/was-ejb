package com.dummyRecorder.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Esta clase almacena la información que llega por las colas MQ que escribe el framework
 * de arquitectura.
 */
@Data
public class TXMQRecord {
    @JsonProperty("txid")
    private String txid;

    @JsonProperty("path")
    private String path;

    @JsonProperty("method")
    private String method;

    @JsonProperty("requestParams")
    private String requestParams;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("state")
    private String state;

    @JsonProperty("tier")
    private int tier;

    @JsonProperty("type")
    private String type;

    @JsonProperty("createdAt")
    private String createdAt;

}
