package com.dummy.mqcli.randomizers;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TransaccionGenerator {

    private static Gson gson;

    // Método para generar un ID de transacción aleatorio
    private static String generateTxid() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(alphanumeric.charAt(random.nextInt(alphanumeric.length())));
        }
        return "TX-" + timestamp + "-" + sb.toString();
    }

    // Método para generar una transacción aleatoria
    public static Transaccion generateRandomTransaccion(String channel) {
        //Generamos unos DATOS aleatorios:
        MensajeJsonGenerator generador = new MensajeJsonGenerator(channel);

        Transaccion transaccion = new Transaccion();
        transaccion.setTxid(generateTxid());
        transaccion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transaccion.setChannel(channel);
        transaccion.setStatus("COMPLETADA");
        transaccion.setType("VENTA");
        transaccion.setData(generador.generarMensajeJson());
        transaccion.setCompensationId(new Random().nextInt(1000));
        return transaccion;
    }

    public static String generateRandomTransaccionJSON(String channel){
        gson = new Gson();
        return gson.toJson(generateRandomTransaccion(channel));
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        String channel = "INTERNET";
        String transaccion = generateRandomTransaccionJSON(channel);
        System.out.println(transaccion);
    }
}


