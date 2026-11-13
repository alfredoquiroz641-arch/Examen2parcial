/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.servicio;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.dao.impl.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServicioLibros {
    private final LibroDAO libroDAO;

    public ServicioLibros(LibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }

    public void insertar(Libro libro) {
        validarLibro(libro);
        libroDAO.insertar(libro);
    }

    public void actualizar(Libro libro) {
        if (libro.getId() == null || libro.getId() <= 0) {
            throw new DatoInvalidoException("El ID del libro es inválido para actualizar.");
        }
        validarLibro(libro);
        libroDAO.actualizar(libro);
    }

    public void eliminar(int id) {
        libroDAO.eliminar(id);
    }

    public Optional<Libro> buscarPorId(int id) {
        return libroDAO.buscarPorId(id);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public List<Libro> listarPorAutor(int autorId) {
        return libroDAO.listarPorAutor(autorId);
    }

    private void validarLibro(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new DatoInvalidoException("El titulo del libro es obligatorio.");
        }
        if (libro.getIsbn() == null || libro.getIsbn().trim().isEmpty()) {
            throw new DatoInvalidoException("El ISBN del libro es obligatorio.");
        }
        int anioActual = LocalDate.now().getYear();
        if (libro.getAnio() < 1450 || libro.getAnio() > anioActual) {
            throw new DatoInvalidoException("El año del libro debe estar entre 1450 y el año actual (" + anioActual + ").");
        }
        if (libro.getPrecio() < 0) {
            throw new DatoInvalidoException("El precio del libro no puede ser negativo.");
        }
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new DatoInvalidoException("El libro debe estar asociado a un autor existente.");
        }
    }
}