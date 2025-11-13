/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import org.bson.types.ObjectId;

// Excepciones personalizadas (deberías crearlas)
import dao.mongo.exceptions.DaoException;
import exceptions.EntityNotFoundException;
import model.Product;

/**
 *
 * @author jonyco
 */

public interface IProductDAO {

    Product create(Product producto) throws DaoException;

    Product findById(ObjectId id) throws EntityNotFoundException, DaoException;

    List<Product> findAll() throws DaoException;

    Product update(ObjectId id, Product producto) throws EntityNotFoundException, DaoException;

    void deleteById(ObjectId id) throws EntityNotFoundException, DaoException;

    void deleteAll() throws DaoException;

    List<Product> findByNombre(String nombre) throws DaoException;

    List<Product> findByCategoria(String categoria) throws DaoException;
}
