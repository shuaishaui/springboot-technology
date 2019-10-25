package com.shuai.springbootelasticsearch.service;

import com.shuai.springbootelasticsearch.vo.HouseData;
import com.shuai.springbootelasticsearch.vo.SearchResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public static final Integer ROWS = 10;

    public SearchResult search(String keyWord, Integer page) {
        // 记得这里要 page-1 是从0开始的
        PageRequest pageRequest = PageRequest.of(page - 1, ROWS); //设置分页参数

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord, "title", "title.pinyin"
                ).operator(Operator.AND)) // match查询
                .withPageable(pageRequest)
                .withHighlightFields(new HighlightBuilder.Field("title")) // 设置高亮
                .build();

        AggregatedPage<HouseData> housePage = this.elasticsearchTemplate.queryForPage
                                                                         (searchQuery, HouseData.class);
        return new SearchResult(housePage.getTotalPages(), housePage.getContent(), null);

    }


}
