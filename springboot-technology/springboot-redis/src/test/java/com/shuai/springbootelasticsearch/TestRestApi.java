package com.shuai.springbootelasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Java Low Level REST Client 的测试
 */
public class TestRestApi {

  private RestClient restClient;
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Before
  public void init() {
    // 搭建的集群
    RestClientBuilder restClientBuilder = RestClient.builder(
        new HttpHost("192.168.44.151", 9200),
        new HttpHost("192.168.44.151", 9201),
        new HttpHost("192.168.44.151", 9202)
    );
    restClient = restClientBuilder.build();
  }
  @After
  public void close() throws Exception{
    // 跟随应用的关闭而关闭
    this.restClient.close();
  }

  @Test
  public void testInfo() throws IOException {
    Request request = new Request("GET", "/_cluster/state");
    request.addParameter("pretty", "true");
    Response response = this.restClient.performRequest(request);

    System.out.println("请求完成 -> "+response.getStatusLine());
    System.out.println(EntityUtils.toString(response.getEntity()));

  }

  /**
   * 保存数据
   * @throws Exception
   */
  @Test
  public void testSave() throws Exception{
    Request request = new Request("POST", "/shuai/house");
    request.addParameter("pretty", "true");

    // 构造数据
    Map<String, Object> data = new HashMap<>();
    data.put("id", 2001);
    data.put("title", "南京西路 一室一厅");
    data.put("price", 3500);

    String json = MAPPER.writeValueAsString(data);
    request.setJsonEntity(json);

    Response response = this.restClient.performRequest(request);
    System.out.println("请求完成 -> "+response.getStatusLine());
    System.out.println(EntityUtils.toString(response.getEntity()));

  }

  // 根据id查询数据
  @Test
  public void testQueryData() throws IOException {
    Request request = new Request("GET", "/haoke/house/j5vaHGgBqiMwr19H-k63");
    request.addParameter("pretty", "true");

    Response response = this.restClient.performRequest(request);

    System.out.println(response.getStatusLine());
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  // 搜索数据
  @Test
  public void testSearchData() throws IOException {
    Request request = new Request("POST", "/haoke/house/_search");
    String searchJson = "{\"query\": {\"match\": {\"title\": \"拎包入住\"}}}";
    request.setJsonEntity(searchJson);
    request.addParameter("pretty","true");
    Response response = this.restClient.performRequest(request);
    System.out.println(response.getStatusLine());
    System.out.println(EntityUtils.toString(response.getEntity()));
  }


  /**
   * 批量导入数据
   * @throws Exception
   */
  @Test
  public void testSave2() throws Exception{
    Request request = new Request("POST", "/haoke/house/_bulk");

    StringBuilder sb = new StringBuilder();
    String createStr = "{\"index\":{\"_index\":\"haoke\",\"_type\":\"house\"}}";
    List<String> lines = FileUtils.readLines(new File("D:\\code\\data.json"), "UTF-8");

    int count = 0;
    for (String line : lines) {

      sb.append(createStr + "\n");
      sb.append(line + "\n");

      if(count >= 200){

        request.setJsonEntity(sb.toString());
        this.restClient.performRequest(request);

        count = 0;
        sb = new StringBuilder();
      }

      count++;

    }

    if(!sb.toString().isEmpty()){
      request.setJsonEntity(sb.toString());
      this.restClient.performRequest(request);
    }

  }

}
