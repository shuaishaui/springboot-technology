package com.shuai.nio;

import java.nio.ByteBuffer;

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
public class TestBuffer {
	public static void main(String[] args) throws Exception {
		
		ByteBuffer buffer = ByteBuffer.allocate(8);
		
		byte[] temp = new byte[]{3,2,1};
		
		// 写入数据之前 ： java.nio.HeapByteBuffer[pos=0 lim=8 cap=8]
		// pos - 游标位置， lim - 限制数量， cap - 最大容量
		System.out.println("写入数据之前 ： " + buffer);
		
		// 写入字节数组到缓存
		buffer.put(temp);
		
		// 写入数据之后 ： java.nio.HeapByteBuffer[pos=3 lim=8 cap=8]
		// 游标为3， 限制为8， 容量为8
		System.out.println("写入数据之后 ： " + buffer);
		
		// 重置游标 ， lim = pos ;  pos = 0;
		buffer.flip();
		
		// 重置游标之后 ： java.nio.HeapByteBuffer[pos=0 lim=3 cap=8]
		// 游标为0， 限制为3， cap为8
		System.out.println("重置游标之后 ： " + buffer);

		// 清空Buffer， pos = 0; lim = cap;
		// buffer.clear();
		
		// get() -> 获取当前游标指向的位置的数据。
		// System.out.println(buffer.get());
		
		for(int i = 0; i < buffer.remaining(); i++){
			// get(int index) -> 获取指定位置的数据。
			int data = buffer.get(i);
			System.out.println(i + " - " + data);
		}
	}
}
