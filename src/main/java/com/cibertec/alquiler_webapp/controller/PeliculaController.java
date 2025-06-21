package com.cibertec.alquiler_webapp.controller;

import com.cibertec.alquiler_webapp.model.Pelicula;
import com.cibertec.alquiler_webapp.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PeliculaController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping("/peliculas")
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "pelicula-listar";
    }

    @GetMapping("/pelicula/nueva")
    public String nuevaPelicula(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "pelicula-formulario";
    }

    @PostMapping("/pelicula/guardar")
    public String guardarPelicula(@ModelAttribute Pelicula pelicula) {
        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }

    @GetMapping("/pelicula/editar/{idPelicula}")
    public String editarPelicula(@PathVariable("idPelicula") Integer idPelicula, Model model) {
        Pelicula pelicula = peliculaRepository.findById(idPelicula).orElseThrow();
        model.addAttribute("pelicula", pelicula);
        return "pelicula-formulario";
    }

    @GetMapping("/pelicula/eliminar/{idPelicula}")
    public String eliminarPelicula(@PathVariable("idPelicula") Integer idPelicula) {
        peliculaRepository.deleteById(idPelicula);
        return "redirect:/peliculas";
    }
}