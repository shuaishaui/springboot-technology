package com.shuai.springbootelasticsearch.vo;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    private Integer totalPage;
    private List<HouseData> list;
    private Set<String> hotWord;//热词

}