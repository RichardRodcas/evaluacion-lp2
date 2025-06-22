package com.cibertec.alquiler_webapp.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.cibertec.alquiler_webapp.model.*;
import com.cibertec.alquiler_webapp.repository.*;

@Controller
//@RequestMapping("/alquiler")
public class AlquilerController {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

    public String getFechaFormateada(LocalDate fecha) {
        return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "—";
    }

    @GetMapping("/alquileres")
    public String mostrarLista(Model model) {
    model.addAttribute("alquileres", alquilerRepository.findAll());
    return "alquiler-listar"; 
    }

    @GetMapping("/alquiler/nuevo")
    public String nuevoAlquiler(Model model) {
    Alquiler alquiler = new Alquiler();
    alquiler.getDetalles().add(new DetalleAlquiler()); // 👈 importante para evitar error en la vista

    model.addAttribute("alquiler", alquiler);
    model.addAttribute("clientes", clienteRepository.findAll());
    model.addAttribute("peliculas", peliculaRepository.findAll());
    return "alquiler-nuevo";
    }

    @PostMapping("/alquiler/guardar")
    public String guardarAlquiler(@ModelAttribute Alquiler alquiler, Model model) {
    List<String> erroresStock = new ArrayList<>();

    // Validación de stock  y mensaje de error
    for (DetalleAlquiler detalle : alquiler.getDetalles()) {
        Integer cantidad = detalle.getCantidad();
        Pelicula peliInput = detalle.getPelicula();

        Pelicula peliGestionada = peliculaRepository.findById(peliInput.getIdPelicula()).orElse(null);

        if (peliGestionada == null) {
            erroresStock.add("❌ La película seleccionada no existe.");
        } else if (cantidad == null || cantidad <= 0) {
            erroresStock.add("❌ Debes ingresar una cantidad válida para: " + peliGestionada.getTitulo());
        } else if (alquiler.getIdAlquiler() == null && peliGestionada.getStock() < cantidad) {
            erroresStock.add("❌ Stock insuficiente para: " + peliGestionada.getTitulo() +
                             " (disponible: " + peliGestionada.getStock() + ", solicitado: " + cantidad + ")");
        }

        detalle.setPelicula(peliGestionada);
    }

    if (!erroresStock.isEmpty()) {
        model.addAttribute("erroresStock", erroresStock);
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return alquiler.getIdAlquiler() == null ? "alquiler-nuevo" : "alquiler-editar";
    }

    if (alquiler.getFecha() == null) {
        alquiler.setFecha(LocalDate.now());
    }

    
    Alquiler alquilerOriginal = null;
    if (alquiler.getIdAlquiler() != null) {
        alquilerOriginal = alquilerRepository.findById(alquiler.getIdAlquiler()).orElse(null);
    }

    BigDecimal total = BigDecimal.ZERO;

    for (DetalleAlquiler detalle : alquiler.getDetalles()) {
        detalle.setAlquiler(alquiler);
        Pelicula peli = detalle.getPelicula();
        Integer cantidad = detalle.getCantidad();

        
        if (alquilerOriginal != null &&
            alquilerOriginal.getEstado() == EstadoAlquiler.ACTIVO &&
            alquiler.getEstado() == EstadoAlquiler.DEVUELTO) {
            peli.setStock(peli.getStock() + cantidad);
            peliculaRepository.save(peli);
        }

       
        if (alquilerOriginal == null && alquiler.getEstado() == EstadoAlquiler.ACTIVO) {
            peli.setStock(peli.getStock() - cantidad);
            peliculaRepository.save(peli);
        }

        // Cálculo del total
        BigDecimal precio = peli.getPrecio();
        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

        if (alquiler.getEstado() == EstadoAlquiler.RETRASADO) {
            BigDecimal penalidad = BigDecimal.valueOf(3).multiply(BigDecimal.valueOf(cantidad));
            subtotal = subtotal.add(penalidad);
        }

        total = total.add(subtotal);
    }

    alquiler.setTotal(total);
    alquilerRepository.save(alquiler);
    return "redirect:/alquileres";
    }
    @GetMapping("alquiler/editar/{id}")
        public String editarAlquiler(@PathVariable Integer id, Model model) {
        Optional<Alquiler> opt = alquilerRepository.findById(id);
        if (opt.isPresent()) {
        Alquiler alquiler = opt.get();
        if (alquiler.getDetalles().isEmpty()) {
            alquiler.getDetalles().add(new DetalleAlquiler());
        }
        model.addAttribute("alquiler", alquiler);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "alquiler-editar";
        }
        return "redirect:/alquileres";
    }

   @GetMapping("/alquiler/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable Integer id) {
    Optional<Alquiler> opt = alquilerRepository.findById(id);

    if (opt.isPresent()) {
        Alquiler alquiler = opt.get();

        // Reponer stock solo si el alquiler estaba ACTIVO o RETRASADO
        if (alquiler.getEstado() == EstadoAlquiler.ACTIVO || alquiler.getEstado() == EstadoAlquiler.RETRASADO) {
            for (DetalleAlquiler detalle : alquiler.getDetalles()) {
                Pelicula pelicula = detalle.getPelicula();
                if (pelicula != null) {
                    pelicula.setStock(pelicula.getStock() + detalle.getCantidad());
                    peliculaRepository.save(pelicula);
                }
            }
        }

        alquilerRepository.deleteById(id);
    }

    return "redirect:/alquileres";
    }

    
    @GetMapping("/alquiler/devolver/{id}")
        public String devolverAlquiler(@PathVariable Integer id) {
        Optional<Alquiler> opt = alquilerRepository.findById(id);

        if (opt.isPresent()) {
        Alquiler alquiler = opt.get();

        if (alquiler.getEstado() == EstadoAlquiler.ACTIVO || alquiler.getEstado() == EstadoAlquiler.RETRASADO) {
            alquiler.setEstado(EstadoAlquiler.DEVUELTO);

            for (DetalleAlquiler detalle : alquiler.getDetalles()) {
                Pelicula pelicula = detalle.getPelicula();
                if (pelicula != null) {
                    pelicula.setStock(pelicula.getStock() + detalle.getCantidad());
                    peliculaRepository.save(pelicula);
                }
            }

            alquilerRepository.save(alquiler);
            }
        }

        return "redirect:/alquileres";
    }

   
}