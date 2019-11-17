package com.shuai.activemq.topic;

import com.shuai.activemq.first.TextProducer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *  发布订阅模式
 */
public class TopicProducer {
  public void sendTextMessage(String datas) throws JMSException {
    // 连接工厂
    ConnectionFactory factory = null;
    // 连接
    Connection connection = null;
    // 目的地
    Destination destination = null;
    // 会话
    Session session = null;
    // 消息生产者
    MessageProducer producer = null;
    // 消息对象
    Message message = null;

    factory = new ActiveMQConnectionFactory("guest", "guest", "tcp://192.168.44.151:61616");

    connection = factory.createConnection();
    connection.start();

    session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE );
    destination = session.createQueue("test-topic");

    producer = session.createProducer(destination);
    message = session.createTextMessage(datas);
    producer.send(message);
    System.out.println("消息已发送");
  }

  public static void main(String[] args) throws JMSException {
    TextProducer producer = new TextProducer();
    producer.sendTextMessage("测试ActiveMq的发布订阅");
  }
}



