package com.shuai.springbootrocketmq.springboot.transaction;

import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;

/**
 * 事务消息
 * @RocketMQTransactionListener 它里面包含 @Component , 所以不用再加
 */
@RocketMQTransactionListener(txProducerGroup = "myTransactionGroup")
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

  private static Map<String, RocketMQLocalTransactionState> STATE_MAP = new HashMap<>();


  /**
   * 执行业务逻辑
   */
  @Override
  public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
    // 获取事务Id
    String transId = (String)message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);
    try {
      System.out.println("执行操作1");
      Thread.sleep(500);

      System.out.println("执行操作2");
      Thread.sleep(800);

      // 可以改变RocketMQLocalTransactionState的三个值分别进行测试
      STATE_MAP.put(transId, RocketMQLocalTransactionState.COMMIT);

      return RocketMQLocalTransactionState.UNKNOWN;

    } catch (Exception e) {
      e.printStackTrace();
    }

    STATE_MAP.put(transId, RocketMQLocalTransactionState.ROLLBACK);
    return RocketMQLocalTransactionState.ROLLBACK;
  }

  /**
   * 回查
   */
  @Override
  public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
    String transId = (String)message.getHeaders().get(RocketMQHeaders.TRANSACTION_ID);

    System.out.println("回查消息 -> transId = " + transId + ", state = " + STATE_MAP.get(transId));

    return STATE_MAP.get(transId);
  }
}
