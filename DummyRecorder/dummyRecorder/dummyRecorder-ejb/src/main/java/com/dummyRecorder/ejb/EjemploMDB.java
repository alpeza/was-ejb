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



import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;

import java.text.SimpleDateFormat;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue")
})
@Slf4j
public class EjemploMDB implements MessageListener{


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
                insertDescartadoIntoDatabase(txtMsg.getText());
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


    /**
     * Realiza el inser en la tabla de canal.
     * @param registro
     */
    public void insertCanalRecordIntoDatabase(CanalRecord registro) {
        try  {
            // Obtener el contexto inicial
            Context ctx = new InitialContext();
            // Buscar el DataSource usando su nombre JNDI
            DataSource dataSource = (DataSource) ctx.lookup(datasourceId);
            // Obtener la conexión del DataSource
            Connection connection = dataSource.getConnection();
            String insertQuery = "INSERT INTO canal (nombre, canal, apellido, timestampc, importe) VALUES (?, ?, ?, ?, ?)";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String timestampStr = registro.getTimestamp();
            Timestamp timestamp = new Timestamp(dateFormat.parse(timestampStr).getTime());

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, registro.getNombre());
            statement.setString(2, registro.getCanal());
            statement.setString(3, registro.getApellido());
            statement.setTimestamp(4, timestamp);
            statement.setDouble(5, registro.getImporte());
            statement.executeUpdate();
            log.info("Registro insertado en la base de datos de canal.");

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error al insertar el registro en la base de datos: " + e.toString());
        }
    }
}