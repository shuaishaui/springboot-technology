package com.shuai.springbootrocketmq.springboot;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Component
@RocketMQMessageListener(
    topic = "spring-my-topic",
    consumerGroup = "shuai",
    selectorExpression = "*",
    consumeMode = ConsumeMode.CONCURRENTLY
)
public class SpringConsumer implements RocketMQListener<String> {


  @Override
  public void onMessage(String s) {
    System.out.println("接收到的消息 -》 " + s);
  }
}
