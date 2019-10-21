package com.shuai.springbootwebsocket.websocket_springboot;

import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor {

  /**
   * 握手之前，若返回false，则不建立链接
   *
   * @param request
   * @param response
   * @param wsHandler
   * @param attributes
   * @return
   * @throws Exception
   */
  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse
      response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws
      Exception {
    //将用户id放入socket处理器的会话(WebSocketSession)中
    attributes.put("uid", 1001);
    System.out.println("开始握手。。。。。。。");
    return true;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
      response, WebSocketHandler wsHandler, Exception exception) {
    System.out.println("握手成功啦。。。。。。");
  }
}
