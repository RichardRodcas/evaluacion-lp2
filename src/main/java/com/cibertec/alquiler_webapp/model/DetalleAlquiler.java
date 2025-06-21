package com.cibertec.alquiler_webapp.model;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "detalle_alquiler")
public class DetalleAlquiler {

    @EmbeddedId
    private DetalleAlquilerId id = new DetalleAlquilerId();

    @ManyToOne
    @MapsId("idAlquiler")
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @ManyToOne
    @MapsId("idPelicula")
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @NotNull
    private Integer cantidad;

    // 🔢 Subtotal automático
    public BigDecimal getSubtotal() {
        if (pelicula == null || pelicula.getPrecio() == null || cantidad == null) {
            return BigDecimal.ZERO;
        }
        return pelicula.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }

    // Getters y Setters
    public DetalleAlquilerId getId() {
        return id;
    }

    public void setId(DetalleAlquilerId id) {
        this.id = id;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}