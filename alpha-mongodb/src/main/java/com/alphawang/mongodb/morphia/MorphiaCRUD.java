package com.alphawang.mongodb.morphia;

import com.alphawang.mongodb.morphia.entity.Member;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

/**
 * https://mongodb.github.io/morphia/
 */
@Slf4j
public class MorphiaCRUD {

    public static void main(String[] args) {
        Morphia morphia = new Morphia();

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        Datastore datastore = morphia.createDatastore(mongoClient, "alpha-db");

        Member member = Member.builder()
            .objectId(new ObjectId("5b8be519ff5fe15344be5222"))
            .name("alpha-morphia")
            .age(21)
            .addr("Zhoupu")
            .build();

        Key<Member> key = datastore.save(member);  // save collection.
        log.error("key : {}", key);
        log.error("key-id : {}", key.getId());
    }
}
