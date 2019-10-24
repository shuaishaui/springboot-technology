package com.shuai.springbootelasticsearch;


import com.shuai.springbootelasticsearch.pojo.User;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringBootES {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void save() {
        User user = new User();
        user.setId(1001L);
        user.setName("张三");
        user.setAge(20);
        user.setHobby("足球、篮球、听音乐");

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(user).build();

        String index = this.elasticsearchTemplate.index(indexQuery);

        System.out.println(index);
    }

    /**
     * 批量插入数据
     */
    @Test
    public void testBulk() {
        List list = new ArrayList();
        for (int i = 0; i < 5000; i++) {
            User user = new User();
            user.setId(1001L + i);
            user.setAge(i % 50 + 10);
            user.setName("张三" + i);
            user.setHobby("足球、篮球、听音乐");
            IndexQuery indexQuery = new
                    IndexQueryBuilder().withObject(user).build();
            list.add(indexQuery);
        }
        Long start = System.currentTimeMillis();
        this.elasticsearchTemplate.bulkIndex(list);
        System.out.println("用时：" + (System.currentTimeMillis() - start)); //用时：7836

    }

    /**
     * 局部更新，全部更新使用index覆盖即可
     */
    @Test
    public void testUpdate() {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source("age", "30");

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withId("1001")
                .withClass(User.class)
                .withIndexRequest(indexRequest).build();

        this.elasticsearchTemplate.update(updateQuery);
    }

    @Test
    public void testDelete() {
        this.elasticsearchTemplate.delete(User.class, "1001");
    }

    @Test
    public void testSearch() {
        PageRequest pageRequest = PageRequest.of(1, 10); //设置分页参数

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "张三")) // match查询
                .withPageable(pageRequest)
                .build();

        AggregatedPage<User> users =
                this.elasticsearchTemplate.queryForPage(searchQuery, User.class);

        System.out.println("总页数：" + users.getTotalPages()); //获取总页数

        for (User user : users.getContent()) { // 获取搜索到的数据
            System.out.println(user);
        }
    }


}
