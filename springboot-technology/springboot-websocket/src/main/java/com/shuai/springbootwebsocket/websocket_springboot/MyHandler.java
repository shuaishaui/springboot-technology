package com.shuai.springbootwebsocket.websocket_springboot;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 编写处理消息的具体业务逻辑
 */
@Component
public class MyHandler extends TextWebSocketHandler {

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws IOException {
    System.out.println("获取到消息 >> " + message.getPayload());

    // 向客户端发送消息
    session.sendMessage(new TextMessage("消息已收到"));

    if (message.getPayload().equals("10")) {
      for (int i = 0; i < 10; i++) {
        session.sendMessage(new TextMessage("消息 -> " + i));
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws
      Exception {
    // 这里的UID是在MyHandshakeInterceptor那里放入的
    Integer uid = (Integer) session.getAttributes().get("uid");
    session.sendMessage(new TextMessage(uid + ", 你好！欢迎连接到ws服务"));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
      throws Exception {
    System.out.println("断开连接！");
  }
}
