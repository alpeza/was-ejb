package com.dummyRecorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransaccionRecord {

    /**
     * Mapea la información de la transacción que llega por MQ a la clase transacción
     * que se acaba por insertar en base de datos.
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    public static Transaccion parseJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TXMQRecord txmqRecord = objectMapper.readValue(json, TXMQRecord.class);

        Transaccion transaccion = new Transaccion();
        transaccion.setTxid(txmqRecord.getTxid());
        transaccion.setChannel(txmqRecord.getChannel());
        transaccion.setType(txmqRecord.getType());
        transaccion.setPath(txmqRecord.getPath());
        transaccion.setMethod(txmqRecord.getMethod());
        transaccion.setRequestParams(txmqRecord.getRequestParams());
        transaccion.setState(txmqRecord.getState());
        transaccion.setTier(txmqRecord.getTier());
        transaccion.setCreatedAt(txmqRecord.getCreatedAt());

        return transaccion;
    }

    public static String generateInsertStatement(Transaccion transaccion) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO Transacciones (txid, createdAt, channel, type, data, path, method, requestParams, state, tier) VALUES (")
                .append("'").append(transaccion.getTxid()).append("', ")
                .append("'").append(transaccion.getCreatedAt()).append("', ")
                .append("'").append(transaccion.getChannel()).append("', ")
                .append("'").append(transaccion.getType()).append("', ")
                .append("'").append(transaccion.getData()).append("', ")
                .append("'").append(transaccion.getPath()).append("', ")
                .append("'").append(transaccion.getMethod()).append("', ")
                .append("'").append(transaccion.getRequestParams()).append("', ")
                .append("'").append(transaccion.getState()).append("', ")
                .append(transaccion.getTier())
                .append(")");
        return sb.toString();
    }


}
