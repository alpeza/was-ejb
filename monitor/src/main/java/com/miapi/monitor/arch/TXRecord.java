package com.miapi.monitor.arch;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

/**
 * Clase que contiene la información de la transacción que se almacenará en la
 * base de datos.
 */
@Data
public class TXRecord {

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
    public TXRecord(){
        this.generateUniqueIdentifier();
        this.state  = Literales.INIT;
    }

    /**
     * Genera una clave única para la transacción
     * @return
     */
    private void generateUniqueIdentifier() {
        long timestamp = System.currentTimeMillis() % 1000000;
        String randomValue = UUID.randomUUID().toString().substring(0, 11);
        this.txid = "TXID-" + timestamp + "-" + randomValue;
    }


}
