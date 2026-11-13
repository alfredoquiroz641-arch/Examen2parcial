/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.excepciones;

/**
 *
 * @author Usuario
 */

public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String message) {
        super(message);
    }
}