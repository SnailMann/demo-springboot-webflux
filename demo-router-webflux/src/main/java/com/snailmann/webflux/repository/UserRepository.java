package com.snailmann.webflux.repository;

import com.snailmann.webflux.entity.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Dao层，继承MongoDB的响应式Repository
 * 第一个泛型是表，第二泛型是主键的类型
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    /**
     * 根据年龄段查找用户
     * 只写接口，jpa会根据方法名来帮我们生成sql
     *
     * @param start
     * @param end
     * @return
     */
    Flux<User> getByAgeBetween(int start, int end);


    /**
     * 也可以自己写查询语句
     */
    @Query("{'age':{'$gte':1,'$lte':40}}")
    Flux<User> getOldUser();

}

