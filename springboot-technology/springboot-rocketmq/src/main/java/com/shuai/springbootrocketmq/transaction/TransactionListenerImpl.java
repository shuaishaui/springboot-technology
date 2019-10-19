package com.shuai.springbootrocketmq.transaction;

import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务监听
 */
public class TransactionListenerImpl implements TransactionListener {

    private static Map<String, LocalTransactionState> STATE_MAP = new HashMap<>();

    /**
     * 执行具体的业务逻辑
     *
     * @param msg 发送的消息对象
     * @param arg
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            System.out.println("用户A账户减500元.");
            Thread.sleep(500); //模拟调用服务

//             System.out.println(1/0);  // 这里是了测试代码出错

            System.out.println("用户B账户加500元.");
            Thread.sleep(800);


            STATE_MAP.put(msg.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);


            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        STATE_MAP.put(msg.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
        // 回滚
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    /**
     * 消息回查
     *
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("状态回查 ---> " + msg.getTransactionId() +" " +STATE_MAP.get(msg.getTransactionId()) );
        return STATE_MAP.get(msg.getTransactionId());
    }
}