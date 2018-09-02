package com.alphawang.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoCRUD {
    public static void main(String[] args) {
        Mongo mongo = new Mongo("127.0.0.1", 27017);
        DB db = new DB(mongo, "alpha-db");
        DBCollection collection = db.getCollection("alpha-collection-2");

        // 类似JDBC，相对比较底层
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("name", "alpha");
        dbObject.put("age", 18);
        dbObject.put("addr", "Shanghai");

        WriteResult writeResult = collection.insert(dbObject);

        DBCursor dbCursor = collection.find();
        for (DBObject obj : dbCursor) {
            log.error(">>>>> find collection: {}", obj);
        }
    }
}
