package com.cibertec.alquiler_webapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cibertec.alquiler_webapp.model.Alquiler;
import com.cibertec.alquiler_webapp.repository.AlquilerRepository;
import com.cibertec.alquiler_webapp.repository.ClienteRepository;
import com.cibertec.alquiler_webapp.repository.PeliculaRepository;

@Controller
public class AlquilerController {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PeliculaRepository peliculaRepository;

// Redirige desde /alquileres a la lista
public String getFechaFormateada(LocalDate fecha) {
    return fecha != null ? fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "—";
}

    @GetMapping("/alquileres")
    public String redirigirAlquileres() {
        return "redirect:/alquiler-listar";
    }

    // Lista principal de alquileres
    @GetMapping("/alquiler/listar")
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerRepository.findAll());
        return "alquiler-listar";
    }
// Guarda un nuevo alquiler
@GetMapping("/alquiler/nuevo")
public String nuevoAlquiler(Model model) {
    model.addAttribute("alquiler", new Alquiler());
    model.addAttribute("clientes", clienteRepository.findAll());
    model.addAttribute("peliculas", peliculaRepository.findAll());
    return "alquiler-nuevo";
}
@PostMapping("/guardar")
public String guardarAlquiler(@ModelAttribute Alquiler alquiler) {
    if (alquiler.getFecha() == null) {
        alquiler.setFecha(LocalDate.now());
    }
    alquilerRepository.save(alquiler);
    return "redirect:/alquiler/listar";
}
@GetMapping("/alquiler/editar/{id}")
public String editarAlquiler(@PathVariable Integer id, Model model) {
    Optional<Alquiler> opt = alquilerRepository.findById(id);
    if (opt.isPresent()) {
        model.addAttribute("alquiler", opt.get());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "alquiler-editar";
    }
    return "redirect:/alquiler/listar";
}
@GetMapping("/alquiler/eliminar/{id}")
public String eliminarAlquiler(@PathVariable Integer id) {
    alquilerRepository.deleteById(id);
    return "redirect:/alquiler/listar";
}
}