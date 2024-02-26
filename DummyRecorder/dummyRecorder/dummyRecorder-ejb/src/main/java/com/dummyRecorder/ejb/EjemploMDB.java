package com.dummyRecorder.ejb;

import com.dummyRecorder.mbeans.MDBStats;


import com.dummyRecorder.model.TransaccionRecord;
import com.dummyRecorder.negocio.ProcesadorTx;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.sql.Timestamp;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue")
})
@Slf4j
public class EjemploMDB implements MessageListener {

    private String datasourceId = "jdbc/LOCALORA";


    /**
     * Recepción de mensajes de la cola MQ
     * @param msg
     */
    @Override
    public void onMessage(Message msg) {
        TextMessage txtMsg = (TextMessage)msg;
        try {
            log.info("Llega el mensaje:" + txtMsg.getText());
            MDBStats.incrementaMensajes();
            //Tratamos de procesar e insertar el mensaje en base de datos
            ProcesadorTx procesadorTx = new ProcesadorTx(datasourceId);
            procesadorTx.grabaTx(txtMsg.getText());
            MDBStats.incrementaMensajesOK();
        }catch(Exception e) {
            log.error("Error al leer el mensaje que llega a la cola MQ");
            log.error(e.getMessage());
            MDBStats.incrementaMensajesKO();
        }
    }

    /**
     * Insert en la tabla de descartados
     * @param serializedMessage
     */
    private void insertDescartadoIntoDatabase(String serializedMessage) {
        try  {

            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup(datasourceId);
            String insertQuery = "INSERT INTO descartes (content, timestampc) VALUES (?, ?)";
            Connection connection = dataSource.getConnection();


            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, serializedMessage);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();

            log.info("Mensaje: " + serializedMessage + " insertado en la base de datos `descartes`.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("DESCARTADOS: Error al insertar el mensaje en descartados: " + e.getMessage());
        }
    }

}