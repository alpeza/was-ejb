package com.dummy.mqcli.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.Properties;

@Data
@Slf4j
public class MQConfig {
    private String host;
    private int port;
    private String channel;
    private String queueManager;
    private String username;
    private String password;

    private String getPropertyOrThrow(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            log.error("La propiedad '" + key + "' no está informada.");
            throw new IllegalArgumentException("La propiedad '" + key + "' no está informada.");
        }
        return value;
    }

    public MQConfig(Properties properties){
        host = getPropertyOrThrow(properties, "mq.host");
        port = Integer.parseInt(getPropertyOrThrow(properties, "mq.port"));
        channel = getPropertyOrThrow(properties, "mq.channel");
        queueManager = getPropertyOrThrow(properties, "mq.queueManager");
        username = properties.getProperty("mq.username");
        password = properties.getProperty("mq.password");

        log.info("Configuración MQ Host: " + host + ":" + Integer.toString(port) + " Channel:" + channel + " MQG:" + queueManager + " username:" + username + " password:" + password);
    }

}
