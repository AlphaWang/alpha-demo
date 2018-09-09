package com.alphawang.mongodb.orm.framework.dao;

import com.alphawang.mongodb.orm.framework.QueryRule;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDaoSupport <T extends Serializable, PK extends Serializable> {
    
    private MongoTemplate mongoTemplate;
    
    protected void setTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }
    
    protected List<T> find(QueryRule rule) {
        mongoTemplate.find()
        return null;
    }
    
    protected int saveAll(List<T> list) {
        mongoTemplate.insertAll(list);
        return list.size();
    }
    
    
}
