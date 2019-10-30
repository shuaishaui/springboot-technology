package com.shuai.netty.fixedlength;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class Client4FixedLengthHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try{
			String message = msg.toString();
			System.out.println("from server : " + message);
		}finally{
			// 用于释放缓存。避免内存溢出
			// 注意这里和服务端的那里的区别，服务端那里时 writeAndFlush 写的时候就自动清除缓存了
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run...");
		// cause.printStackTrace();
		ctx.close();
	}

}
