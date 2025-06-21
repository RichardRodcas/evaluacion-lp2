package com.cibertec.alquiler_webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.alquiler_webapp.model.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    // Aquí puedes agregar métodos personalizados si los necesitas
}
