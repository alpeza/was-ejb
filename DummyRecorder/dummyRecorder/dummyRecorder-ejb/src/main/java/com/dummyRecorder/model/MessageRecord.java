package com.dummyRecorder.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Clase encargada de parsear un mensaje de entrada de tipo string JSON con un campo llamado
 * "id"  y otro campo llamado "message"
 */
public class MessageRecord {
    private static final Logger logger = LogManager.getLogger(MessageRecord.class);
    private int id;
    private String message;
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    public MessageRecord fromJsonString(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MessageRecord record = mapper.readValue(jsonString, MessageRecord.class);
            logger.info("Objeto MessageRecord creado desde JSON: " + record);
            return record;
        } catch (JsonProcessingException e) {
            logger.error("Error al parsear JSON a MessageRecord: " + e.getMessage());
            return null;
        }
    }

    public String serializeMessage(String jsonString){
        String serialiced_message;
        try {
            //Obtener los campos del mensaje
            String message = fromJsonString(jsonString).getMessage();
            String id = String.valueOf(fromJsonString(jsonString).getId());

            // Obtener la fecha actual y formatearla
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String currentDate = dateFormat.format(new Date());
            //Serialización
            serialiced_message = currentDate + ";" + id + ";" + message;
            return serialiced_message;
        } catch (Exception e) {
            logger.error("Error al tratar de serializar el mensaje : " + e.getMessage());
            return null;
        }
    }


}
