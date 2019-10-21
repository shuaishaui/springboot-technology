package com.shuai.springbootwebsocket.websocket;

import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class MyWebSocket {

  @OnOpen
  public void onOpen(Session session) throws IOException {
    System.out.println("websocker已经连接" + session);
    // 给客户端响应
    session.getBasicRemote().sendText("欢迎登陆");
  }

  @OnClose
  public void onClose(Session session){
    System.out.println("websocket已经关闭" + session);
  }

  @OnMessage
  public void onMessage(String message, Session session) throws IOException {
    System.out.println("收到客户端发来的消息 --> " + message);

    try {
      Thread.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //给客户端一个反馈
    session.getBasicRemote().sendText("消息已收到");
  }
}
