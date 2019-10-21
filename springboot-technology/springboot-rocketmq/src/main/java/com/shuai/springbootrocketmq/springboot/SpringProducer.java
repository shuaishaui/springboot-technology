package com.shuai.springbootrocketmq.springboot;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * springboot整合rocketmq
 */
@Component
public class SpringProducer {

  @Autowired
  private RocketMQTemplate rocketMQTemplate;

  @Autowired
  private DefaultMQProducer defaultMQProducer;

  public void sendMsg(String topic,String msg){

    rocketMQTemplate.convertAndSend(topic,msg);
  }
}
