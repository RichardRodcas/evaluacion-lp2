package com.cibertec;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.cibertec.model.Cliente;
import com.cibertec.service.AlquilerService;
import com.cibertec.model.Pelicula;
/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AlquilerService service = new AlquilerService();

        try {
            // Mostrar clientes
            List<Cliente> clientes = service.obtenerClientes();
            System.out.println("=== CLIENTES ===");
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println((i + 1) + ". " + clientes.get(i).getNombre());
            }

            System.out.print("Seleccione cliente: ");
            int indexCliente = sc.nextInt() - 1;
            Cliente cliente = clientes.get(indexCliente);

            // Mostrar películas
            List<Pelicula> peliculas = service.obtenerPeliculas();
            Map<Pelicula, Integer> seleccionadas = new HashMap<>();

            while (true) {
                System.out.println("=== PELÍCULAS ===");
                for (int i = 0; i < peliculas.size(); i++) {
                    Pelicula p = peliculas.get(i);
                    System.out.println((i + 1) + ". " + p.getTitulo() + " (Stock: " + p.getStock() + ")");
                }

                System.out.print("Seleccione película (0 para terminar): ");
                int index = sc.nextInt();
                if (index == 0) break;

                Pelicula pelicula = peliculas.get(index - 1);

                System.out.print("Ingrese cantidad: ");
                int cantidad = sc.nextInt();

                if (cantidad > pelicula.getStock()) {
                    System.out.println("❌ Stock insuficiente.");
                    continue;
                }

                seleccionadas.put(pelicula, cantidad);
            }

            // Calcular total 
            BigDecimal total = seleccionadas.entrySet().stream()
                    .map(e -> BigDecimal.valueOf(e.getValue()).multiply(BigDecimal.TEN))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Registrar alquiler
            service.registrarAlquiler(cliente, seleccionadas, total);

            System.out.println("✅ Alquiler registrado con éxito. Total: S/ " + total);

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        sc.close();
    }
}