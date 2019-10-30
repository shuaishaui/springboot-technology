/**
 * 1. 双线程组
 * 2. Bootstrap配置启动信息
 * 3. 注册业务处理Handler
 * 4. 绑定服务监听端口并启动服务
 */
package com.shuai.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server4HelloWorld {
	// 监听线程组，监听客户端请求
	private EventLoopGroup acceptorGroup = null;
	// 处理客户端相关操作线程组，负责处理与客户端的数据通讯
	private EventLoopGroup clientGroup = null;
	// 服务启动相关配置信息
	private ServerBootstrap bootstrap = null;
	public Server4HelloWorld(){
		init();
	}
	private void init(){
		// 初始化线程组,构建线程组的时候，如果不传递参数，则默认构建的线程组线程数是CPU核心数量。
		acceptorGroup = new NioEventLoopGroup();
		clientGroup = new NioEventLoopGroup();
		// 初始化服务的配置
		bootstrap = new ServerBootstrap();
		// 绑定线程组
		bootstrap.group(acceptorGroup, clientGroup);
		// 设定通讯模式为NIO， 同步非阻塞
		bootstrap.channel(NioServerSocketChannel.class);
		// 设定缓冲区大小， 缓存区的单位是字节。
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		// SO_SNDBUF发送缓冲区，SO_RCVBUF接收缓冲区，SO_KEEPALIVE开启心跳监测（保证连接有效）
		bootstrap.option(ChannelOption.SO_SNDBUF, 16*1024)
			.option(ChannelOption.SO_RCVBUF, 16*1024)
			.option(ChannelOption.SO_KEEPALIVE, true);
	}
	/**
	 * 监听处理逻辑。
	 * @param port 监听端口。
	 * @param acceptorHandlers 处理器， 如何处理客户端请求。
	 * @return
	 * @throws InterruptedException
	 */
	private ChannelFuture doAccept(int port, final ChannelHandler... acceptorHandlers) throws InterruptedException{
		
		/*
		 * childHandler是服务的Bootstrap独有的方法。是用于提供处理对象的。
		 * 可以一次性增加若干个处理逻辑。是类似责任链模式的处理方式。
		 * 增加A，B两个处理逻辑，在处理客户端请求数据的时候，根据A-》B顺序依次处理。
		 * 
		 * ChannelInitializer - 用于提供处理器的一个模型对象。
		 *  其中定义了一个方法，initChannel方法。
		 *   方法是用于初始化处理逻辑责任链条的。
		 *   可以保证服务端的Bootstrap只初始化一次处理器，尽量提供处理逻辑的重用。
		 *   避免反复的创建处理器对象。节约资源开销。
		 */
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(acceptorHandlers);
			}
		});
		// bind方法 - 绑定监听端口的。ServerBootstrap可以绑定多个监听端口。 多次调用bind方法即可
		// sync - 开始监听逻辑。 返回一个ChannelFuture。 返回结果代表的是监听成功后的一个对应的未来结果
		// 可以使用ChannelFuture实现后续的服务器和客户端的交互。
		ChannelFuture future = bootstrap.bind(port).sync();
		return future;
	}
	
	/**
	 * shutdownGracefully - 方法是一个安全关闭的方法。可以保证不放弃任何一个已接收的客户端请求。
	 */
	public void release(){
		this.acceptorGroup.shutdownGracefully();
		this.clientGroup.shutdownGracefully();
	}
	
	public static void main(String[] args){
		ChannelFuture future = null;
		Server4HelloWorld server = null;
		try{
			server = new Server4HelloWorld();
			future = server.doAccept(9999,new Server4HelloWorldHandler());
			System.out.println("server started.");
			
			// 关闭连接的。
			future.channel().closeFuture().sync();
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			if(null != future){
				try {
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(null != server){
				server.release();
			}
		}
	}
	
}
