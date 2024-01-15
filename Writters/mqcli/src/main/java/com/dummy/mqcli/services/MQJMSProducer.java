package com.dummy.mqcli.services;

import com.dummy.mqcli.config.MQConfig;
import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;
import lombok.extern.slf4j.Slf4j;

import javax.jms.*;
import java.util.Properties;

@Slf4j
public class MQJMSProducer {
    private MQQueueConnectionFactory connectionFactory;
    private String queueName;

    public MQJMSProducer(Properties properties, String queueName) throws JMSException {
        MQConfig mqConfig = new MQConfig(properties);
        this.queueName = queueName;

        connectionFactory = new MQQueueConnectionFactory();
        connectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        connectionFactory.setHostName(mqConfig.getHost());
        connectionFactory.setPort(mqConfig.getPort());
        connectionFactory.setChannel(mqConfig.getChannel());
        connectionFactory.setQueueManager(mqConfig.getQueueManager());

        /*
        if (mqConfig.getUsername() != null && !mqConfig.getUsername().isEmpty()) {
            connectionFactory.setUserName(mqConfig.getUsername());
        }

        if (mqConfig.getPassword() != null && !mqConfig.getPassword().isEmpty()) {
            connectionFactory.setPassword(mqConfig.getPassword());
        }
         */
    }

    public void sendMessage(String message) throws JMSException {
        try (JMSContext context = connectionFactory.createContext()) {
            Queue queue = context.createQueue(queueName);
            JMSProducer producer = context.createProducer();
            producer.send(queue, message);
            log.info("Mensaje enviado a la cola '" + queueName + "': " + message);
        }
    }

}
