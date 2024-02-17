package com.dummyRecorder.ejb;

import com.dummyRecorder.model.CanalRecord;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.Timestamp;


import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue")
})
@Slf4j
public class EjemploMDB implements MessageListener{


    @Resource(name = "jdbc/LOCALORA")
    private DataSource dataSource;

    /**
     * Recepción de mensajes de la cola MQ
     * @param msg
     */
    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = (TextMessage)msg;
        try {
            log.info("Llega el mensaje:" + txtMsg.getText());
            try {
                //1.- Tratamos de procesar el mensaje que llega por la cola MQ. Este está en formato json
                ObjectMapper mapper = new ObjectMapper();
                CanalRecord canalRecord = mapper.readValue(txtMsg.getText(), CanalRecord.class);
                log.info("Mensaje parseado: " + canalRecord.toString());
                //2.- Tratamos de escribir el mensaje en la base de datos:
                insertCanalRecordIntoDatabase(canalRecord);
            }catch (Exception ex){
                //En caso de que este mensaje no se pueda procesar se añade a descartados.
                log.error("Error al tratar de deserializar el mensaje: " + txtMsg.getText());
                log.error(ex.toString());
                //insertDescartadoIntoDatabase(txtMsg.getText());
            }
        }catch(Exception e) {
            log.error("Error al leer el mensaje que llega a la cola MQ");
            log.error(e.getMessage());
        }
    }

    /**
     * Insert en la tabla de descartados
     * @param serializedMessage
     */
    private void insertDescartadoIntoDatabase(String serializedMessage) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO descartes (content, timestampc) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, serializedMessage);
                statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
                log.info("Mensaje insertado en la base de datos.");
            }
        } catch (Exception e) {
            log.error("Error al insertar el mensaje en la base de datos: " + e.getMessage());
        }
    }


    /**
     * Realiza el inser en la tabla de canal.
     * @param registro
     */
    public void insertCanalRecordIntoDatabase(CanalRecord registro) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO canal (nombre, canal, apellido, timestampc, importe) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, registro.getNombre());
                statement.setString(2, registro.getCanal());
                statement.setString(3, registro.getApellido());
                statement.setTimestamp(4, Timestamp.valueOf(registro.getTimestamp()));
                statement.setDouble(5, registro.getImporte());
                statement.executeUpdate();
                log.info("Registro insertado en la base de datos.");
            }
        } catch (Exception e) {
            log.error("Error al insertar el registro en la base de datos: " + e.getMessage());
        }
    }
}