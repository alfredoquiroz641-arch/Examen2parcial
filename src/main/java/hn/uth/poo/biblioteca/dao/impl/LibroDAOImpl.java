/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.dao.impl.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void insertar(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, anio, precio, autor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getAnio());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getAutor().getId());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    libro.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Libro libro) {
        String sql = "UPDATE libros SET isbn = ?, titulo = ?, anio = ?, precio = ?, autor_id = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setInt(3, libro.getAnio());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getAutor().getId());
            stmt.setInt(6, libro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Libro> buscarPorId(int id) {
        String sql = "SELECT l.*, a.nombre as autor_nombre, a.nacionalidad as autor_nacionalidad " +
                     "FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar libro por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre as autor_nombre, a.nacionalidad as autor_nacionalidad " +
                     "FROM libros l JOIN autores a ON l.autor_id = a.id";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar libros: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public List<Libro> listarPorAutor(int autorId) {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT l.*, a.nombre as autor_nombre, a.nacionalidad as autor_nacionalidad " +
                     "FROM libros l JOIN autores a ON l.autor_id = a.id WHERE l.autor_id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, autorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar libros por autor: " + e.getMessage(), e);
        }
        return lista;
    }

    private Libro mapear(ResultSet rs) throws SQLException {
        Autor autor = new Autor(
                rs.getInt("autor_id"),
                rs.getString("autor_nombre"),
                rs.getString("autor_nacionalidad")
        );
        return new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                autor
        );
    }
}