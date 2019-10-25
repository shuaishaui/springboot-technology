package com.shuai.springbootelasticsearch.controller;


import com.shuai.springbootelasticsearch.service.SearchService;
import com.shuai.springbootelasticsearch.vo.SearchResult;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("search")
@RestController
// 支持跨域
@CrossOrigin
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private SearchService searchService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping
    public SearchResult search(@RequestParam("keyWord") String keyWord,
                               @RequestParam(value = "page", defaultValue = "1")
                                       Integer page) {
        if (page > 100) { //防止爬虫抓取过多的数据
            page = 1;
        }
        String redisKey = "HAOKE_SERCH_HOT_WORD";
        SearchResult search = this.searchService.search(keyWord, page);
        if(search.getTotalPage() <= 1){
            // 当搜索数据少于一一页的时候，提示热词，从Redis中获取热词，ZSET根据分数排序，reverseRange倒过来排
            Set<String> set = this.redisTemplate.opsForZSet().reverseRange(redisKey, 0, 4);
            search.setHotWord(set);
        }
        // (Math.max(search.getTotalPage(), 1) 为了保证避免当页数为0是0-1=-1而结果出错
        double count = ((Math.max(search.getTotalPage(), 1) - 1) * SearchService.ROWS) + search.getList().size();
        LOGGER.info("[Search]关键字为：" + keyWord + "，数量为：" + count);

        // 进行热词的保存，使用Redis的可排序zset
        redisTemplate.opsForZSet().add(redisKey,keyWord ,count );

        return search;
    }

}
