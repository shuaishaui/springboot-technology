/**
 * @Sharable注解 - 
 *  代表当前Handler是一个可以分享的处理器。也就意味着，服务器注册此Handler后，可以分享给多个客户端同时使用。
 *  如果不使用注解描述类型，则每次客户端请求时，必须为客户端重新创建一个新的Handler对象。
 *  
 */
package com.shuai.netty.protocol;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class Server4ProtocolHandler extends ChannelHandlerAdapter {
	
	// 业务处理逻辑
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String message = msg.toString();
		System.out.println("server receive protocol content : " + message);
		message = ProtocolParser.parse(message);
		if(null == message){
			System.out.println("error request from client");
			return ;
		}
		System.out.println("from client : " + message);
		String line = "server message";
		line = ProtocolParser.transferTo(line);
		System.out.println("server send protocol content : " + line);
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
	}

	// 异常处理逻辑
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		cause.printStackTrace();
		ctx.close();
	}
	
	static class ProtocolParser{
		public static String parse(String message){
			String[] temp = message.split("HEADBODY");
			temp[0] = temp[0].substring(4);
			temp[1] = temp[1].substring(0, (temp[1].length()-4));
			int length = Integer.parseInt(temp[0].substring(temp[0].indexOf(":")+1));
			if(length != temp[1].length()){
				return null;
			}
			return temp[1];
		}
		public static String transferTo(String message){
			message = "HEADcontent-length:" + message.length() + "HEADBODY" + message + "BODY";
			return message;
		}
	}

}
