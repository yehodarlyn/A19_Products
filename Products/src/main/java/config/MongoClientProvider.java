/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.Product;
import org.bson.Document;

/**
 *
 * @author jonyco
 */

public class MongoClientProvider {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private MongoClientProvider() {
    }

    /**
     
     */
    private static void initialize() {
        if (mongoClient == null) {

            mongoClient = MongoClients.create(MongoConfig.createClientSettings());
            database = mongoClient.getDatabase(MongoConfig.DB_NAME);
        }
    }

    /**
     * Obtiene la instancia de la base de datos
     */
    public static MongoDatabase getDatabase() {
        initialize();
        return database;
    }

    /**
     * Obtiene una colección tipada (POJO)
     *
     * @param collectionName Nombre de la colección
     * @param modelClass Clase del modelo POJO
     * @return La MongoCollection tipada
     */
    public static <T> MongoCollection<T> getCollection(String collectionName, Class<T> modelClass) {
        initialize();
        return database.getCollection(collectionName, modelClass);
    }

    public static MongoCollection<Document> getCollection(String collectionName) {
        initialize();
        return database.getCollection(collectionName);
    }

    /**
     * Cierra la conexión
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
        }
    }
}
