## 1. 声明

在这个项目里面，我引入了`Elasticsearch`，我使用了三种方式来操作它，分别是

+ REST风格的客户端
+ Java API的客户端
+ SpringBoot与Elasticsearch的整合

因为存在pom依赖冲突的问题，所以我注释掉了前两者的pom依赖，大家在各种测试的时候，记得打开相应依赖

而且这里我是使用了三个Elasticsearch的集群

## 2. REST风格的客户端

pom

```java
<dependency>
   <groupId>org.elasticsearch.client</groupId>
   <artifactId>elasticsearch-rest-client</artifactId>
   <version>6.5.4</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.4</version>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
</dependency>
```

是在test目录下的`TestRestApi`

## 3. Java API的客户端

pom

```java
 <dependency>
      <groupId>org.elasticsearch.client</groupId>
      <artifactId>elasticsearch-rest-high-level-client</artifactId>
      <version>6.5.4</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.4</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
    </dependency>
```

是在test目录下的`TestRestHighLevel`

## 4. SpringBoot与Elasticsearch的整合

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```





是在test目录下的`TestSpringBootES`

注意，这里在配置文件中，端口号要写为9300，而不是之前的9200，，原因是9200是RESTful端口，9300是API端口。 

