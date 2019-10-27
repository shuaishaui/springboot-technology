package com.shuai.springbootslf4j.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {

  private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);

  public static void main(String[] args) {
    LOGGER.info("这是info日志" );
    System.out.println("测试代码");
    LOGGER.error("这是error日志");
  }

}
