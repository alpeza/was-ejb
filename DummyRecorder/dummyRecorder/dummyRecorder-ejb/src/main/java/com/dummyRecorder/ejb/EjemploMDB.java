package com.dummyRecorder.ejb;

import com.dummyRecorder.model.MessageRecord;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
public class EjemploMDB implements MessageListener{
    private static final Logger logger = LogManager.getLogger(EjemploMDB.class);

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
            logger.info("Llega el mensaje:" + txtMsg.getText());
            //1.- Tratamos de procesar el mensaje que llega por la cola MQ
            MessageRecord messageRecord = new MessageRecord();
            //String serializedMessage = messageRecord.serializeMessage(txtMsg.getText());
            //logger.info("Mensaje procesado: " + serializedMessage);
            //2.- Escribimos el mensaje en base de datos
            insertDescartadoIntoDatabase(txtMsg.getText());
        }catch(Exception e) {
            logger.error(e);
        }
    }

    private void insertDescartadoIntoDatabase(String serializedMessage) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO descartes (content, timestampc) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, serializedMessage);
                statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
                logger.info("Mensaje insertado en la base de datos.");
            }
        } catch (Exception e) {
            logger.error("Error al insertar el mensaje en la base de datos: " + e.getMessage());
        }
    }
}