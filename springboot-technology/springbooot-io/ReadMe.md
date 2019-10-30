这个工程主要介绍nio、bio、aio的使用，分别对应各自的三个包，每个包下都有各自的服务端server和客户端client，可以直接运行。

## 1. 网络编程（Socket）概念 

首先注意， Socket不是Java中独有的概念，而是一个语言无关标准。 任何可以实现网络编程的编程语言都有 Socket 

**网络上的两个程序通过一个双向的通信连接实现数据的交换，这个连接的一端称为一个 socket。 建立网络通信连接至少要一个端口号。socket 本质是编程接口(API)，对 TCP/IP 的封装， TCP/IP 也要提供可供程序员做网络开发所用的接口，这就是 Socket 编程接口；HTTP 是轿车， 提供了封装或者显示数据的具体形式；Socket 是发动机，提供了网络通信的能力**

三次握手、四次挥手
注意这里的8/9是一起发过去的，回应client关闭/server关闭信息，然后到了第十步，server端已经关闭，所以10是收不到的，但是还是要发

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572337218661.png" alt="1572337218661" style="zoom:33%;" />

根据连接启动的方式以及本地套接字要连接的目标，套接字之间的连接过程可以分为三 个步骤：服务器监听，客户端请求，连接确认。【如果包含数据交互+断开连接，那么一共是 五个步骤】 

Java 中的 Socket 
在 java.net 包是网络编程的基础类库。其中 ServerSocket 和 Socket 是网络编程的基础类 型。ServerSocket 是服务端应用类型。Socket 是建立连接的类型。当连接建立成功后，服务 器和客户端都会有一个 Socket 对象示例，可以通过这个 Socket 对象示例，完成会话的所有 操作。 
ServerSocket  -->  服务端 server                 Socket ---->  客户端client

## 2. 同步和异步 

​        同步和异步是针对应用程序和内核的交互而言的，同步指的是用户进程触发 IO 操作并 等待或者轮询的去查看 IO 操作是否就绪，而异步是指用户进程触发 IO 操作以后便开始做自 己的事情，而当 IO 操作已经完成的时候会得到 IO 完成的通知。 
​        以银行取款为例： 
同步 ： 自己亲自出马持银行卡到银行取钱(使用同步 IO 时，Java 自己处理 IO 读写)；
异步 ： 委托一小弟拿银行卡到银行取钱，然后给你（使用异步 IO 时，Java 将 IO 读写 委托给 OS 处理，需要将数据缓冲区地址和大小传给 OS(银行卡和密码)，OS 需要支持异步 IO 操作 API）；

## 3. 阻塞和非阻塞 

​        阻塞和非阻塞是针对于进程在访问数据的时候，根据 IO 操作的就绪状态来采取的不同 方式，说白了是一种读取或者写入操作方法的实现方式，阻塞方式下读取或者写入函数将一 直等待，而非阻塞方式下，读取或者写入方法会立即返回一个状态值。 
​         以银行取款为例：
 阻塞 ： ATM 排队取款，你只能等待（使用阻塞 IO 时，Java 调用会一直阻塞到读写完 成才返回）； 
非阻塞 ： 柜台取款，取个号，然后坐在椅子上做其它事，等号广播会通知你办理，没 到号你就不能去，你可以不断问大堂经理排到了没有，大堂经理如果说还没到你就不能去（使 用非阻塞 IO 时，如果不能读写 Java 调用会马上返回，当 IO 事件分发器通知可读写时再继 续进行读写，不断循环直到读写完成） 

## 4. BIO 编程 

​        Blocking IO： 同步阻塞的编程方式。

​        BIO 编程方式通常是在 JDK1.4 版本之前常用的编程方式。编程实现过程为：首先在服务 端启动一个 ServerSocket 来监听网络请求，客户端启动 Socket 发起网络请求，默认情况下 ServerSocket 回建立一个线程来处理此请求，如果服务端没有线程可用，客户端则会阻塞等 待或遭到拒绝。

​        同步阻塞，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销，当然可 以通过线程池机制改善。
<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572340374569.png" alt="1572340374569" style="zoom:33%;" />



## 5.  NIO 编程 

​         Non-blocking IO（New IO）： 同步非阻塞的编程方式。

​         NIO的读写都是要依靠buffer来进行操作

​         NIO 本身是基于事件驱动思想来完成的，其主要想解决的是 BIO 的大并发问题，NIO 基 于 Reactor，当 socket 有流可读或可写入 socket 时，操作系统会相应的通知引用程序进行处 理，应用再将流读取到缓冲区或写入操作系统。也就是说，这个时候，已经不是一个连接就 要对应一个处理线程了，而是有效的请求，对应一个线程，当连接没有数据时，是没有工作 线程来处理的。 
​         NIO 的最重要的地方是当一个连接创建后，不需要对应一个线程，这个连接会被注册到 多路复用器上面，所以所有的连接只需要一个线程就可以搞定，当这个线程中的多路复用器 进行轮询的时候，发现连接上有请求的话，才开启一个线程进行处理，也就是一个请求一个 线程模式。 
​         在 NIO 的处理方式中，当一个请求来的话，开启线程进行处理，可能会等待后端应用的 资源(JDBC 连接等)，其实这个线程就被阻塞了，当并发上来的话，还是会有 BIO 一样的问题。
​                               <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572340966657.png" alt="1572340966657" style="zoom:33%;" />

```
    同步非阻塞，服务器实现模式为一个请求一个通道，即客户端发送的连接请求都会注册 到多路复用器上，多路复用器轮询到连接有 I/O 请求时才启动一个线程进行处理。 
     NIO 方式适用于连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，并发局 限于应用中，编程复杂，JDK1.4 开始支持
```

Buffer: ByteBuffer,CharBuffer,ShortBuffer,IntBuffer,LongBuffer,FloatBuffer,DoubleBuffer
Channel:
FileChannel文件IO、DatagramChannel  UDP、SocketChannel/ServerSocketChannel  TCP（Server和Client）
Selector:
Selector,AbstractSelector 
SelectionKey:
OP_READ,OP_WRITE,OP_CONNECT,OP_ACCEPT 

NIO主要有三大核心部分：``Channel(通道)``，``Buffer(缓冲区)``, `Selector`。传统IO基于字节流和字符流进行操作，而NIO基于Channel和Buffer(缓冲区)进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。Selector(选择区)用于监听多个通道的事件（比如：连接打开，数据到达）。因此，单个线程可以监听多个数据通道。

步骤：

- 分配空间（ByteBuffer buf = ByteBuffer.allocate(1024); 还有一种allocateDirector后面再陈述）
- 写入数据到Buffer(int bytesRead = fileChannel.read(buf);)
- 调用filp()方法(buf.flip();)  `position设回0，并将limit设成之前的position的值`
- 从Buffer中读取数据（System.out.print((char)buf.get());）
- 调用clear()方法或者compact()方法
  <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572349495353.png" alt="1572349495353" style="zoom:33%;" />

​              <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572349673083.png" alt="1572349673083" style="zoom:33%;" />

   <img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572350399511.png" alt="1572350399511" style="zoom:50%;" />

### buffer

就是往buffer里面添加数据，position会改变位置，而我们读取数据的时候，
buffer.get(datas); 一次全部读到byte数组里面，``byte[] datas = new byte[buffer.remaining()];``
buffer.get();  一个一个的读
而这个读取的过程，是通过position来确定位置的，所以在读取之前要调用flip()来使position复位

```java
/**
 * 
 * Buffer的应用固定逻辑
 * 写操作顺序
 * 1. clear()
 * 2. put() -> 写操作
 * 3. flip() -> 重置游标
 * 4. SocketChannel.write(buffer); -> 将缓存数据发送到网络的另一端
 * 5. clear()
 * 
 * 读操作顺序
 * 1. clear()
 * 2. SocketChannel.read(buffer); -> 从网络中读取数据
 * 3. buffer.flip() -> 重置游标
 * 4. buffer.get() -> 读取数据
 * 5. buffer.clear()
 *
 */
```



<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572350533908.png" alt="1572350533908" style="zoom:50%;" />

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572350875194.png" alt="1572350875194" style="zoom:50%;" />

![1572359684218](C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572359684218.png)

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572359733400.png" alt="1572359733400" style="zoom:50%;" />

下面来慢慢讲解这段代码。

### ServerSocketChannel

打开ServerSocketChannel：

```
ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
```

关闭ServerSocketChannel：

```
serverSocketChannel.close();
```

监听新进来的连接：

```
while(true){    SocketChannel socketChannel = serverSocketChannel.accept();}
```

ServerSocketChannel可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null。 因此，需要检查返回的SocketChannel是否是null.如：

```
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();        serverSocketChannel.socket().bind(new InetSocketAddress(9999));        serverSocketChannel.configureBlocking(false);        while (true)        {            SocketChannel socketChannel = serverSocketChannel.accept();            if (socketChannel != null)            {                // do something with socketChannel...            }        }
```

### Selector

Selector的创建：Selector selector = Selector.open();

为了将Channel和Selector配合使用，必须将Channel注册到Selector上，通过SelectableChannel.register()方法来实现，沿用案例5中的部分代码：

```
            ssc= ServerSocketChannel.open();            ssc.socket().bind(new InetSocketAddress(PORT));            ssc.configureBlocking(false);            ssc.register(selector, SelectionKey.OP_ACCEPT);
```

与Selector一起使用时，Channel必须处于非阻塞模式下。这意味着不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以。

注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：

```
1. Connect2. Accept3. Read4. Write
```

通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。

这四种事件用SelectionKey的四个常量来表示：

```
1. SelectionKey.OP_CONNECT2. SelectionKey.OP_ACCEPT3. SelectionKey.OP_READ4. SelectionKey.OP_WRITE
```

## 6. AIO编程

Asynchronous IO： 异步非阻塞的编程方式 

​       与 NIO 不同，当进行读写操作时，只须直接调用 API 的 read 或 write 方法即可。这两种 方法均为异步的，对于读操作而言，当有流可读取时，操作系统会将可读的流传入 read 方 法的缓冲区，并通知应用程序；对于写操作而言，当操作系统将 write 方法传递的流写入完 毕时，操作系统主动通知应用程序。即可以理解为，read/write 方法都是异步的，完成后会 主动调用回调函数。在 JDK1.7 中，这部分内容被称作 NIO.2，主要在 java.nio.channels 包下 增加了下面四个异步通道： AsynchronousSocketChannel 
AsynchronousServerSocketChannel 
AsynchronousFileChannel 
AsynchronousDatagramChannel

<img src="C:\Users\帅帅\AppData\Roaming\Typora\typora-user-images\1572363163086.png" alt="1572363163086" style="zoom: 50%;" />

