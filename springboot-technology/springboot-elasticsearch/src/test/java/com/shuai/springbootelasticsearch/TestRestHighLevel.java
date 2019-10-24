package com.shuai.springbootelasticsearch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Java High Level REST Client 的测试
 */
public class TestRestHighLevel {

  private RestHighLevelClient restHighLevelClient;

  @Before
  public void init() {
    RestClientBuilder restClientBuilder = RestClient.builder(
        new HttpHost("172.16.55.185", 9200),
        new HttpHost("172.16.55.185", 9201),
        new HttpHost("172.16.55.185", 9202)
    );
    this.restHighLevelClient = new RestHighLevelClient(restClientBuilder);
  }

  @After
  public void close() throws Exception {
    this.restHighLevelClient.close();
  }

  @Test
  public void testSave() throws Exception {

    Map<String, Object> data = new HashMap<>();
    data.put("id", 2002);
    data.put("title", "南京东路 二室一厅");
    data.put("price", 4000);

    IndexRequest indexRequest = new IndexRequest("shuai", "house").source(data);

    IndexResponse response = this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

    System.out.println("id -> " + response.getId());
    System.out.println("version -> " + response.getVersion());
    System.out.println("result -> " + response.getResult());


  }

  @Test
  public void testCreateAsync() throws Exception {
    Map<String, Object> data = new HashMap<>();
    data.put("id", "2004");
    data.put("title", "南京东路2 最新房源 二室一厅");
    data.put("price", "5600");

    IndexRequest indexRequest = new IndexRequest("shuai", "house")
        .source(data);

    this.restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new
        ActionListener<IndexResponse>() {
          @Override
          public void onResponse(IndexResponse indexResponse) {
            System.out.println("id->" + indexResponse.getId());
            System.out.println("index->" + indexResponse.getIndex());
            System.out.println("type->" + indexResponse.getType());
            System.out.println("version->" + indexResponse.getVersion());
            System.out.println("result->" + indexResponse.getResult());
            System.out.println("shardInfo->" + indexResponse.getShardInfo());
          }

          @Override
          public void onFailure(Exception e) {
            System.out.println(e);
          }
        });

    System.out.println("ok");

    Thread.sleep(20000);
  }

  @Test
  public void testQuery() throws Exception {
    GetRequest getRequest = new GetRequest("shuai", "house",
        "jpugHGgBqiMwr19HZE4A");

    // 指定返回的字段
    String[] includes = new String[]{"title", "id"};
    String[] excludes = Strings.EMPTY_ARRAY;
    FetchSourceContext fetchSourceContext =
        new FetchSourceContext(true, includes, excludes);

    getRequest.fetchSourceContext(fetchSourceContext);

    GetResponse response = this.restHighLevelClient.get(getRequest,
        RequestOptions.DEFAULT);

    System.out.println("数据 -> " + response.getSource());
  }

  /**
   * 判断是否存在
   *
   * @throws Exception
   */
  @Test
  public void testExists() throws Exception {
    GetRequest getRequest = new GetRequest("shuai", "house",
        "jpugHGgBqiMwr19HZE4b");

    // 不返回的字段
    getRequest.fetchSourceContext(new FetchSourceContext(false));
    boolean exists = this.restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    System.out.println("exists -> " + exists);
  }

  /**
   * 删除数据
   *
   * @throws Exception
   */
  @Test
  public void testDelete() throws Exception {
    DeleteRequest deleteRequest = new DeleteRequest("shuai", "house",
        "kJv6HGgBqiMwr19H005b");

    DeleteResponse response = this.restHighLevelClient.delete(deleteRequest,
        RequestOptions.DEFAULT);

    System.out.println(response.status());// OK or NOT_FOUND
  }

  /**
   * 更新数据
   *
   * @throws Exception
   */
  @Test
  public void testUpdate() throws Exception {
    UpdateRequest updateRequest = new UpdateRequest("shuai", "house",
        "j5vaHGgBqiMwr19H-k63");

    Map<String, Object> data = new HashMap<>();
    data.put("title", "南京西路2 一室一厅2");
    data.put("price", "4000");
    updateRequest.doc(data);

    UpdateResponse response = this.restHighLevelClient.update(updateRequest,
        RequestOptions.DEFAULT);
    System.out.println("version -> " + response.getVersion());
  }

  /**
   * 测试搜索
   *
   * @throws Exception
   */
  @Test
  public void testSearch() throws Exception {
    SearchRequest searchRequest = new SearchRequest("shuai");
    searchRequest.types("house");

    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    sourceBuilder.query(QueryBuilders.matchQuery("title", "拎包入住"));
    sourceBuilder.from(0);
    sourceBuilder.size(5);
    sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
    searchRequest.source(sourceBuilder);

    SearchResponse search = this.restHighLevelClient.search(searchRequest,
        RequestOptions.DEFAULT);

    System.out.println("搜索到 " + search.getHits().totalHits + " 条数据.");

    SearchHits hits = search.getHits();
    for (SearchHit hit : hits) {
      System.out.println(hit.getSourceAsString());
    }
  }

}


