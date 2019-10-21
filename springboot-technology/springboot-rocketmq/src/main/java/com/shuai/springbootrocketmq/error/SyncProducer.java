package com.shuai.springbootrocketmq.error;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 这里直接运行会报超时错误，所以看一下流程了解一下就好了
 * 因为在源码里面存在这个，直接break，而不continue下一次重试
 * if (timeout < costTime) {
 *     callTimeout = true;
 *     break;
 *         }
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("xshuai");
        producer.setNamesrvAddr("192.168.44.149:9876");

        //消息发送失败时，重试3次
        producer.setRetryTimesWhenSendFailed(3);

        producer.start();
        String msgStr = "用户A发送消息给用户B";
        Message msg = new Message("shuai", "SEND_MSG",
                msgStr.getBytes(RemotingHelper.DEFAULT_CHARSET));

        // 发送消息,并且指定超时时间
        SendResult sendResult = producer.send(msg, 1000);

        System.out.println("消息状态：" + sendResult.getSendStatus());
        System.out.println("消息id：" + sendResult.getMsgId());
        System.out.println("消息queue：" + sendResult.getMessageQueue());
        System.out.println("消息offset：" + sendResult.getQueueOffset());
        System.out.println(sendResult);

        producer.shutdown();
    }
}
