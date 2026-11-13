/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.app;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.dao.impl.Conexion;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Conexion.inicializar();
        
        try {
         System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AutorDAOImpl autorDAO = new AutorDAOImpl();
        LibroDAOImpl libroDAO = new LibroDAOImpl();

        ServicioAutores servicioAutores = new ServicioAutores(autorDAO, libroDAO);
        ServicioLibros servicioLibros = new ServicioLibros(libroDAO);

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=== SISTEMA DE BIBLIOTECA ===");
            System.out.println("1. Listar Autores");
            System.out.println("2. Registrar Autor");
            System.out.println("3. Eliminar Autor");
            System.out.println("4. Listar Libros");
            System.out.println("5. Registrar Libro");
            System.out.println("6. Listar Libros por Autor");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        System.out.println("\n--- Lista de Autores ---");
                        servicioAutores.listar().forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Nombre del autor: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Nacionalidad: ");
                        String nac = scanner.nextLine();
                        servicioAutores.insertar(new Autor(nombre, nac));
                        System.out.println("Autor registrado con exito.");
                        break;
                    case 3:
                        System.out.print("ID del autor a eliminar: ");
                        int idAutor = Integer.parseInt(scanner.nextLine());
                        servicioAutores.eliminar(idAutor);
                        System.out.println("Autor eliminado");
                        break;
                    case 4:
                        System.out.println("\n--- Lista de Libros ---");
                        servicioLibros.listar().forEach(System.out::println);
                        break;
                    case 5:
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Titulo: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Año: ");
                        int anio = Integer.parseInt(scanner.nextLine());
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("ID del Autor existente: ");
                        int aId = Integer.parseInt(scanner.nextLine());

                        Autor autorOpt = servicioAutores.buscarPorId(aId).orElseThrow(() -> new DatoInvalidoException("El autor con ID " + aId + " no existe."));
                        servicioLibros.insertar(new Libro(isbn, titulo, anio, precio, autorOpt));
                        System.out.println("Libro registrado con exito");
                        break;
                    case 6:
                        System.out.print("ID del Autor: ");
                        int autorFiltro = Integer.parseInt(scanner.nextLine());
                        System.out.println("\n--- Libros del Autor ---");
                        servicioLibros.listarPorAutor(autorFiltro).forEach(System.out::println);
                        break;
                    case 7:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            } catch (DatoInvalidoException | DatosException e) {
                System.err.println("Error de validacion / base de datos: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Debe ingresar un valor numerico valido.");
            }
        } while (opcion != 7);

        scanner.close();
    }
}