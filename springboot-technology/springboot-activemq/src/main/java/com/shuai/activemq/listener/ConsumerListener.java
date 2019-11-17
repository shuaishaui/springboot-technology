package com.shuai.activemq.listener;

import java.io.IOException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 使用监听的方式 实现消息的处理
 */
public class ConsumerListener {

  public void consumMessage() throws JMSException, IOException {
    ConnectionFactory factory = null;
    Connection connection = null;
    Session session = null;
    Destination destination = null;
    MessageConsumer consumer = null;
    Message message = null;

    factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.44.151:61616");
    connection = factory.createConnection();
    connection.start();
    session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
    destination = session.createQueue("test-listener");
    consumer = session.createConsumer(destination);

    // 注册监听器，注册成功后，队列中的消息变化会自动触发监听器代码，
    consumer.setMessageListener(new MessageListener() {
      /**
       *  监听器一旦注册，永久有效
       *  只要有消息未处理，自动调用onMessage方法去处理
       *  监听器可以注册若干，相当于集群
       */
      @Override
      public void onMessage(Message message) {
        ObjectMessage om = (ObjectMessage)message;
        try {
          // acknowledge就是确认方法，表示consumer已经收到消息，确定之后，mq会删除对应的消息
          message.acknowledge();
          Object data = om.getObject();
          System.out.println(data);
        } catch (JMSException e) {
          e.printStackTrace();
        }
      }
    });
    // 阻塞当前代码，保证listener代码一直运行，如果代码结束了，监听器就会关闭
    System.in.read();
  }

  public static void main(String[] args) throws IOException, JMSException {
    ConsumerListener listener = new ConsumerListener();
    listener.consumMessage();
  }
}
