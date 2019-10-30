/**
 * @Sharable注解 - 
 *  代表当前Handler是一个可以分享的处理器。也就意味着，服务器注册此Handler后，可以分享给多个客户端同时使用。
 *  如果不使用注解描述类型，则每次客户端请求时，必须为客户端重新创建一个新的Handler对象。
 *  如果handler是一个Sharable的，一定避免定义可写的实例变量。
 *  bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new XxxHandler());
			}
		});
 */
package com.shuai.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

@Sharable
public class Server4HelloWorldHandler extends ChannelHandlerAdapter {
	
	/**
	 * 业务处理逻辑
	 * 用于处理读取数据请求的逻辑。
	 * ctx - 上下文对象。其中包含于客户端建立连接的所有资源。 如： 对应的Channel
	 * msg - 读取到的数据。 默认类型是ByteBuf，是Netty自定义的。是对ByteBuffer的封装。 不需要考虑复位问题。
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 获取读取的数据， 是一个缓冲。
		ByteBuf readBuffer = (ByteBuf) msg;
		// 创建一个字节数组，用于保存缓存中的数据。
		byte[] tempDatas = new byte[readBuffer.readableBytes()];
		// 将缓存中的数据读取到字节数组中。
		readBuffer.readBytes(tempDatas);
		String message = new String(tempDatas, "UTF-8");
		System.out.println("from client : " + message);
		if("exit".equals(message)){
			ctx.close();
			return;
		}
		String line = "server message to client!";
		// 写操作自动释放缓存，避免内存溢出问题。
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
		// 注意，如果调用的是write方法。不会刷新缓存，缓存中的数据不会发送到客户端，必须再次调用flush方法才行。
		// ctx.write(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
		// ctx.flush();
	}

	/**
	 * 异常处理逻辑， 当客户端异常退出的时候，也会运行。
	 * ChannelHandlerContext关闭，也代表当前与客户端连接的资源关闭。
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		// cause.printStackTrace();
		ctx.close();
	}

}
