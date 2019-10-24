package com.shuai.springbootwebmagic.webmagic;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class MyPipeline implements Pipeline {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Override
  public void process(ResultItems resultItems, Task task) {
    System.out.println(resultItems);
  }
}
