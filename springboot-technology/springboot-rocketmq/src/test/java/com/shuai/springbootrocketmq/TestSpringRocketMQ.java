package com.shuai.springbootrocketmq;


import com.shuai.springbootrocketmq.springboot.SpringProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringRocketMQ {

    @Autowired
    private SpringProducer springProducer;



    @Test
    public void testSendMsg(){
        String msg = "我的第1个SpringRocketMQ消息!";
        this.springProducer.sendMsg("spring-my-topic", msg);
        System.out.println("发送成功");
    }


}
