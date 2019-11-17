package com.shuai.activemq.first;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送一个字符串文本消息到ActiveMQ中 开发JMS相关代码过程中，使用的接口类型但是javax.jms包才的类型
 */
public class TextProducer {

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

    // 创建连接工厂：连接Activemq服务
    // 创建：构造方法有三个参数->用户名、密码、连接地址
    factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.44.151:61616");
    connection = factory.createConnection();
    connection.start();

    // 通过连接对象，创建会话对象
    /**
     * 创建会话的时候，需要传递两个参数：是否支持事务 + 如何确认消息处理
     * transacted -> 是否
     */
    session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE );
    destination = session.createQueue("first-mq");

    producer = session.createProducer(destination);
    message = session.createTextMessage(datas);
    producer.send(message);
    System.out.println("消息已发送");
  }

  public static void main(String[] args) throws JMSException {
    TextProducer producer = new TextProducer();
    producer.sendTextMessage("测试ActiveMq");
  }
}
