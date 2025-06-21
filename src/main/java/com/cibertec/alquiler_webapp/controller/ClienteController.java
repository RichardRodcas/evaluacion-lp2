package com.cibertec.alquiler_webapp.controller;
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
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "cliente-listar";
    }
    @GetMapping("/cliente/nuevo")
    public String nuevoCliente(Model model) {
        System.out.println("➡ Entró al formulario de cliente");
        model.addAttribute("cliente", new Cliente());
        return "cliente-formulario";
    }
    @PostMapping("/cliente/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
    clienteRepository.save(cliente);
    return "redirect:/clientes";
    }

   @GetMapping("/cliente/editar/{idCliente}")
    public String editarCliente(@PathVariable("idCliente") Integer idCliente, Model model) {
    Cliente cliente = clienteRepository.findById(idCliente).orElseThrow();
    model.addAttribute("cliente", cliente);
    return "cliente-formulario";
    }
    @PostMapping("/cliente/actualizar")
    public String actualizarCliente(@ModelAttribute Cliente cliente) {
    clienteRepository.save(cliente);
    return "redirect:/clientes";
    }
    @GetMapping("/cliente/eliminar/{idCliente}")
    public String eliminarCliente(@PathVariable("idCliente") Integer idCliente) {
    clienteRepository.deleteById(idCliente);
    return "redirect:/clientes";
    }
}
