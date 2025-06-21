package com.cibertec.alquiler_webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.alquiler_webapp.model.Alquiler;

public interface AlquilerRepository

extends JpaRepository<Alquiler, Integer> {
    
    // Aquí puedes agregar métodos personalizados si es necesario
} 
