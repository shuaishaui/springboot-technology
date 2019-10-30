/**
 * 1. 单线程组
 * 2. Bootstrap配置启动信息
 * 3. 注册业务处理Handler
 * 4. connect连接服务，并发起请求
 */
package com.shuai.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client4Protocol {
	
	// 处理请求和处理服务端响应的线程组
	private EventLoopGroup group = null;
	// 服务启动相关配置信息
	private Bootstrap bootstrap = null;
	
	public Client4Protocol(){
		init();
	}
	
	private void init(){
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		// 绑定线程组
		bootstrap.group(group);
		// 设定通讯模式为NIO
		bootstrap.channel(NioSocketChannel.class);
	}
	
	public ChannelFuture doRequest(String host, int port, final ChannelHandler... handlers) throws InterruptedException{
		this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
				ch.pipeline().addLast(handlers);
			}
		});
		ChannelFuture future = this.bootstrap.connect(host, port).sync();
		return future;
	}
	
	public void release(){
		this.group.shutdownGracefully();
	}
	
	public static void main(String[] args) {
		Client4Protocol client = null;
		ChannelFuture future = null;
		try{
			client = new Client4Protocol();
			future = client.doRequest("localhost", 9999, new Client4ProtocolHandler());
			
			Scanner s = null;
			while(true){
				s = new Scanner(System.in);
				System.out.print("enter message send to server > ");
				String line = s.nextLine();
				line = Client4ProtocolHandler.ProtocolParser.transferTo(line);
				System.out.println("client send protocol content : " + line);
				future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
				TimeUnit.SECONDS.sleep(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(null != future){
				try {
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(null != client){
				client.release();
			}
		}
	}
	
}
