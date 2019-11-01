## 1. 简介 

​         Netty 是由 JBOSS 提供的一个 java 开源框架。Netty 提供异步的、事件驱动的网络应用 程序框架和工具，用以快速开发高性能、高可靠性的网络服务器和客户端程序。	
​         Netty 是一个基于 NIO 的客户、服务器端编程框架
​       

## 2. 线程模型 

Netty 中支持单线程模型，多线程模型，主从多线程模型 ==> 都是针对服务端
主从多线程模型 :
         在 ServerBootstrap 调用方法 group 的时候，传递的参数是两个不同的线程组。负责监听 的 acceptor 线程组，线程数大于 1，也就是构造参数大于 1。负责处理客户端任务的线程组， 线程数大于 1，也就是构造参数大于 1。这种开发方式，就是主从多线程模型。 长连接，客户端数量相对较多，连接持续时间比较长的情况下使用。如：对外提供服务 的相册服务器。如下是主从多线程模型，可以更改指定acceptorGroup = new NioEventLoopGroup(1);
clientGroup = new NioEventLoopGroup(1或者2);
bootstrap.group(acceptorGroup, acceptorGroup); 
来指定为单线程模型或多线程模型
        在 ServerBootstrap 调用方法 group 的时候，传递的参数是同一个线程组，且在构造线程 组的时候，构造参数为 1，这种开发方式，就是一个单线程模型。 
        在 ServerBootstrap 调用方法 group 的时候，传递的参数是两个不同的线程组。负责监听 的 acceptor 线程组，线程数为 1，也就是构造参数为 1。负责处理客户端任务的线程组，线 程数大于 1，也就是构造参数大于 1。这种开发方式，就是多线程模型

​         <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572425489450.png" alt="1572425489450" style="zoom:33%;" /> 

## 3. 详解

ByteBuf：
writeAndFlush:

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572422445429.png" alt="1572422445429" style="zoom:50%;" />

@Sharble:
        <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572423218115.png" alt="1572423218115" style="zoom: 50%;" />

## 4. 拆包粘包问题解决 

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572427614540.png" alt="1572427614540" style="zoom: 33%;" />

4.2.1 定长数据流 
客户端和服务器，提前协调好，每个消息长度固定。（如：长度 10）。如果客户端或服 务器写出的数据不足 10，则使用空白字符补足，两个端都要定

然后哈，注意，拿这里来说，定长为3，当客户端消息不足3时，服务端是不会响应的，直到多条消息达到了3才响应，当超出3时，会以3为一组进行响应，不足3的继续等待

​           <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572428440259.png" alt="1572428440259" style="zoom:33%;" /> 

​                <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572428504869.png" alt="1572428504869" style="zoom:33%;" />

4.2.2 特殊结束符 
客户端和服务器，协商定义一个特殊的分隔符号，分隔符号长度自定义。如：‘#’、‘$_$’、 ‘AA@’。在通讯的时候，只要没有发送分隔符号，则代表一条数据没有结束。 
          <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572429857762.png" alt="1572429857762" style="zoom:33%;" />

4.2.3 协议 
相对最成熟的数据传递方式。有服务器的开发者提供一个固定格式的协议标准。客户端 和服务器发送数据和接受数据的时候，都依据协议制定和解析消息。 
         这里可以自己写一个协议
          <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572430530556.png" alt="1572430530556" style="zoom:33%;" />


