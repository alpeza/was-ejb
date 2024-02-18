package com.dummyRecorder.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransaccionRecord {

    public static Transaccion parseJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Transaccion.class);
    }

    public static String generateInsertStatement(Transaccion transaccion) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO transacciones (txid, channel, status, type, data, compensation_id) VALUES (")
                .append("'").append(transaccion.getTxid()).append("', ")
                .append("'").append(transaccion.getChannel()).append("', ")
                .append("'").append(transaccion.getStatus()).append("', ")
                .append("'").append(transaccion.getType()).append("', ")
                .append("'").append(transaccion.getData()).append("', ")
                .append(transaccion.getCompensationId()).append(")");
        return sb.toString();
    }



    public static void main(String[] args) {
        String json = "{\"txid\":\"TX-20240218142822-57VO43M0IL\",\"createdAt\":\"Feb 18, 2024 2:28:22 PM\",\"channel\":\"tienda\",\"status\":\"COMPLETADA\",\"type\":\"VENTA\",\"data\":\"{\\\"nombre\\\":\\\"Roberto\\\",\\\"canal\\\":\\\"tienda\\\",\\\"apellido\\\":\\\"Cabrera\\\",\\\"timestamp\\\":\\\"2024-02-18T14:28:22.269+0100\\\",\\\"importe\\\":60.77}\"}";

        try {
            Transaccion transaccion = parseJson(json);
            System.out.println(generateInsertStatement(transaccion));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
