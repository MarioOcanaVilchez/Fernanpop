package Modelos;

import java.io.Serializable;

public class Producto implements Serializable {
    private long id;
    private String titulo;
    private String descripcion;
    private String estado;
    private double precio;
    private String nombreImagen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    /*public Producto(long id, String titulo, String descripcion, double precio, String estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
    }*/

    public Producto(long id, String titulo, String descripcion, double precio,String estado, String nombreImagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.precio = precio;
        this.nombreImagen = nombreImagen;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
    public String pintarProductosVendedor(){
        return "* " + titulo + " por " + precio + "€";
    }
    public String pintaProductoCompra(Usuario vendedor) {
        // Formateamos la salida para que sea fácil de escanear en un listado
        String info = String.format(
                "=========================================\n" +
                        "  [P%d] %s (%.2f€) | Estado: %s\n" +
                        "=========================================\n" +
                        "  Descripción: %s\n" +
                        "  Vendedor: %s\n",
                id, titulo, precio, estado,
                descripcion, vendedor.getEmail()
        );
        return info;
    }
}
