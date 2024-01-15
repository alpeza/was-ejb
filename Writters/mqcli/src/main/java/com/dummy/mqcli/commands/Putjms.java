package com.dummy.mqcli.commands;


import com.dummy.mqcli.config.ConfigLoader;
import com.dummy.mqcli.services.MQJMSProducer;
import lombok.SneakyThrows;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "putjms", description = "Escribe un mensaje JMS en una cola MQ.")
public class Putjms implements Runnable {

    @Option(names = {"--queue"}, required = true, description = "Nombre de la cola mq")
    private String queue;

    @Option(names = {"--message"}, required = true, description = "Mensaje a escribir en la cola mq")
    private String message;

    @Option(names = {"--config"}, required = true, description = "Fichero de propiedades de configuración")
    private String configFile;

    @SneakyThrows
    @Override
    public void run() {
        ConfigLoader.validateConfigFile(configFile);
        MQJMSProducer mqjmsProducer = new MQJMSProducer(ConfigLoader.loadConfig(configFile), queue);
        mqjmsProducer.sendMessage(message);
    }
}
