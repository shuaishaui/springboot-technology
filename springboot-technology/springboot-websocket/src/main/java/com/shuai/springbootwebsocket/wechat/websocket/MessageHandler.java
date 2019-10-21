package com.shuai.springbootwebsocket.wechat.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.shuai.springbootwebsocket.wechat.dao.MessageDao;
import com.shuai.springbootwebsocket.wechat.pojo.Message;
import com.shuai.springbootwebsocket.wechat.pojo.UserData;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MessageHandler extends TextWebSocketHandler {
  @Autowired
  private MessageDao messageDAO;

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final Map<Long, WebSocketSession> SESSIONS = new HashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    Long uid = (Long) session.getAttributes().get("uid");
    // 将当前用户的session放置到map中，后面会使用相应的session通信
    SESSIONS.put(uid, session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage
      textMessage) throws Exception {
    Long uid = (Long) session.getAttributes().get("uid");

    System.out.println("textMessage<==>" + textMessage + "<==>textMessage");
    System.out.println("textMessage.getPayload()<==>" + textMessage.getPayload() + "<==>textMessage.getPayload()");


    JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());

    System.out.println("jsonNode<==>" + jsonNode + "<==>jsonNode");

    Long toId = jsonNode.get("toId").asLong();
    String msg = jsonNode.get("msg").asText();

    Message message = Message.builder()
        .from(UserData.USER_MAP.get(uid))
        .to(UserData.USER_MAP.get(toId))
        .msg(msg)
        .build();

    // 将消息保存到MongoDB
    message = this.messageDAO.saveMessage(message);

    // 判断to用户是否在线
    WebSocketSession toSession = SESSIONS.get(toId);
    if (toSession != null && toSession.isOpen()) {
      //TODO 具体格式需要和前端对接
      toSession.sendMessage(new
          TextMessage(MAPPER.writeValueAsString(message)));
      // 更新消息状态为已读
      this.messageDAO.updateMessageState(message.getId(), 2);
    }

  }
}
