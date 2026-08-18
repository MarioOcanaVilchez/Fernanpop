package Utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Utilidades {
    public static void limpiarPantalla(){
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
    public static void pulsaParaContinuar(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pulsa una tecla para continuar...");
        scanner.nextLine();
    }
    public static void AnimacionIniciando(){
        limpiarPantalla();
        for (int i = 0; i < 3; i++) {
            System.out.print("Iniciando");
            for (int j = 0; j < 4; j++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (j != 3) System.out.print(".");
            }
            limpiarPantalla();
        }
    }
    public static void cargando(String mensaje){
        limpiarPantalla();
        int veces = (int) (Math.random() * 3 + 1);
        for (int i = 0; i < veces; i++) {
            System.out.print(mensaje);
            for (int j = 0; j < 4; j++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (j != 3) System.out.print(".");
            }
            limpiarPantalla();
        }
    }
    public static String pasarFechaBBDD(LocalDate fecha){
        return fecha.getYear() + "/" + (fecha.getMonthValue() < 10 ? "0" + fecha.getMonthValue() : fecha.getMonthValue() ) + "/" + fecha.getDayOfMonth();
    }
    public static LocalDate pasarFechaLocaldate(String fecha){
        return LocalDate.of(Integer.parseInt(fecha.substring(0,4)),Integer.parseInt(fecha.substring(5,7)),Integer.parseInt(fecha.substring(8)));
    }
    public static String pasarFechaHoraBBDD(LocalDateTime fecha){
        return fecha.getYear() + "/" + (fecha.getMonthValue() < 10 ? "0" + fecha.getMonthValue() : fecha.getMonthValue() ) + "/" + fecha.getDayOfMonth() + " " + fecha.getHour() + ":" + fecha.getMinute() + ":" + fecha.getSecond();
    }
    public static LocalDateTime pasarFechaHoraLocaldate(String fecha){
        return LocalDateTime.of(Integer.parseInt(fecha.substring(0,4)),Integer.parseInt(fecha.substring(5,7)),Integer.parseInt(fecha.substring(8,10)), Integer.parseInt(fecha.substring(11,13)), Integer.parseInt(fecha.substring(14,16)),Integer.parseInt(fecha.substring(17,19)));
    }
}
