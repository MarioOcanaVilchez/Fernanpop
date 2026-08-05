package Modelos;

import Controller.GestionAPP;

import java.io.Serializable;
import java.time.LocalDate;

public class Trato implements Comparable<Trato>, Serializable {
    private int id;
    private String tipo;
    private String emailOtroUser;
    private String comentario;
    private LocalDate fecha;
    private int puntuacion;
    private double precio;
    private Producto producto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmailOtroUser() {
        return emailOtroUser;
    }

    public void setEmailOtroUser(String emailOtroUser) {
        this.emailOtroUser = emailOtroUser;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuaccion) {
        this.puntuacion = puntuaccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Trato(int id, String tipo, String emailOtroUser, String comentario, LocalDate fecha, int puntuaccion, double precio, Producto producto) {
        this.id = id;
        this.tipo = tipo;
        this.emailOtroUser = emailOtroUser;
        this.comentario = comentario;
        this.fecha = fecha;
        this.puntuacion = puntuaccion;
        this.precio = precio;
        this.producto = producto;
    }


    @Override
    public String toString() {
        return "Trato{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", emailOtroUser='" + emailOtroUser + '\'' +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                ", puntuaccion=" + puntuacion +
                ", precio=" + precio +
                ", producto=" + producto +
                '}';
    }
    public String historicoVenta(){
        return "****************************************************************\n" +
                "Producto vendido: " + producto.getTitulo() + " " + precio + "€\n" +
                "email del comprador: " + emailOtroUser + "\n" +
                "fecha de compra: " + fecha + "\n" +
                "comentario del comprador: " +(comentario != null?comentario + "\n": "No comentado\n")  +
                "puntuación del comprador: " +(puntuacion != -1? pintaPuntuacion() + "\n": "No puntuado\n") +
                "****************************************************************\n";
    }
    public String historicoCompra(){
        return "****************************************************************\n" +
                "Producto comprado: " + producto.getTitulo() + " " + precio + "€\n" +
                "email del vendedor: " + emailOtroUser + "\n" +
                "fecha de compra: " + fecha + "\n" +
                "comentario: " + (comentario != null?comentario + "\n": "No comentado\n") +
                "puntuación: " + (puntuacion != -1? pintaPuntuacion() + "\n": "No puntuado\n") +
                "****************************************************************\n";
    }
    public String solicitudCompra(GestionAPP fernanpop){
        return "El usuario " + fernanpop.buscaMail(emailOtroUser).getNombre() + "\ncon email " + emailOtroUser +
                "\nsolicita comprar el producto " + producto.getTitulo() + " por " + precio + "€";
    }
    public String pintaPuntuacion(){
        String respuesta = "";
        for (int i = 0; i < puntuacion; i++) {
            respuesta += "*";
        }
        for (int i = puntuacion; i < 5; i++) {
            respuesta += "o";
        }
        return respuesta;
    }

    @Override
    public int compareTo(Trato o) {
        return o.fecha.compareTo(fecha);
    }
}
