package com.alphawang.mongodb.crud;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.Arrays;

@Slf4j
public class MongoClientCRUD {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("alpha-db");
        MongoCollection<Document> collection = database.getCollection("alpha-collection");

        Document doc = new Document("name", "MongoDB")
            .append("type", "database")
            .append("count", 1)
            .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
            .append("info", new Document("x", 203).append("y", 102));
        
        log.info("document: {}", doc);
        collection.insertOne(doc);

        FindIterable<Document> documents = collection.find();
        for (Document document: documents) {
            log.error(">>>> get document: {}", document);
        }
    }
}
