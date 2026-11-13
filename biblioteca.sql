SQL
-- Activar el soporte de claves foráneas en SQLite
PRAGMA foreign_keys = ON;

-- Creación de la tabla de Autores
CREATE TABLE IF NOT EXISTS autores (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    nacionalidad TEXT
);

-- Creación de la tabla de Libros relacionada con Autores
CREATE TABLE IF NOT EXISTS libros (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    isbn TEXT NOT NULL UNIQUE,
    titulo TEXT NOT NULL,
    anio INTEGER NOT NULL,
    precio REAL NOT NULL,
    autor_id INTEGER NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);