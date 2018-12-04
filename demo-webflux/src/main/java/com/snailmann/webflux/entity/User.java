package com.snailmann.webflux.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * document是mongo的注解，文件也就是表
 */
@Data
@Document(collection = "user")
public class User {

    /**
     * mongodb主键
     */
    @Id
    private String id;

    private String name;

    private int age;


}
