package com.shuai.springbootelasticsearch.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "itcast", type = "user", createIndex = false)
public class User {

    @Id
    private Long id;
    @Field(store = true)
    private String name;
    @Field
    private Integer age;
    @Field(store = true)
    private String hobby;


}
