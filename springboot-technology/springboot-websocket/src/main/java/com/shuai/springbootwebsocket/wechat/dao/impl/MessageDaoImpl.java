package com.shuai.springbootwebsocket.wechat.dao.impl;

import com.mongodb.client.result.UpdateResult;

import com.shuai.springbootwebsocket.wechat.dao.MessageDao;
import com.shuai.springbootwebsocket.wechat.pojo.Message;
import java.util.Date;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MessageDaoImpl implements MessageDao {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer rows) {
    // 用户A发送给用户B的条件
    Criteria criteriaFrom = new Criteria().andOperator(
        Criteria.where("from.id").is(fromId),
        Criteria.where("to.id").is(toId)
    );

    // 用户B发送给用户A的条件
    Criteria criteriaTo = new Criteria().andOperator(
        Criteria.where("from.id").is(toId),
        Criteria.where("to.id").is(fromId)
    );

    Criteria criteria = new Criteria().orOperator(criteriaFrom, criteriaTo);

    PageRequest pageRequest = PageRequest.of(page - 1, rows, Sort.by(Sort.Direction.ASC, "sendDate"));

    // 设置查询条件，分页
    Query query = Query.query(criteria).with(pageRequest);

    return this.mongoTemplate.find(query, Message.class);
  }

  @Override
  public Message findMessageById(String id) {
    return null;
  }

  @Override
  public UpdateResult updateMessageState(ObjectId id, Integer status) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = Update.update("status", status);
    if (status.intValue() == 1) {
      update.set("send_date", new Date());
    } else if (status.intValue() == 2) {
      update.set("read_date", new Date());
    }
    return this.mongoTemplate.updateFirst(query, update, Message.class);

  }

  @Override
  public Message saveMessage(Message message) {
    // 写入发送时间
    message.setSendDate(new Date());
    message.setStatus(1);
    message.setId(ObjectId.get());
    return this.mongoTemplate.save(message);
  }
}
