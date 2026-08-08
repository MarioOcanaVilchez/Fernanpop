package Modelos;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chat {
    private long id;
    private Usuario [] usuarios;
    private ArrayList<Mensaje> mensajes;
    private String nombre;
    private LocalDateTime fechaUltimoMensaje;
    private String ultimoMensaje;

    public Chat(long id, Usuario[] usuarios, String nombre, LocalDateTime fechaUltimoMensaje, String ultimoMensaje) {
        this.id = id;
        this.usuarios = usuarios;
        this.nombre = nombre;
        this.fechaUltimoMensaje = fechaUltimoMensaje;
        this.ultimoMensaje = ultimoMensaje;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuario[] usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaUltimoMensaje() {
        return fechaUltimoMensaje;
    }

    public void setFechaUltimoMensaje(LocalDateTime fechaUltimoMensaje) {
        this.fechaUltimoMensaje = fechaUltimoMensaje;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }
}
