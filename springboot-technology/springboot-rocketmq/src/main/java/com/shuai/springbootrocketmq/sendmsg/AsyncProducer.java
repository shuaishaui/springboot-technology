package com.shuai.springbootrocketmq.sendmsg;


import java.io.UnsupportedEncodingException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 异步消息
 */
public class AsyncProducer {

  public static void main(String[] args)
      throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
    DefaultMQProducer producer = new DefaultMQProducer("xshuai");
    producer.setNamesrvAddr("192.168.44.149:9876");
    producer.start();

    // 发送消息
    String msg = "我的第一个异步消息";
    // 搜嘎  这里的tags相当于一个标签，然后消费者在读取消息的时候，可以设置读取对应标签的信息
    Message message = new Message("shuai","asyncMessage",msg.getBytes("UTF-8"));
    producer.send(message, new SendCallback() {
      @Override
      public void onSuccess(SendResult sendResult) {
        System.out.println("消息id：" + sendResult.getMsgId());
        System.out.println("消息队列：" + sendResult.getMessageQueue());
        System.out.println("消息offset值：" + sendResult.getQueueOffset());
        System.out.println(sendResult);
      }

      @Override
      public void onException(Throwable e) {
        System.out.println("消息发送失败：" + e);
      }
    });
//    producer.shutdown(); 不能紧接着shutdown
  }

}
