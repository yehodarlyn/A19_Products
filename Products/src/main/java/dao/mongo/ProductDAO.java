/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import config.MongoClientProvider;
import dao.IProductDAO;

import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import dao.mongo.exceptions.DaoException;
import exceptions.EntityNotFoundException;
import model.Product;

/**
 *
 * @author jonyco
 */


public class ProductDAO implements IProductDAO {

    private final MongoCollection<Product> productosCollection;

    public ProductDAO() {
        
        this.productosCollection = MongoClientProvider.getCollection("productos", Product.class);
    }

    @Override
    public Product create(Product producto) throws DaoException {
        try {
            
            producto.setId(new ObjectId());
            Instant now = Instant.now();
            producto.setCreatedAt(now);
            producto.setUpdatedAt(now);
            
            productosCollection.insertOne(producto);
            return producto;
        } catch (Exception e) {
            throw new DaoException("Error al crear el producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Product findById(ObjectId id) throws EntityNotFoundException, DaoException {
        try {
            Product producto = productosCollection.find(Filters.eq("_id", id)).first();
            if (producto == null) {
                throw new EntityNotFoundException("Producto no encontrado con ID: " + id);
            }
            return producto;
        } catch (EntityNotFoundException e) {
            throw e; // Relanzar la excepción específica 
        } catch (Exception e) {
            throw new DaoException("Error al buscar producto por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> findAll() throws DaoException {
        try {
            return productosCollection.find().into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error al listar todos los productos: " + e.getMessage(), e);
        }
    }

    @Override
    public Product update(ObjectId id, Product producto) throws EntityNotFoundException, DaoException {
        try {
            
            Instant now = Instant.now();
            
            UpdateResult result = productosCollection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(
                    Updates.set("nombre", producto.getNombre()),
                    Updates.set("precio", producto.getPrecio()),
                    Updates.set("stock", producto.getStock()),
                    Updates.set("proveedor", producto.getProveedor()),
                    Updates.set("categorias", producto.getCategorias()),
                    Updates.set("updatedAt", now) // Actualiza la fecha [cite: 51]
                )
            );

            if (result.getMatchedCount() == 0) {
                throw new EntityNotFoundException("Producto no encontrado para actualizar (ID: " + id + ")");
            }
            
            // Devolver el estado actualizado
            producto.setId(id);
            producto.setUpdatedAt(now);
            // (Nota: createdAt se mantendría del objeto original, que no tenemos aquí. 
            // Para ser perfecto, deberíamos hacer un find() primero o usar findOneAndUpdate)
            return producto; 
            
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException("Error al actualizar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ObjectId id) throws EntityNotFoundException, DaoException {
        try {
            DeleteResult result = productosCollection.deleteOne(Filters.eq("_id", id));
            if (result.getDeletedCount() == 0) {
                throw new EntityNotFoundException("Producto no encontrado para eliminar (ID: " + id + ")");
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DaoException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() throws DaoException {
        try {
            productosCollection.deleteMany(Filters.empty());
        } catch (Exception e) {
            throw new DaoException("Error al eliminar todos los productos: " + e.getMessage(), e);
        }
    }

    // --- Búsquedas Específicas ---

    @Override
    public List<Product> findByNombre(String nombre) throws DaoException {
        try {
            // Búsqueda tipo "like" (insensible a mayúsculas)
            Pattern regex = Pattern.compile(Pattern.quote(nombre), Pattern.CASE_INSENSITIVE);
            return productosCollection.find(Filters.regex("nombre", regex)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error al buscar por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> findByCategoria(String categoria) throws DaoException {
        try {
            // Busca si la categoría está en el array "categorias"
            return productosCollection.find(Filters.eq("categorias", categoria)).into(new ArrayList<>());
        } catch (Exception e) {
            throw new DaoException("Error al buscar por categoría: " + e.getMessage(), e);
        }
    }
}