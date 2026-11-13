/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author Usuario
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    public static void inicializar() {
        String sqlAutores = "CREATE TABLE IF NOT EXISTS autores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "nacionalidad TEXT"
                + ");";

        String sqlLibros = "CREATE TABLE IF NOT EXISTS libros ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "isbn TEXT NOT NULL UNIQUE, "
                + "titulo TEXT NOT NULL, "
                + "anio INTEGER NOT NULL, "
                + "precio REAL NOT NULL, "
                + "autor_id INTEGER NOT NULL, "
                + "FOREIGN KEY (autor_id) REFERENCES autores(id)"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlAutores);
            stmt.execute(sqlLibros);
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}