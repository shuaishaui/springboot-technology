package com.shuai.activemq.listener;

import java.util.Random;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ObjectProducer {
  public void sendMessage(Object obj) throws JMSException {
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

    factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.44.151:61616");
    connection = factory.createConnection();

    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    destination = session.createQueue("test-listener");
    producer = session.createProducer(destination);
    connection.start();

    Random r = new Random();
    for (int i = 0; i < 100; i++) {
      Integer data = i;
      // 创建对象消息，消息中的数据载体是一个可序列化的对象
      message = session.createObjectMessage(data);
      producer.send(message);
    }
  }

  public static void main(String[] args) throws JMSException {
    ObjectProducer producer = new ObjectProducer();
    producer.sendMessage(null);
  }
}
