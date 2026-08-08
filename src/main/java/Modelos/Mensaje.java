package Modelos;

import java.time.LocalDateTime;

public class Mensaje {
    private long id;
    private String contenido;
    private Usuario usuario;
    private LocalDateTime fecha;

    public Mensaje(long id, String contenido, Usuario usuario, LocalDateTime fecha) {
        this.id = id;
        this.contenido = contenido;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
