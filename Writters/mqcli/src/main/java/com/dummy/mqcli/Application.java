package com.dummy.mqcli;

import com.dummy.mqcli.commands.Putjms;


import picocli.CommandLine;
import picocli.CommandLine.Command;


@Command(name = "mqcli",
        description = "Aplicación CLI para leer y escribir mensajes JMS en colas MQ.",
        mixinStandardHelpOptions = true,
        subcommands = {Putjms.class})
public class Application implements Runnable {

    @Override
    public void run() {
        System.out.println("Hola");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Application()).execute(args);
        System.exit(exitCode);
    }

}
