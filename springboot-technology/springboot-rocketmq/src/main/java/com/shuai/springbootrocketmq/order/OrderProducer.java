package com.shuai.springbootrocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 *  顺序消息，保证所有消息在一个消息队列中
 */
public class OrderProducer {
  public static void main(String[] args) throws Exception {
    DefaultMQProducer producer = new DefaultMQProducer("xshuai");
    producer.setNamesrvAddr("192.168.44.149:9876");
    producer.start();
    for (int i = 0; i < 100; i++) {
      // 模拟生成订单id
      int orderId = i % 10;
      String msgStr = "order --> " + i +", id = "+ orderId;
      Message message = new Message("shuai", "orderMessage",
          msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));

      //  send(Message msg, MessageQueueSelector selector, Object arg)
      // MessageQueueSelector来选择消息队列
      // (mqs, msg, arg) 是一个匿名实现类  而且注意这里的lambda表达式写法  orderID是会传递给里面的
      SendResult sendResult = producer.send(message, (mqs, msg, arg) -> {
        Integer id = (Integer) arg;
        int index = id % mqs.size();
        return mqs.get(index);
      }, orderId);
      System.out.println(sendResult);
    }
    producer.shutdown();
  }

}
