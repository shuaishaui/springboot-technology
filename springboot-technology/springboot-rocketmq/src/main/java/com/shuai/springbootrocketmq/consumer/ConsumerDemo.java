package com.shuai.springbootrocketmq.consumer;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消费者  有两种：1.主动去获取消息  2.由server主动推给它
 */
public class ConsumerDemo {

  public static void main(String[] args) throws MQClientException {
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xshuai");
    consumer.setNamesrvAddr("192.168.44.149:9876");

    // 订阅消息 这里设置为接收所有消息
//    consumer.subscribe("shuai","*" );

    // 增加消息的标签匹配  比如这里就会只读取mytags标签的消息
    consumer.subscribe("shuai","mytags" );

    // 拿到消息  先注册一个消息监听  有两种参数
    // 1.MessageListenerOrderly 顺序消息   2.MessageListenerConcurrently 并发消息
    consumer.registerMessageListener(new MessageListenerConcurrently() {
      @Override
      public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
          ConsumeConcurrentlyContext context) {
        try {
          for (MessageExt msg : msgs) {
            System.out.println("消息:" + new String(msg.getBody(), "UTF-8"));
          }
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        System.out.println("接收到消息 -> " + msgs);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
      }
    });

    consumer.start();
  }
}
