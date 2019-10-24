在这个工程里面，分为三部分：

+ websocket的原生java代码
+ websocket与springboot的整合
+ 结合实现的一个简易通信

这两部分的websocket依赖不一样，而且会有冲突，在pom文件里面我有声明，大家在测试的时候需要仔细一点，为了简便，还引入了`lombok`

`websocket`：对websocket的原生java代码进行测试

在这里我是使用Tomcat的方式进行启动，所以在pom文件里面我引入了Tomcat的插件，并且配置端口号为8082，使用idea，启动的时候点击`Maven Project`-->`plugins`-->`tomcat7`-->`tomcat7:run`,然后就可以直接运行这个`MyWebSocket`

```java
 <!-- 配置Tomcat插件 -->
<plugin>       <groupId>org.apache.tomcat.maven</groupId>
<artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
          <port>8082</port>
          <path>/</path>
        </configuration>
      </plugin>
```

websocket的相关注解说明

- @ServerEndpoint("/websocket/{uid}") 申明这是一个websocket服务 需要指定访问该服务的地址，在地址中可以指定参数，需要通过{}进行占位 
- @OnOpen 用法：public void onOpen(Session session, 
- @PathParam("uid") String uid) throws IOException{} 该方法将在建立连接后执行，会传入session对象，就是客户端与服务端建立的长连接通道 通过
- @PathParam获取url申明中的参数 
- @OnClose 用法：public void onClose() {} 该方法是在连接关闭后执行 
- @OnMessage 用法：public void onMessage(String message, Session session) throws IOException {} 该方法用于接收客户端发来的消息 message：发来的消息数据 session：会话对象（也是通道） 发送消息到客户端 用法：session.getBasicRemote().sendText("你好"); 通过session进行发送

`websocket_springboot`：这个里面是springboot与websocket的整合，如上所述，需要注意pom依赖

+ 编写handler类：处理消息的具体业务逻辑，需要实现TextWebSocketHandler接口，重写`handleTextMessage`、`afterConnectionEstablished`、`afterConnectionClosed`

+ 编写config类：`WebSocketConfig implements WebSocketConfigurer `

+ 编写websocket拦截器：` MyHandshakeInterceptor implements HandshakeInterceptor `

`wechat`：这个里面是springboot+websocket+MongoDB开发的一个简易通信，需要在配置文件中写入自己的MongoDB的配置指明

```java
spring.data.mongodb.uri=mongodb://192.168.44.149:27017/testdb
```

