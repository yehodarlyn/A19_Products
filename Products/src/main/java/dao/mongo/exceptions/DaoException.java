/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.mongo.exceptions;

/**
 *
 * @author jonyco
 */
/**
 * Excepción general para errores ocurridos en la capa DAO.
 */
public class DaoException extends Exception {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje que describe el error.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje y la causa original (excepción
     * anidada).
     *
     * @param message El mensaje que describe el error.
     * @param cause La excepción original que causó este error.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
