package com.cibertec.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table( name="peliculas")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_pelicula")

    private Integer idPelicula;

    @NotBlank
    private String titulo;

    @NotBlank
    private String genero;

    @NotNull
    private Integer stock;

    
    public Pelicula() {
    }

    public Pelicula(Integer idPelicula, @NotBlank String titulo, @NotBlank String genero, @NotNull Integer stock) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.genero = genero;
        this.stock = stock;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // Getters y setters


    
}
