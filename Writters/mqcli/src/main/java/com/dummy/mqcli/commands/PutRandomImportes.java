package com.dummy.mqcli.commands;

import com.dummy.mqcli.config.ConfigLoader;
import com.dummy.mqcli.randomizers.MensajeJsonGenerator;
import com.dummy.mqcli.services.MQJMSProducer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@CommandLine.Command(name = "putrandomimportes", description = "Escribe N mensajes json aleatorios.")
@Slf4j
public class PutRandomImportes implements Runnable {
    @CommandLine.Option(names = {"--queue"}, required = true, description = "Nombre de la cola mq")
    private String queue;

    @CommandLine.Option(names = {"--number"}, required = true, description = "Número de mensajes a escribir")
    private int total;

    @CommandLine.Option(names = {"--config"}, required = true, description = "Fichero de propiedades de configuración")
    private String configFile;

    @SneakyThrows
    @Override
    public void run() {
        ConfigLoader.validateConfigFile(configFile);
        MQJMSProducer mqjmsProducer = new MQJMSProducer(ConfigLoader.loadConfig(configFile), queue);
        MensajeJsonGenerator generador = new MensajeJsonGenerator();
        for (int i = 0; i < total; i++) {
            String mensajeJson = generador.generarMensajeJson();
            log.info("Tratando de escribir: " + mensajeJson);
            mqjmsProducer.sendMessage(mensajeJson);
        }

    }
}