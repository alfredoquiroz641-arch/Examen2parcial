/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.servicio;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.dao.impl.AutorDAO;
import hn.uth.poo.biblioteca.dao.impl.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.util.List;
import java.util.Optional;

public class ServicioAutores {
    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void insertar(Autor autor) {
        validarAutor(autor);
        autorDAO.insertar(autor);
    }

    public void actualizar(Autor autor) {
        if (autor.getId() == null || autor.getId() <= 0) {
            throw new DatoInvalidoException("El ID del autor es invalido para actualizar.");
        }
        validarAutor(autor);
        autorDAO.actualizar(autor);
    }

    public void eliminar(int id) {
        // Regla: No se puede eliminar un autor que tiene libros
        List<?> libros = libroDAO.listarPorAutor(id);
        if (!libros.isEmpty()) {
            throw new DatoInvalidoException("No se puede eliminar el autor porque tiene libros asociados.");
        }
        autorDAO.eliminar(id);
    }

    public Optional<Autor> buscarPorId(int id) {
        return autorDAO.buscarPorId(id);
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    private void validarAutor(Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del autor es obligatorio.");
        }
    }
}
