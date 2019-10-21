package com.shuai.springbootwebsocket.wechat.service;


import com.shuai.springbootwebsocket.wechat.dao.MessageDao;
import com.shuai.springbootwebsocket.wechat.pojo.Message;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

  @Autowired
  private MessageDao messageDAO;

  public List<Message> queryMessageList(Long fromId, Long toId, Integer page, Integer rows) {
    List<Message> list = this.messageDAO.findListByFromAndTo(fromId, toId, page, rows);
    for (Message message : list) {
      if (message.getStatus().intValue() == 1) {
        // 修改消息状态为已读
        this.messageDAO.updateMessageState(message.getId(), 2);
      }
    }
    return list;
  }
}
