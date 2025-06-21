package com.cibertec.alquiler_webapp.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DetalleAlquilerId implements Serializable {

    private Integer idAlquiler;
    private Integer idPelicula;

    // Constructor vacío obligatorio
    public DetalleAlquilerId() {}

    public DetalleAlquilerId(Integer idAlquiler, Integer idPelicula) {
        this.idAlquiler = idAlquiler;
        this.idPelicula = idPelicula;
    }

    // Getters y setters
    public Integer getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(Integer idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    // equals() y hashCode() requeridos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleAlquilerId)) return false;
        DetalleAlquilerId that = (DetalleAlquilerId) o;
        return Objects.equals(idAlquiler, that.idAlquiler) &&
               Objects.equals(idPelicula, that.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlquiler, idPelicula);
    }
}
