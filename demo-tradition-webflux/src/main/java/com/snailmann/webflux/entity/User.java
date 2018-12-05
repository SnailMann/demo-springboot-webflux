package com.snailmann.webflux.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


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

    /**
     * 原来是Hibernate校验，现在Javax包下自己就有了
     * 名字不能为空
     */
    @NotBlank
    private String name;

    /**
     * 年龄参数在10到40之间
     */
    @Range(min = 10, max = 40)
    private int age;


}
