/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author Usuario
 */

import hn.uth.poo.biblioteca.dao.impl.AutorDAO;
import hn.uth.poo.biblioteca.dao.impl.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public void insertar(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getNacionalidad());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    autor.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autores SET nombre = ?, nacionalidad = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNombre());
            stmt.setString(2, autor.getNacionalidad());
            stmt.setInt(3, autor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM autores WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Autor> buscarPorId(int id) {
        String sql = "SELECT * FROM autores WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor por ID: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Autor> listar() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar autores: " + e.getMessage(), e);
        }
        return lista;
    }

    private Autor mapear(ResultSet rs) throws SQLException {
        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad")
        );
    }
}
