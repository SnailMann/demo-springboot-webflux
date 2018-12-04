package com.snailmann.webflux.repository;

import com.snailmann.webflux.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Dao层，继承MongoDB的响应式Repository
 * 第一个泛型是表，第二泛型是主键的类型
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
