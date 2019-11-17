package com.shuai.activemq.first;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TextConsumer {

  public String receiveTextMessage() throws JMSException {
    String resultCode = "";
    ConnectionFactory factory = null;
    Connection connection = null;
    Session session = null;
    Destination destination = null;
    MessageConsumer consumer = null;
    Message message = null;

    factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.44.151:61616");
    connection = factory.createConnection();
    connection.start();
    session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE );
    destination = session.createQueue("first-mq");
    consumer = session.createConsumer(destination);

    message = consumer.receive();
    resultCode = ((TextMessage)message).getText();

    return resultCode;
  }

  public static void main(String[] args) throws JMSException {
    TextConsumer consumer = new TextConsumer();
    System.out.println("接收到的消息为：" + consumer.receiveTextMessage());

  }
}
