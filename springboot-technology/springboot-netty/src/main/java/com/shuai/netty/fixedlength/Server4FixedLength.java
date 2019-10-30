/**
 * 1. 双线程组
 * 2. Bootstrap配置启动信息
 * 3. 注册业务处理Handler
 * 4. 绑定服务监听端口并启动服务
 */
package com.shuai.netty.fixedlength;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.Charset;

public class Server4FixedLength {
	// 监听线程组，监听客户端请求
	private EventLoopGroup acceptorGroup = null;
	// 处理客户端相关操作线程组，负责处理与客户端的数据通讯
	private EventLoopGroup clientGroup = null;
	// 服务启动相关配置信息
	private ServerBootstrap bootstrap = null;
	public Server4FixedLength(){
		init();
	}
	private void init(){
		acceptorGroup = new NioEventLoopGroup();
		clientGroup = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();
		// 绑定线程组
		bootstrap.group(acceptorGroup, clientGroup);
		// 设定通讯模式为NIO
		bootstrap.channel(NioServerSocketChannel.class);
		// 设定缓冲区大小
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		// SO_SNDBUF发送缓冲区，SO_RCVBUF接收缓冲区，SO_KEEPALIVE开启心跳监测（保证连接有效）
		bootstrap.option(ChannelOption.SO_SNDBUF, 16*1024)
			.option(ChannelOption.SO_RCVBUF, 16*1024)
			.option(ChannelOption.SO_KEEPALIVE, true);
	}
	public ChannelFuture doAccept(int port) throws InterruptedException{
		
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelHandler[] acceptorHandlers = new ChannelHandler[3];
				// 定长Handler。通过构造参数设置消息长度（单位是字节）。发送的消息长度不足可以使用空格补全。
				acceptorHandlers[0] = new FixedLengthFrameDecoder(5);
				// 字符串解码器Handler，会自动处理channelRead方法的msg参数，将ByteBuf类型的数据转换为字符串对象
				acceptorHandlers[1] = new StringDecoder(Charset.forName("UTF-8"));
				acceptorHandlers[2] = new Server4FixedLengthHandler();
				ch.pipeline().addLast(acceptorHandlers);
			}
		});
		ChannelFuture future = bootstrap.bind(port).sync();
		return future;
	}
	public void release(){
		this.acceptorGroup.shutdownGracefully();
		this.clientGroup.shutdownGracefully();
	}
	
	public static void main(String[] args){
		ChannelFuture future = null;
		Server4FixedLength server = null;
		try{
			server = new Server4FixedLength();
			
			future = server.doAccept(9999);
			System.out.println("server started.");
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
