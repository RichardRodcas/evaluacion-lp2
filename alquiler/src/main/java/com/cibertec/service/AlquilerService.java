package com.cibertec.service;

import com.cibertec.model.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class AlquilerService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("BD2_ApellidoPU");

    public List<Cliente> obtenerClientes() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Cliente", Cliente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pelicula> obtenerPeliculas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Pelicula", Pelicula.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void registrarAlquiler(Cliente cliente, Map<Pelicula, Integer> seleccion, BigDecimal total) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(cliente);
            alquiler.setFecha(LocalDate.now());
            alquiler.setTotal(total);
            alquiler.setEstado(EstadoAlquiler.ACTIVO);

            em.persist(alquiler);
            em.flush();

            Integer idAlquiler = alquiler.getIdAlquiler();
            System.out.println("ID Alquiler generado: " + idAlquiler);
            if (idAlquiler == null) {
                throw new IllegalStateException("No se pudo obtener el id del alquiler");
            }

            for (Map.Entry<Pelicula, Integer> entry : seleccion.entrySet()) {
                Pelicula peliculaDetached = entry.getKey();
                Integer cantidad = entry.getValue();

                Pelicula pelicula = em.find(Pelicula.class, peliculaDetached.getIdPelicula());
                if (pelicula == null) {
                    throw new IllegalArgumentException("La película no existe en la base de datos");
                }

                DetalleAlquiler detalle = new DetalleAlquiler();

                DetalleAlquilerId detalleId = new DetalleAlquilerId();
                detalleId.setIdAlquiler(idAlquiler);
                detalleId.setIdPelicula(pelicula.getIdPelicula());

                detalle.setId(detalleId);
                detalle.setAlquiler(alquiler);
                detalle.setPelicula(pelicula);
                detalle.setCantidad(cantidad);

                em.persist(detalle);

                // Actualiza stock
                pelicula.setStock(pelicula.getStock() - cantidad);
                em.merge(pelicula);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
