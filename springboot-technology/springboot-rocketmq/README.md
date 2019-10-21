这个是rocketmq的实例，在虚拟机上安装rocketmq并运行的实例可以参考这篇博客

 https://blog.csdn.net/shuaishuai5213/article/details/102628936

阅读顺序如下，其中每一个包都可以单独运行：

`topic`：建立第一个topic

`sendmsg`：发送消息

+ `AsyncProducer`：异步消息
+ `SyncProducer`：同步消息

`consumer`：消费消息

`filter`：消息过滤器

注意这里可能会报错

```java
Exception in thread "main" org.apache.rocketmq.client.exception.MQClientException: CODE: 1  DESC: The broker does not support consumer to filter message by SQL92 For more information, please visit the url, http://rocketmq.apache.org/docs/faq/    at org.apache.rocketmq.client.impl.MQClientAPIImpl.checkClientInBroker(MQClientAPIImpl.j ava:2089)    at org.apache.rocketmq.client.impl.factory.MQClientInstance.checkClientInBroker(MQClient Instance.java:432)    at org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl.start(DefaultMQPus hConsumerImpl.java:633)    at org.apache.rocketmq.client.consumer.DefaultMQPushConsumer.start(DefaultMQPushConsumer .java:520)    at cn.itcast.rocketmq.ConsumerFilterDemo.main(ConsumerFilterDemo.java:39)
```

原因是默认配置下，不支持自定义属性，需要设置开启:

```java
#加入到broker的配置文件中 enablePropertyFilter=true
```

`order`：产生顺序消息，即让产生的消息落入同一队列，这里使用一个简单的订单id来模拟

`transaction`：分布式事务消息，这里使用一个银行转账demo来进行模拟
`error`：重试策略

+ 这里分为producer端重试和consumer端重试。 
+ 两个重试策略不一样，可以结合代码去了解一个



`springboot`：springboot与rocketmq的整合

注意这里，我注释掉了之前的rocketmq-client依赖

```java
<dependency>
   <groupId>org.apache.rocketmq</groupId>
   <artifactId>rocketmq-client</artifactId>
   <version>4.3.2</version>
</dependency>
```

因为这个依赖会和rocketmq-spring-boot-starter依赖产生冲突，有需要的可以自己加上去，但是只能留一个，可以看一下我写的博客

[点击这里]: https://blog.csdn.net/shuaishuai5213/article/details/102665133



+ 这个里面的`transaction`包是事务消息包，具体的内容参考代码

