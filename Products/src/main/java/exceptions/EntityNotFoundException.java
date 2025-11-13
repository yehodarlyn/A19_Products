/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

import dao.mongo.exceptions.DaoException;

/**
 *
 * @author jonyco
 */
/**
 * Excepción lanzada cuando una entidad (ej. un Producto) no se encuentra en la
 * base de datos cuando se esperaba.
 */
public class EntityNotFoundException extends DaoException {

    /**
     * Constructor que acepta un mensaje de error.
     *
     * @param message El mensaje que describe el error (ej. "Producto no
     * encontrado").
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor que acepta un mensaje y la causa original.
     *
     * @param message El mensaje que describe el error.
     * @param cause La excepción original que causó este error.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
