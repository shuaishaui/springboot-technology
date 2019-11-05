1 Destination
目的地，JMS Provider（消息中间件）负责维护，用于对 Message 进行管理的对象。 MessageProducer 需要指定 Destination 才能发送消息， MessageConsumer需要指定Destination 才能接收消息。
2 Producer
消息生成者(客户端、生成消息)，负责发送 Message 到目的地。应用接口为 MessageProducer。在 JMS 规范中，所有的标准定义都在 javax.jms 包中。
3 Consumer【Receiver】
消息消费者（处理消息），负责从目的地中消费【处理|监听|订阅】Message。应用接口 为 MessageConsumer
4 Message
消息（Message），消息封装一次通信的内容。常见类型有： StreamMessage、 BytesMessage、 TextMessage、ObjectMessage、MapMessage。
5 ConnectionFactory
链接工厂, 用于创建链接的工厂类型。 注意，不能和 JDBC 中的 ConnectionFactory 混
淆。
6 Connection
链接. 用于建立访问 ActiveMQ 连接的类型, 由链接工厂创建. 注意，不能和 JDBC 中的 Connection 混淆。
7 Session
会话, 一次 持 久 有 效 有 状 态 的访问. 由链接创建. 是具体操作消息的基础支撑。
8 Queue&Topic
Queue 是队列目的地，Topic 是主题目的地。都是 Destination 的子接口。 Queue 特点： 队列中的消息，默认只能由唯一的一个消费者处理。一旦处理消息删除。 Topic 特点：主题中的消息，会发送给所有的消费者同时处理。只有在消息可以重复处 理的业务场景中可使用。
9 PTP
PointtoPoint。点对点消息模型。就是基于 Queue 实现的消息处理方式。
10 PUB&SUB
Publish&Subscribe 。消息的发布/订阅模型。是基于 Topic 实现的消息处理方式。



## 1. 安装

需要关注的配置文件有:activemq.xml,jetty.xml,users.properties 任何配置文件修改后,必须重启 ActiveMQ,才能生效.

配置文件简介
/usr/local/activemq/conf/* - 配置文件. 需要关注的配置文件有:activemq.xml,jetty.xml,users.properties 任何配置文件修改后,必须重启 ActiveMQ,才能生效.
6.1 activemq.xml
就是 spring 配置文件. 其中配置的是 ActiveMQ 应用使用的默认对象组件. transportConnectors 标签 - 配置链接端口信息的. 其中的端口号 61616 是 ActiveMQ 对 外发布的 tcp 协议访问端口. 就是 java 代码访问 ActiveMQ 时使用的端口.
6.2 jetty.xml
spring 配置文件, 用于配置 jetty 服务器的默认对象组件. jetty 是类似 tomcat 的一个中间件容器. ActiveMQ 默认支持一个网页版的服务查看站点. 可以实现 ActiveMQ 中消息相关数据 的页面查看. 8161 端口, 是 ActiveMQ 网页版管理站点的默认端口. 在 ActiveMQ 网页版管理站点中,需要登录, 默认的用户名和密码都是 admin.
6.3 users.properties
内容信息: 用户名=密码 是用于配置客户端通过协议访问 ActiveMQ 时,使用的用户名和密码.
7 启动 ActiveMQ
/usr/local/activemq/bin/activemqstart

## 2. 启动

还是apache-activemq-5.9.0目录进行启动：`bin/activemq start`
进入bin目录之后使用`avtivemq start`启动不了
jps进行查看
<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572845618827.png" alt="1572845618827" style="zoom:50%;" />

jps(Java Virtual Machine Process Status Tool)是[JDK](https://www.baidu.com/s?wd=JDK&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao) [1.5](https://www.baidu.com/s?wd=1.5&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)提供的一个显示当前所有java进程pid的命令，简单实用，非常适合在linux/unix平台上简单察看当前java进程的一些简单情况。

## 三. ActiveMQ 应用

## 1. PTP 处理模式（Queue）

点对点

消息生产者生产消息发送到 queue 中，然后消息消费者从 queue 中取出并且消费消息。 消息被消费以后，queue 中不再有存储，所以消息消费者不可能消费到已经被消费的消息。
Queue 支持存在多个消费者，但是对一个消息而言，只会有一个消费者可以消费、其它 的则不能消费此消息了。 当消费者不存在时，消息会一直保存，直到有消费消费

```
factory = new ActiveMQConnectionFactory("admin","admin","tcp://192.168.44.151:61616");
```

![屏幕截图_3](D:\markdown笔记\尚学堂\截图\屏幕截图_3.png)

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572855242971.png" alt="1572855242971" style="zoom:45%;" />

## 2. Publish/Subscribe 处理模式（Topic）

发布订阅

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572855261955.png" alt="1572855261955" style="zoom:45%;" />

消息生产者（发布）将消息发布到 topic 中，同时有多个消息消费者（订阅）消费该消息。
和点对点方式不同，发布到 topic 的消息会被所有订阅者消费。 当生产者发布消息，不管是否有消费者。都不会保存消息 一定要先有消息的消费者，后有消息的生产者。

## 3. PTP 和 PUB/SUB 简单对比

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572856711912.png" alt="1572856711912" style="zoom:50%;" />

## 4.  ActiveMQ 安全认证

​        ActiveMQ 也提供了安全认证。就是用户名密码登录规则。ActiveMQ 如果需要使用安全 认 证 的 话 ， 必 须 在 activemq 的 核 心 配 置 文 件 中 开 启 安 全 配 置 。 配 置 文 件 就 是 conf/activemq.xml
​        在 conf/activemq.xml 配置文件的 broker 标 签 中增加下述内容。 <jaasAuthenticationPlugin configuration="activemq" />指定了使用 JAAS 插件管理权限， 至于 configuration="activemq"是在 login.conf 文件里定义的 <authorizationEntry topic="名字" read="用户组名" write="用户组名" admin="用户组名" />指定了具体的 Topic/Queue 与用户组的授权关系 <authorizationEntry topic="ActiveMQ.Advisory.>" read="admins" write="admins" admin="admins"/>这个是必须的配置，不能少

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572873718634.png" alt="1572873718634" style="zoom:45%;" />

没有这样设置之前，代码连接activemq里面的用户名和密码可以随便写，但是设置之后，只能使用我们设置好了的

## 5. ActiveMQ 的持久化

​        ActiveMQ 中，持久化是指对消息数据的持久化。在 ActiveMQ 中，默认的消息是保存 在内存中的。当内存容量不足的时候，或 ActiveMQ 正常关闭的时候，会将内存中的未处理的消息持久化到磁盘中。具体的持久化策略由配置文件中的具体配置决定。 ActiveMQ 的默认存储策略是 kahadb。如果使用 JDBC 作为持久化策略，则会将所有的 需要持久化的消息保存到数据库中。 所有的持久化配置都在 conf/activemq.xml 中配置，配置信息都在 broker 标签内部定义

### 1. kahadb 方式

是 ActiveMQ 默认的持久化策略。kahadb 是一个文件型数据库。是使用内存+文件保证 数据的持久化的。kahadb 可以限制每个数据文件的大小。不代表总计数据容量
<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572874891067.png" alt="1572874891067" style="zoom:50%;" />

### 2. AMQ 方式

### 3. JDBC 持久化方式

​	

## 6.  API 简介

具体PDF

### 1. ProducerAPI 简介

#### 1.1 发送消息

MessageProducer. send(Message message);发送消息到默认目的地，就是创建 Producer 时指定的目的
地。
send(Destination destination, Message message); 发送消息到指定目的地，Producer 不建议绑定目的地。也就是创建 Producer 的时候，不绑定目的地。 session.createProducer(null)。 send(Message message, int deliveryMode, int priority,long timeToLive);发送消息到默 认目的地，且设置相关参数。deliveryMode-持久化方式（DeliveryMode.PERSISTENT| DeliveryMode.NON_PERSISTENT）。priority-优先级。timeToLive-消息有效期（单位毫秒） 。 send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive); 发送消息到指定目的地，且设置相关参数。

### 2. ConsumerAPI 简介

#### 2.1 消息的确认

Consumer 拉取消息后，如果没有做确认 acknowledge，此消息不会从 MQ 中删除。 消息的如果拉去到 consumer 后，未确认，那么消息被锁定。如果 consumer 关闭的时候 仍旧没有确认消息，则释放消息锁定信息。消息将发送给其他的 consumer 处理。 消息一旦处理，应该必须确认。类似数据库中的事务管理机制。

