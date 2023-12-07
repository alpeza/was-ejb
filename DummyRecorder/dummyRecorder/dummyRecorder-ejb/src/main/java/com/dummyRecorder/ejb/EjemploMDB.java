package com.dummyRecorder.ejb;

import com.dummyRecorder.model.MessageRecord;
import com.dummyRecorder.utils.AppendStringToFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "MyQueue")
})
public class EjemploMDB implements MessageListener{

    private static final Logger logger = LogManager.getLogger(EjemploMDB.class);

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
            String serializedMessage = messageRecord.serializeMessage(txtMsg.getText());
            logger.info("Mensaje procesado: " + serializedMessage);
            //2.- Escribimos el mensaje en un fichero
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String currentDate = dateFormat.format(new Date());
            String  filePath = "/tmp/" + currentDate;
            AppendStringToFile.appendToFile(filePath, serializedMessage);
        }catch(Exception e) {
            logger.error(e);
        }
    }
}