package com.example.demo.dao;

import com.example.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author duanxiaoduan
 * @version 2018/5/17
 */
@Component
public class MongoDBDaoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(UserInfo userInfo){
        mongoTemplate.save(userInfo);
    }

    public <T> T findById(Class<T> entityClass, String id) {
        return mongoTemplate.findById(id, entityClass);
    }
}
