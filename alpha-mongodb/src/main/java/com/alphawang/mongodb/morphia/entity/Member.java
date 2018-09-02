package com.alphawang.mongodb.morphia.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

@Data
@Builder
public class Member {
    
    @Id
    private ObjectId objectId;
    
    private String name;
    private int age;
    private String addr;
}
