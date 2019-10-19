package com.shuai.springbootrocketmq.topic;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class TopicDemo {

  public static void main(String[] args) throws MQClientException {
    DefaultMQProducer producer = new DefaultMQProducer("xshuai");

    // 设置nameserver的地址
    producer.setNamesrvAddr("192.168.44.149:9876");

    // 启动生产者
    producer.start();

    /**
     * 创建topic，参数分别是：broker的名称，topic的名称，queue的数量
     * broker的名称是在rmq里面的conf文件里设置的，
     */
    producer.createTopic("shuai", "topic1", 4);

    System.out.println("topic创建成功!");

    producer.shutdown();
  }

}
