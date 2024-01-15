package com.dummy.mqcli.randomizers;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MensajeJsonGenerator {

    private static final String[] NOMBRES_USUARIOS = {"Juan", "Maria", "Pedro", "Laura", "Carlos", "Ana", "Elena", "Sergio", "Isabel", "Alejandro",
            "Carmen", "Javier", "Rosa", "Miguel", "Beatriz", "Francisco", "Luis", "Eva", "Pablo", "Silvia",
            "Fernando", "Victoria", "Diego", "Monica", "Raul", "Natalia", "Alberto", "Sara", "Antonio", "Patricia",
            "Jose", "Raquel", "Manuel", "Alicia", "Adrian", "Eva", "Daniel", "Lorena", "Jesus", "Cristina",
            "Roberto", "Julia", "Ruben", "Paula", "Oscar", "Celia", "Gonzalo", "Nerea", "Guillermo", "Marina"};

    private static final String[] APELLIDOS = {"Gomez", "Fernandez", "Rodriguez", "Lopez", "Martinez", "Sanchez", "Perez", "Gonzalez", "Ruiz", "Serrano",
            "Jimenez", "Hernandez", "Diaz", "Torres", "Santos", "Vega", "Castro", "Ortega", "Cruz", "Flores",
            "Nunez", "Molina", "Soto", "Herrera", "Blanco", "Ramirez", "Medina", "Iglesias", "Delgado", "Navarro",
            "Gutierrez", "Garcia", "Moreno", "Castillo", "Fuentes", "Romero", "Morales", "Aguilar", "Cabrera", "Vargas",
            "Reyes", "Ramos", "Vazquez", "Cano", "Mendez", "Arias", "Suarez", "Collado", "Peralta", "Lara"};

    private static final double MAX_IMPORTE = 100.0;
    private static final double MIN_IMPORTE = 1.0;

    private Random random;
    private Gson gson;
    private SimpleDateFormat dateFormat;
    private DecimalFormat decimalFormat;

    public MensajeJsonGenerator() {
        random = new Random();
        gson = new Gson();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        decimalFormat = new DecimalFormat("#.##");
    }

    public String generarMensajeJson() {
        String nombreUsuario = obtenerNombreUsuarioAleatorio();
        String apellidoUsuario = obtenerApellidoUsuarioAleatorio();
        String timestamp = obtenerTimestampActual();
        double importe = obtenerImporteAleatorio();

        MensajeJson mensaje = new MensajeJson(nombreUsuario, apellidoUsuario, timestamp, importe);

        return gson.toJson(mensaje);
    }

    private String obtenerNombreUsuarioAleatorio() {
        return NOMBRES_USUARIOS[random.nextInt(NOMBRES_USUARIOS.length)];
    }

    private String obtenerApellidoUsuarioAleatorio() {
        return APELLIDOS[random.nextInt(APELLIDOS.length)];
    }

    private String obtenerTimestampActual() {
        return dateFormat.format(new Date());
    }

    private double obtenerImporteAleatorio() {
        double importe = MIN_IMPORTE + (MAX_IMPORTE - MIN_IMPORTE) * random.nextDouble();
        String importeFormateado = decimalFormat.format(importe).replace(",", ".");
        return Double.parseDouble(importeFormateado);
    }

    private static class MensajeJson {
        private String nombre;

        private String apellido;
        private String timestamp;
        private double importe;

        public MensajeJson(String nombre, String apellido, String timestamp, double importe) {
            this.nombre = nombre;
            this.timestamp = timestamp;
            this.importe = importe;
            this.apellido = apellido;
        }
    }
    public static void main(String[] args) {
        MensajeJsonGenerator generador = new MensajeJsonGenerator();

        for (int i = 0; i < 5; i++) {
            String mensajeJson = generador.generarMensajeJson();
            System.out.println(mensajeJson);
        }
    }

}
