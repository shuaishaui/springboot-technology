package com.shuai.springbootrocketmq.sendmsg;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 同步发送消息
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("xshuai");

        producer.setNamesrvAddr("192.168.44.149:9876");

        producer.start();

        //发送消息
        String msg = "这是一个同步消息";
        Message message = new Message("shuai", "syncMessage", msg.getBytes("UTF-8"));
        SendResult sendResult = producer.send(message);
        System.out.println("消息id：" + sendResult.getMsgId());
        System.out.println("消息队列：" + sendResult.getMessageQueue());
        System.out.println("消息offset值：" + sendResult.getQueueOffset());
        System.out.println(sendResult);

        producer.shutdown();
    }
}
