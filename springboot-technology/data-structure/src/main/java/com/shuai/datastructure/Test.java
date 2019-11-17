package com.shuai.datastructure;

public class Test {

  private static void modify(StringBuilder builder,int i,Integer o){
    builder.append("我是引用传递修改");
    i = 1;
    o = 1;
  }

  public static void main(String[] args) {
    StringBuilder builder = new StringBuilder("Hello");
    int i = 0;
    Integer o = 0;
    System.out.println("调用传递前的值：" + "builder=" + builder + " i=" + i + " o=" + o);
    modify(builder,i,o);
    System.out.println("调用传递后的值：" + "builder=" + builder + " i=" + i + " o=" + o);
  }

}
