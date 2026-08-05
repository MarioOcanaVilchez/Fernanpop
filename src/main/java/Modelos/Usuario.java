package Modelos;

import Controller.GestionAPP;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Usuario implements Serializable {
    private int id;
    private String email;
    private String nombre;
    private String apel;
    private String clave;
    private int movil;
    private boolean admin;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApel() {
        return apel;
    }

    public void setApel(String apel) {
        this.apel = apel;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getMovil() {
        return movil;
    }

    public void setMovil(int movil) {
        this.movil = movil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Usuario(int id, String email, String nombre, String apel, String clave) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apel = apel;
        this.clave = clave;
    }
    public Usuario(int id, String email, String nombre, String apel, String clave,boolean admin) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apel = apel;
        this.clave = clave;
        this.admin = admin;
    }

    public Usuario(int id, String email, String nombre, String apel, int movil) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apel = apel;
        this.movil = movil;
    }

    public Usuario(String email, String nombre, String apel, String clave) {
        this.email = email;
        this.nombre = nombre;
        this.apel = apel;
        this.clave = clave;
    }

    public Usuario(int id, String email, String nombre, String apel, String clave, int movil, boolean admin) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apel = apel;
        this.clave = clave;
        this.movil = movil;
        this.admin = admin;
    }
    public double notaMedia(ArrayList<Trato> historicoVentas){
        int cont = 0;
        double media = 0;
        for (Trato t : historicoVentas){
            if (t.getPuntuacion() != 0 ){
                cont++;
                media += t.getPuntuacion();
            }
        }
        if (media == 0) return -1;
        return media / cont;
    }
    public String pintaMovil(){
        String telefono = String.valueOf(movil);
        return telefono.substring(0,3) + " " + telefono.substring(3,5) + " " + telefono.substring(5,7) + " " + telefono.substring(7);
    }
    //toString Provisional
    //En el futuro se mejorara

    public String toString(GestionAPP fernanpop) {
        ArrayList<Trato> historicoCompras = fernanpop.getHistoricoCompras();
        ArrayList<Trato> historicoVentas = fernanpop.getHistoricoVentas();
        String info = String.format(
                "**************************************************\n" +
                        "** PERFIL DE USUARIO \n" +
                        "**************************************************\n" +
                        "  Email: %s\n" +
                        "  Nombre: %s\n",
                email, nombre
        );
        info += "  Apellidos: " + (apel != null ? apel : "No introducidos") + "\n";
        info += "  Teléfono: " + (movil != 0 ? pintaMovil() : "No introducido") + "\n";
        info += "Compras realizadas: " + historicoCompras.size() + "\n";
        info += "Ventas realizadas: " + (!historicoVentas.isEmpty() ? historicoVentas.size() + "\n" +
        "Valoración media por otros usuarios: " + (notaMedia(historicoVentas) != -1 ? notaMedia(historicoVentas) + "/5\n" : "no valorado") : "0\n");
        /*info += "\n--- PRODUCTOS EN VENTA ---\n";
        if (!hayProductosEnVenta()) {
            info += "  (Ningún producto activo en venta)\n";
        } else {
            info += pintaProductos(1);
        }
        info += "--------------------------------------------------";*/
        return info;
    }


}
