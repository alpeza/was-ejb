package com.miapi.monitor.ARCH.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.miapi.monitor.ARCH.common.Literales;
import lombok.Data;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Clase que contiene la información de la transacción que se almacenará en la
 * base de datos.
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
    private int tier=1;

    @JsonProperty("type")
    private String type;

    @JsonProperty("createdAt")
    private String createdAt;

    public TXMQRecord(){
        this.generateUniqueIdentifier();
        this.state  = Literales.INIT;
        this.type = Literales.TIPO_TRANSACCION_INFORMATIVA;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        this.createdAt = dateFormat.format(new Date());
    }

    /**
     * Genera una clave única para la transacción
     * @return
     */
    private void generateUniqueIdentifier() {
        long timestamp = System.currentTimeMillis() % 1000000;
        String randomValue = UUID.randomUUID().toString().substring(0, 10).replace("-","");
        this.txid = "TXID-" + timestamp + "-" + randomValue;
    }

    public String getASJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


}
