/**
 * 1. 单线程组
 * 2. Bootstrap配置启动信息
 * 3. 注册业务处理Handler
 * 4. connect连接服务，并发起请求
 */
package com.shuai.netty.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client4Delimiter {
	
	// 处理请求和处理服务端响应的线程组
	private EventLoopGroup group = null;
	// 服务启动相关配置信息
	private Bootstrap bootstrap = null;
	
	public Client4Delimiter(){
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
	
	public ChannelFuture doRequest(String host, int port) throws InterruptedException{
		this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// 数据分隔符
				ByteBuf delimiter = Unpooled.copiedBuffer("$E$".getBytes());
				ChannelHandler[] handlers = new ChannelHandler[3];
				handlers[0] = new DelimiterBasedFrameDecoder(1024, delimiter);
				// 字符串解码器Handler，会自动处理channelRead方法的msg参数，将ByteBuf类型的数据转换为字符串对象
				handlers[1] = new StringDecoder(Charset.forName("UTF-8"));
				handlers[2] = new Client4DelimiterHandler();
				
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
		Client4Delimiter client = null;
		ChannelFuture future = null;
		try{
			client = new Client4Delimiter();
			
			future = client.doRequest("localhost", 9999);
			
			Scanner s = null;
			while(true){
				s = new Scanner(System.in);
				System.out.print("enter message send to server > ");
				String line = s.nextLine();
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
