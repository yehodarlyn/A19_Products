/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app;

/**
 *
 * @author jonyco
 */
import config.MongoClientProvider;
import dao.IProductDAO;
import dao.mongo.ProductDAO;
import java.util.Arrays;
import java.util.List;
import model.Product;
import model.Proveedor;
import org.bson.types.ObjectId;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando prueba CRUD de ProductosDB...");

        
        IProductDAO productoDao = new ProductDAO();
        ObjectId idProductoCreado = null;

        try {
            // --- LIMPIEZA INICIAL ---
            System.out.println("\n--- Limpiando colección ---");
            productoDao.deleteAll();
            System.out.println("Colección 'productos' limpia.");

            
            System.out.println("\n--- CREATE ---");
            Proveedor prov1 = new Proveedor("TechGlobal", "ventas@techg.com", "USA");
            Product nuevoProducto = new Product();
            nuevoProducto.setNombre("Laptop Pro 15");
            nuevoProducto.setPrecio(1200.50);
            nuevoProducto.setStock(50);
            nuevoProducto.setProveedor(prov1);
            nuevoProducto.setCategorias(Arrays.asList("Electrónica", "Computadoras", "Premium"));

            Product creado = productoDao.create(nuevoProducto);
            idProductoCreado = creado.getId();
            System.out.println("Producto CREADO: " + creado);

            
            System.out.println("\n--- READ (By Id) ---");
            Product encontrado = productoDao.findById(idProductoCreado);
            System.out.println("Producto ENCONTRADO: " + encontrado);

            
            System.out.println("\n--- UPDATE ---");
            encontrado.setStock(45); // Reducir el stock
            encontrado.setPrecio(1199.00); // Rebaja
            Product actualizado = productoDao.update(idProductoCreado, encontrado);
            System.out.println("Producto ACTUALIZADO: " + actualizado);

            
            System.out.println("\n--- READ (All) ---");
            List<Product> todos = productoDao.findAll();
            System.out.println("Total de productos: " + todos.size());
            todos.forEach(System.out::println);

            // --- READ (Búsquedas específicas) ---
            System.out.println("\n--- READ (By Nombre 'Laptop') ---");
            List<Product> porNombre = productoDao.findByNombre("Laptop");
            System.out.println("Encontrados por nombre: " + porNombre.size());

            System.out.println("\n--- READ (By Categoria 'Premium') ---");
            List<Product> porCategoria = productoDao.findByCategoria("Premium");
            System.out.println("Encontrados por categoría: " + porCategoria.size());

            
            System.out.println("\n--- DELETE ---");
            productoDao.deleteById(idProductoCreado);
            System.out.println("Producto ELIMINADO (ID: " + idProductoCreado + ")");

            // --- Verificación Final ---
            System.out.println("\n--- Verificación Final (FindAll) ---");
            List<Product> fin = productoDao.findAll();
            System.out.println("Total de productos después de eliminar: " + fin.size());

        } catch (Exception e) {
            
            System.err.println("\n*** ERROR EN LA OPERACIÓN CRUD ***");
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            MongoClientProvider.close();
            System.out.println("\nConexión cerrada. Fin de la prueba.");
        }
    }
}
