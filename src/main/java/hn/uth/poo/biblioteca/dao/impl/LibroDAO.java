/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;
import java.util.Optional;

public interface LibroDAO {
    void insertar(Libro libro);
    void actualizar(Libro libro);
    void eliminar(int id);
    Optional<Libro> buscarPorId(int id);
    List<Libro> listar();
    List<Libro> listarPorAutor(int autorId);
}
