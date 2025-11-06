/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author jonyco
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Asignacion18 {

    public static void main(String[] args) {
        // Conexion
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

            // Usar la bd restaurants
            MongoDatabase database = mongoClient.getDatabase("restaurants");

            // Obtener la coleccion cafes 
            MongoCollection<Document> collection = database.getCollection("cafes");

            // -Aqui iran los fragmentos de codigo de las tareas 
            System.out.println("Operaciones completadas.");

            // 1. Insertar un solo documento 
            System.out.println("Insertando Café de la Plaza...");
            Document cafePlaza = new Document("name", "Café de la Plaza")
                    .append("stars", 4.3)
                    .append("categories", Arrays.asList("Café", "Postres", "Desayuno"));

            collection.insertOne(cafePlaza);

            // 2. Insertar varios documentos 
            System.out.println("Insertando varios cafes....");
            Document espresso = new Document("name", "Espresso Express")
                    .append("stars", 4.8)
                    .append("categories", Arrays.asList("Cafe", "Rapido", "Takeaway"));

            Document teaHouse = new Document("name", "The Tea House")
                    .append("stars", 3.9)
                    .append("categories", Arrays.asList("Te", "Infusiones", "Postres"));

            Document morningBrew = new Document("name", "Morning Brew")
                    .append("stars", 4.0)
                    .append("categories", Arrays.asList("Cafe", "Desayuno", "Bakery"));

            List<Document> cafes = Arrays.asList(espresso, teaHouse, morningBrew);
            collection.insertMany(cafes);

            // 3. Filtros para mostrar
            System.out.println("\n--- Documentos con stars >= 4.5 ---");
            collection.find(gte("stars", 4.5))
                    .forEach((java.util.function.Consumer<Document>) doc -> System.out.println(doc.toJson()));

            System.out.println("\n--- Documentos cuyo nombre contiene 'Cafe' ---");
            // Usamos una expresion regular para "contiene
            collection.find(regex("name", "Cafe"))
                    .forEach((java.util.function.Consumer<Document>) doc -> System.out.println(doc.toJson()));

            System.out.println("\n--- Documentos con categories que incluyan 'Postres' ---");
            // Correcto para buscar valores dentro de un arreglo
            collection.find(in("categories", "Postres"))
                    .forEach((java.util.function.Consumer<Document>) doc -> System.out.println(doc.toJson()));

            System.out.println("\n--- Documentos con stars entre 3 y 4.3 ---");
            collection.find(and(gte("stars", 3.0), lte("stars", 4.3)))
                    .forEach((java.util.function.Consumer<Document>) doc -> System.out.println(doc.toJson()));

            System.out.println("\n--- Documentos cuyo nombre empieza con 'T' ---");
            // Usamos expresion regular para "empieza con
            collection.find(regex("name", "^T"))
                    .forEach((java.util.function.Consumer<Document>) doc -> System.out.println(doc.toJson()));

            // 4. Updates 
            // Cambiar stars a 4.5 para "Morning brew 
            System.out.println("\nActualizando 'Morning Brew'....");
            collection.updateOne(eq("name", "Morning Brew"), set("stars", 4.5));

            // Incrementar stars +0.2 para "bakery o "desayuno 
            collection.updateMany(
                    or(in("categories", "Bakery"), in("categories", "Desayuno")),
                    inc("stars", 0.2)
            );

            // Agregar campos phone y open a "Cafe de la Plaza 
            System.out.println("Agregando campos a 'Cafe de la Plaza'...");
            collection.updateOne(
                    eq("name", "Cafe de la Plaza"),
                    combine(set("phone", "668-2432-923"), set("open", true))
            );

            // 5. Deletes
            // Eliminar documento con name = "Espresso wxpress
            System.out.println("\nEliminando 'Espresso Express'...");
            collection.deleteOne(eq("name", "Espresso Express"));

            // Eliminar todos los documentos con stars < 4 
            System.out.println("Eliminando documentos con stars < 4...");
            collection.deleteMany(lt("stars", 4.0));

            // Eliminar documentos con "Takeaway o "infusiones 
            System.out.println("Eliminando por categoria 'Takeaway' o 'Infusiones'...");
            collection.deleteMany(or(in("categories", "Takeaway"), in("categories", "Infusiones")));

        }
    }
}
