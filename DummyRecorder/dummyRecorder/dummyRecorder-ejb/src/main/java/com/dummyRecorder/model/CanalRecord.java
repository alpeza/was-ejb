package com.dummyRecorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class CanalRecord {
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("canal")
    private String canal;

    @JsonProperty("apellido")
    private String apellido;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("importe")
    private double importe;

}
